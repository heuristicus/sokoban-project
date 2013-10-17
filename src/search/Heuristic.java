/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilities.Pair;
import board.Board;
import board.StaticBoard;
import board.Symbol;

/**
 * An interface for heuristics which estimate the cost to get from a start point
 * to a goal point.
 * @param <T> The type on which to perform the computation of the cost estimate
 * @author michal
 */
public interface Heuristic<T> {
    float utility(T start, T goal);
    
    public static class ManhattanClosestHeuristic implements Heuristic<Board>
    {
    	
    	public static Symbol[] START_SYMBOLS = {Symbol.Box, Symbol.BoxOnGoal};
    	public static Symbol[] GOAL_SYMBOLS = {Symbol.Box, Symbol.BoxOnGoal};
    	
    	
        @Override
    	/** Returns an optimistic estimation of the coast to go from state start to state goal.
    	 * 	
    	 */
    	public float utility(Board begin, Board end)
    	{		
            
            Map<Point, Symbol> start = begin.getDynamicObjects();
            Map<Point, Symbol> goal = end.getDynamicObjects();
    		float estimation = 0;
    		for (Point startPt : start.keySet())
    		{
    			if (isStartSymbol(start.get(startPt)))
    			{
    				float bestDistance = Float.POSITIVE_INFINITY;
    				for (Point goalPt : goal.keySet())
    				{
    					if (isGoalSymbol(goal.get(goalPt)))
    					{
    						float MHdistance = Math.abs(startPt.x - goalPt.x) + Math.abs(startPt.y - goalPt.y);
    						bestDistance = Math.min(bestDistance, MHdistance);
//    						bestDistance = Math.min(bestDistance, MHdistance*MHdistance);
    					}
    				}
    				estimation += bestDistance;
    			}
    		}
    		return estimation;
    	}
        
        
    	
    	private boolean isStartSymbol(Symbol sym)
    	{
    		for (Symbol ref : START_SYMBOLS)
    		{
    			if (ref == sym)
    			{
    				return true;
    			}
    		}
    		return false;
    	}
    	
    	private boolean isGoalSymbol(Symbol sym)
    	{
    		for (Symbol ref : GOAL_SYMBOLS)
    		{
    			if (ref == sym)
    			{
    				return true;
    			}
    		}
    		return false;
    	}
    	
    }
    
    public static class RealClosestHeuristic implements Heuristic<Board>
    {
        @Override
    	/** Returns an optimistic estimation of the coast to go from state start to state goal.
    	 * 	
    	 */
    	public float utility(Board begin, Board end)
    	{		
            Map<Point, Symbol> start = begin.getDynamicObjects();
    		float estimation = 0;
    		for (Point startPt : start.keySet())
    		{
    			if (!startPt.equals(begin.getPlayerPosition()))
    			{
    				float bestDistance = Float.POSITIVE_INFINITY;
    				if(StaticBoard.getInstance().pointToGoalCost.get(startPt) != null)
    				{
	    				for (Integer dist : StaticBoard.getInstance().pointToGoalCost.get(startPt).values())
	    				{	
							bestDistance = Math.min(bestDistance, dist);
	    				}
    				}
    				estimation += bestDistance;
    			}	
    		}
    		return estimation;
    	}
        
    }
    
    
    /**
     * This heuristic takes into account walls in the map. For each box, the closest
     * goal is computed, and then the cost for the *player* to move from that box position
     * to the goal. This means that even if the box is unable to be pushed to its nearest
     * goal due to the map structure, the cost to that goal is still considered. The heuristic
     * returns the sum of the cost for each box on the map to move to its closes goal,
     * ignoring positions which would be impassable, but considering walls.
     * 
     * !!! This heuristic only works for moving to goal positions !!!
     * The goal board is implicitly considered to be the state in which all boxes are
     * on a goal.
     *
     * @author michal
     */
    public static class PointCostHeuristic implements Heuristic<Board>{

        @Override
        public float utility(Board start, Board goal) {
            float totalCost = 0;
            Map<Point, Symbol> dynamic = start.getDynamicObjects();
            Map<Point, Map<Point, Integer> > pointCosts = StaticBoard.getInstance().pointToGoalCost;
            // Go through each point in the dynamic portion of the map, ignoring the player.
            for (Point point : dynamic.keySet()) {
                Symbol thisSymbol = start.get(point);
                // We are not interested in the player position.
                if (thisSymbol.type == Symbol.Type.Player)
                    continue;
                // Get the costMap for this box
                Map<Point, Integer> goalCosts = pointCosts.get(point);
                int lowestCost = Integer.MAX_VALUE;
                // Check all the goal points, and select the cost of the one which
                // is closest.
                for (Point goalPoint : goalCosts.keySet()) {
                    int thisCost = goalCosts.get(goalPoint);
                    // We don't care about which goal we are pushing towards, only the
                    // cost to get there, so just find the lowest cost.
                    if (thisCost < lowestCost)
                        lowestCost = thisCost;
                }
                totalCost += lowestCost;
            }

            return totalCost;
        }
        
    }
    
