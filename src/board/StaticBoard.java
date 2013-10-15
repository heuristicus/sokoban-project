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
    public final Map<Point, Map<Point, Integer> > pointToGoalCost;
    public final Map<Point, Map<Point, Integer> > goalToPointCost;
    public final Dimension mapDim;
	
	public static void init(Symbol[][] grid, List<Point> goals, Dimension boardDim) {
		INSTANCE = new StaticBoard(grid, goals, boardDim);
	}
	
	public static StaticBoard getInstance() {
		if (INSTANCE == null) throw new RuntimeException("The static map has not been initialized");
		return INSTANCE;
	}
	
	private StaticBoard(Symbol[][] grid, List<Point> goals, Dimension boardDim) {
		this.grid = grid;
		this.goals = Collections.unmodifiableList(goals);
        this.pointToGoalCost = new HashMap<>();
        this.goalToPointCost = new HashMap<>();
        this.mapDim = boardDim;
        generatePathCosts();
	}
    
    /**
     * Fills the goalDistanceCost map with a map for each point on the board. Each point
     * maps to another map, which contains a mapping from goal points to integers
     * representing the cost of getting to the given point from the given goal.
     */
    private void generatePathCosts(){
    	
        for (Point goal : goals) {
            Queue<WeightedPoint> open = new LinkedList<>();
            Set<Point> closed = new HashSet<>(); // A set is more efficient for search
            
            WeightedPoint wp = new WeightedPoint(goal, 0);
            open.add(wp);
            while (!open.isEmpty()) { // while there are unexpanded points
                WeightedPoint next = open.remove();
                closed.add(next.point);
                Symbol currentSymbol = get(next.point); 
                if (currentSymbol.isWalkable) {
                // Only consider walkable positions
                    // Make sure the hashmap for this point is initialised
                    Map<Point, Integer> currentCosts = pointToGoalCost.get(next.point);
                    if (currentCosts == null){
                        currentCosts = new HashMap<>();
                        pointToGoalCost.put(next.point, currentCosts);
                    }
                    
                    // Put the goal node being checked along with the cost of getting to
                    // this position from that goal into the map for this point.

                    // expand the neighbours of this point and add them to the
                    // open list if they have not yet been visited.
                    List<WeightedPoint> neighbours = expandPoint(next); 
                	currentCosts.put(goal, next.cost);
                    
                    for (WeightedPoint neighbour : neighbours) {
                        if (!closed.contains(neighbour.point)){
                            open.add(neighbour);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Flood fill expansion from a point
     * The nodes to be expanded are the neighbours that have walkable tiles on both sides
     * @param p Origin point
     * @return list of the nodes to be expanded
     */
    private List<WeightedPoint> expandPoint(WeightedPoint p){
        List<WeightedPoint> freeNeighbours = new ArrayList<>();
		
        for (Action a : Action.values()) {
			Point neighbour = SokobanUtil.applyActionToPoint(a, p.point);
			
			if (get(neighbour).isWalkable) {
				Point farNeighbour = SokobanUtil.applyActionToPoint(a, neighbour);
				if (get(farNeighbour).isWalkable) {
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
