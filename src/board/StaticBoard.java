package board;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import utilities.SokobanUtil;
import utilities.SokobanUtil.Action;
import utilities.WeightedPoint;

public class StaticBoard {

	private static StaticBoard INSTANCE = null;
	
	/** Warning, grid[y][x] is the value at the point (x,y)*/
	public final Symbol[][] grid; 
	public final List<Point> goals;
	/** Cost to get from a point to one of the boxes' initial position */
	public final Map<Point, Map<Point, Integer>> pointToBoxCost;
    public final Map<Point, Map<Point, Integer>> pointToGoalCost;
    public final Dimension mapDim;
	
	public static void init(Symbol[][] grid, List<Point> goals, List<Point> boxSetup, Dimension boardDim) {
		INSTANCE = new StaticBoard(grid, goals, boxSetup, boardDim);
	}
	
	public static StaticBoard getInstance() {
		if (INSTANCE == null) throw new RuntimeException("The static map has not been initialized");
		return INSTANCE;
	}
	
	private StaticBoard(Symbol[][] grid, List<Point> goals, List<Point> boxSetup, Dimension boardDim) {
		this.grid = grid;
		this.goals = Collections.unmodifiableList(goals);
        this.pointToGoalCost = generatePathCosts(goals, true);
        this.pointToBoxCost = generatePathCosts(boxSetup, false);
        this.mapDim = boardDim;
        
	}
    
    /**
     * Creates a cost mapFills with a map for each point on the board. Each point
     * maps to another map, which contains a mapping from target points to integers
     * representing the cost of getting to the given point from the given target.
     */
    private Map<Point, Map<Point, Integer>> generatePathCosts(List<Point> targets, boolean pullMode) {
    	Map<Point, Map<Point, Integer>> costMap = new HashMap<>();
    	
        for (Point goal : targets) {
            Queue<WeightedPoint> open = new LinkedList<>();
            Set<Point> closed = new HashSet<>(); // A set is more efficient for search
            
            WeightedPoint wp = new WeightedPoint(goal, 0);
            open.add(wp);
            while (!open.isEmpty()) { // while there are unexpanded points
                WeightedPoint next = open.remove();
                closed.add(next.point);
                Symbol currentSymbol = get(next.point); 
                if (currentSymbol.isWalkable) {
                    // Make sure the hashmap for this point is initialised
                    Map<Point, Integer> currentCosts = costMap.get(next.point);
                    if (currentCosts == null){
                        currentCosts = new HashMap<>();
                        costMap.put(next.point, currentCosts);
                    }

                    // expand the neighbours of this point and add them to the
                    // open list if they have not yet been visited.
                    List<WeightedPoint> neighbours = expandPoint(next, pullMode); 
                	currentCosts.put(goal, next.cost);
                    
                    for (WeightedPoint neighbour : neighbours) {
                        if (!closed.contains(neighbour.point)){
                            open.add(neighbour);
                        }
                    }
                }
            }
        }
        
        return costMap;
    }
    
    /**
     * Flood fill expansion from a point. Returns the points that can be reached 
     * using a the chosen exploration mode
     * @param p Origin point
     * @param pullMode if true, you want to pull to the target, else you want to push
     * @return list of the nodes to be expanded
     */
    private List<WeightedPoint> expandPoint(WeightedPoint p, boolean pullMode){
        List<WeightedPoint> freeNeighbours = new ArrayList<>();
		
        for (Action a : Action.values()) {
        	// Reversing the actions is not important as we don't really care 
        	// about the action itself, we just expand in every direction.
			Point neighbour = SokobanUtil.applyActionToPoint(a, p.point);
			
			if (get(neighbour).isWalkable) {
				Point extraPoint;
				// The player needs space behind the box, where he will be able to stay after pulling it
				if (pullMode) extraPoint = SokobanUtil.applyActionToPoint(a, neighbour);
				// The player needs space in front of the box to be able to push it
				else extraPoint = SokobanUtil.applyActionToPoint(SokobanUtil.inverseAction(a), p.point);
				
				if (get(extraPoint).isWalkable) {
					freeNeighbours.add(new WeightedPoint(neighbour, p.cost + 1));
				}
			}
		}
		return freeNeighbours;
    }
	
	public Symbol get(Point point) {
		return get(point.x, point.y);
	}
	
	public Symbol get(int x, int y) {
		return grid[y][x];
	}
	
	/** @return Ascii art representation of the map (static only) */
	public String toString() {
		return SokobanUtil.stringifyGrid(grid);
	}

	public static boolean isLocked(Point p) {
		Map<Point, Integer> costs = getInstance().pointToGoalCost.get(p); 
		return costs == null || costs.isEmpty();
	}
	
}