     /**
     * MinMatching between the goals and the boxes
     * @return 
     * 	sum(cost_per_box_to_reach_goal).
	 * 	If no solution is found, returns Float.POSITIVE_INFINITY
     */
    public static class MinMatching2Heuristic implements Heuristic<Board> {
    	boolean forwardMode;
    	
    	public MinMatching2Heuristic(boolean forwardMode) {
    		super();
    		this.forwardMode = forwardMode;
    	}
    	
    	// TODO might not be needed if we just want the max instead of sorting the list
    	private static Comparator<Map.Entry<Point, Integer>> candidatesPerGoalComparator = 
    			new Comparator<Map.Entry<Point,Integer>>()  {
		    		@Override
					public int compare(Map.Entry<Point, Integer> o1, Map.Entry<Point, Integer> o2) {
						return Integer.compare(o1.getValue(), o2.getValue());
					}
    			};
    			
    	/** Sorts the collection by size of the inner cost list */
    	private static Comparator<Pair<Point, Map<Point, Integer>>> candidatesPerBoxComparator = 
    			new Comparator<Pair<Point, Map<Point, Integer>>>() {
					@Override
					public int compare(Pair<Point, Map<Point, Integer>> o1, Pair<Point, Map<Point, Integer>> o2) {
						return Integer.compare(o1.second.size(), o2.second.size());
					}
				};
    	
		/**
		 * @param start board to evaluate
		 * @param goal unused, can be set to null
		 */
    	@Override
    	public float utility(Board start, Board goal) {
    		List<Point> goals;
    		Map<Point, Map<Point, Integer>> costMap;
    		if (forwardMode) { // We are pushing the boxes to the actual goals
    			goals = StaticBoard.getInstance().goals;
    			costMap = StaticBoard.getInstance().pointToGoalCost;
    		} else { // We are pulling the boxes to their initial position
    			goals = StaticBoard.getInstance().initialBoxSetup;
    			costMap = StaticBoard.getInstance().pointToBoxCost;
    		}
    		
    		List<Pair<Point, Map<Point, Integer>>> candidatesPerBox = new ArrayList<>(goals.size());
    		Map<Point, Integer> candidatesPerGoal = new HashMap<>(goals.size());
    		for (Point p : goals) {
    			candidatesPerGoal.put(p, 0);
    		}
    		
    		// Initialize the data structures: count the candidates for the boxes and goals
    		for (Point p: start.getDynamicObjects().keySet()) {
    			if (start.get(p).type != Symbol.Type.Box) continue;
    			if (costMap.get(p) == null) return Float.POSITIVE_INFINITY; // Box in a locked position
    			
    			candidatesPerBox.add(new Pair<>(p, costMap.get(p)));
    			for (Point key : costMap.get(p).keySet()) {
    				candidatesPerGoal.put(key, candidatesPerGoal.get(key) + 1);
    			}
    			
    		}
    		Collections.sort(candidatesPerBox, candidatesPerBoxComparator);
    		
    		int totalCost = 0;
    		for (Pair<Point, Map<Point, Integer>> pair : candidatesPerBox) {
    			
    			List<Map.Entry<Point, Integer>> selectedCandidates = new ArrayList<>(pair.second.size());
    			for (Map.Entry<Point, Integer> entry : candidatesPerGoal.entrySet()) {
    				if (pair.second.containsKey(entry.getKey())) {
    					selectedCandidates.add(entry);
    				}
    			}
    			
    			if (selectedCandidates.isEmpty()) {
    				// A box has no available goal: no solution found
    				return Float.POSITIVE_INFINITY;
    			}
    			
    			// Sorting the selected goals by number of candidates
    			Collections.sort(selectedCandidates, candidatesPerGoalComparator);
    			
    			/* Finally: getting the selected goal. 
    			 * Here the method could be changed to try not to get the first, to ensure a 
    			 * more reliable solution. 
    			 * TODO: if we stick with first, it might be better to get it from the loop
    			 * instead of sorting the array. */
    			Point selectedGoal = selectedCandidates.get(0).getKey();
    			
    			totalCost += pair.second.get(selectedGoal);
    			
    			// Update the data structures to not include the goal and the box anymore.
    			for (Point p : pair.second.keySet()) {
    				if (candidatesPerGoal.containsKey(p)) {
    					candidatesPerGoal.put(p, candidatesPerGoal.get(p) - 1);    					
    				}
    			}
    			candidatesPerGoal.remove(selectedGoal);
    		}

    		// The value of the current state is based on the cost to reach all the goals
    		return totalCost;

    	}
    }
    
