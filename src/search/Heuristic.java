/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    
    public static class ManhattanHeuristic implements Heuristic<Board>{

        /**
         * THIS IS A PLACEHOLDER!
         * @param start
         * @param goal
         * @return 
         */
        @Override
        public float utility(Board start, Board goal) {
            return 1.0f;
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
            Map<Point, Map<Point, Integer> > pointCosts = StaticBoard.getInstance().goalDistanceCost;
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
    
    public static class MinMatchingHeuristic implements Heuristic<Board> {

    	public static Symbol[] START_SYMBOLS = { Symbol.Box, Symbol.BoxOnGoal };
    	public static Symbol[] GOAL_SYMBOLS = { Symbol.Box, Symbol.BoxOnGoal };

    	public float utility(Board start, Board goal) {
    		goal.setAvailablePosition();
    		List<Point> Goals = goal.getGoalPoints();
    		Map<Point, Symbol> startSet = start.getDynamicObjects();
    		List<List<Integer>> Cost = new ArrayList<>();
    		List<Integer> DegreeOfOccupied = new ArrayList<>(Goals.size());

    		for (int i = 0; i < Goals.size(); i++) {
    			DegreeOfOccupied.add(0);
    		}
    		/** Create a 2D array to store the cost of box to Goals **/
    		for (Point startPt : startSet.keySet()) {
    			if (isStartSymbol(start.get(startPt))) {
    				List<Integer> EstimateCost = new ArrayList<>(Goals.size());
    				for (Point goalPt : Goals) {
    					/**
    					 * If it is available position, calculate the Manhattan
    					 * Heuristic
    					 **/
    	
    					if (goal.availablePosition.get(Goals.indexOf(goalPt))
    							.contains(startPt)) {
    						EstimateCost.add(Math.abs(startPt.x - goalPt.x)
    								+ Math.abs(startPt.y - goalPt.y));
    						DegreeOfOccupied
    								.set(Goals.indexOf(goalPt), DegreeOfOccupied
    										.get(Goals.indexOf(goalPt)) + 1);
    					} else {
    						/** If no, then it is positive infinity **/
    						EstimateCost.add(Integer.MAX_VALUE);
    					}

    				}

    				Cost.add(EstimateCost);
    			}
    		}
    		/** Greedy Estimation, may overestimated but save Computational Time **/
    		List<Integer> FinalEstimateCost = new ArrayList<>(Goals.size());
    		for (int i = 0; i < Goals.size(); i++) {
    			FinalEstimateCost.add(0);
    		}

    		for (List<Integer> innerList : Cost) {

    			int MinValue = Integer.MAX_VALUE;
    			int MinIndex = 0;
    			for (int i = 0; i < innerList.size(); i++) {
    				int value = innerList.get(i);
    				/**
    				 * Fit box to the goals that has the fewest available box to
    				 * reach
    				 **/
    				if (value != Integer.MAX_VALUE) {
    					if (DegreeOfOccupied.get(i) < DegreeOfOccupied
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
    			DegreeOfOccupied.set(MinIndex, DegreeOfOccupied.get(MinIndex) - 1);
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

    	private boolean isStartSymbol(Symbol sym) {
    		for (Symbol ref : START_SYMBOLS) {
    			if (ref == sym) {
    				return true;
    			}
    		}
    		return false;
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