    public static class MinMatchingHeuristic implements Heuristic<Board> {

//    	public static Symbol[] GOAL_SYMBOLS = { Symbol.Box, Symbol.BoxOnGoal };

    	public float utility(Board start, Board goal) {
    		goal.setAvailablePosition();
    		List<Point> goals = StaticBoard.getInstance().goals;
    		Map<Point, Symbol> startSet = start.getDynamicObjects();
    		List<List<Integer>> cost = new ArrayList<>();
    		List<Integer> degreeOfOccupied = new ArrayList<>(goals.size());

    		for (int i = 0; i < goals.size(); i++) {
    			degreeOfOccupied.add(0);
    		}
    		/** Create a 2D array to store the cost of box to Goals **/
    		for (Point startPt : startSet.keySet()) {
    			if (start.get(startPt).type == Symbol.Type.Box) {
    				List<Integer> estimateCost = new ArrayList<>(goals.size());
    				for (Point goalPt : goals) {
    					/**
    					 * If it is available position, calculate the Manhattan
    					 * Heuristic
    					 **/
    	
    					if (goal.availablePosition.get(goals.indexOf(goalPt)).contains(startPt)) {
    						estimateCost.add(Math.abs(startPt.x - goalPt.x) 
    								+ Math.abs(startPt.y - goalPt.y));
    						degreeOfOccupied.set(goals.indexOf(goalPt), 
    								degreeOfOccupied.get(goals.indexOf(goalPt)) + 1);
    					} else {
    						/** If no, then it is positive infinity **/
    						estimateCost.add(Integer.MAX_VALUE);
    					}

    				}

    				cost.add(estimateCost);
    			}
    		}
    		/** Greedy Estimation, may overestimated but save Computational Time **/
    		List<Integer> FinalEstimateCost = new ArrayList<>(goals.size());
    		for (int i = 0; i < goals.size(); i++) {
    			FinalEstimateCost.add(0);
    		}

    		for (List<Integer> innerList : cost) {

    			int MinValue = Integer.MAX_VALUE;
    			int MinIndex = 0;
    			for (int i = 0; i < innerList.size(); i++) {
    				int value = innerList.get(i);
    				/**
    				 * Fit box to the goals that has the fewest available box to
    				 * reach
    				 **/
    				if (value != Integer.MAX_VALUE) {
    					if (degreeOfOccupied.get(i) < degreeOfOccupied
    							.get(MinIndex)) {
    						MinIndex = i;
    						MinValue = value;
    					}
    				}

    			}
    			/** If no goal can fit, it is deadlock **/
    			if (MinValue == Integer.MAX_VALUE) {
    				// TODO: handle deadlock in this situation?
    				System.out.println("It is deadlock");
    				return (float) Integer.MAX_VALUE;
    			}
    			FinalEstimateCost.set(MinIndex, MinValue);
    			degreeOfOccupied.set(MinIndex, degreeOfOccupied.get(MinIndex) - 1);
    		}

    		/**
    		 * return the minimum value of the List, so that the agent will keep
    		 * push same box
    		 **/
    		int minValue = Integer.MAX_VALUE;
    		for (int i = 0; i < FinalEstimateCost.size(); i++) {
    			int temp = FinalEstimateCost.get(i);
    			if (minValue < temp) {
    				minValue = temp;
    			}
    		}
    		return minValue;

    	}
    }
    
    public static class DiagonalDistanceHeuristic implements Heuristic<Board> {
    	/**
		 * DiagonalDistanceHeuristic
         * This heuristic is used to calculate the player's path on the board, between the boxes.
         * It assumes no box is affected by the player's movement.
         * 
         * The value returned is actually the square of the diagonal distance, but since it's only used
         * as comparison values, avoiding to compute the square root is probably better
         * 
         * @param start
         * @param goal
         * @return a value of distance between the two points
         */
        @Override
        public float utility(Board start, Board goal) {
        	return (float)goal.getPlayerPosition().distanceSq(start.getPlayerPosition());
        }
    }
    
}
