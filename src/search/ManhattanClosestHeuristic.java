package search;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import board.Board;
import board.Symbol;


public class ManhattanClosestHeuristic implements Heuristic<Map<Point, Symbol>>
{
	
	public static Symbol[] START_SYMBOLS = {Symbol.Box, Symbol.BoxOnGoal};
	public static Symbol[] GOAL_SYMBOLS = {Symbol.Box, Symbol.BoxOnGoal};
	
	
	/** Returns an optimistic estimation of the coast to go from state start to state goal.
	 * 	Wrapper for the Map<Point,Symbol> version.
	 */
	public float utility(Board start, Board goal)
	{	
		/** Not sure about the heuristic interface, here is the Minimum Matching Algorithm and result**/
		start.setAvailablePosition();
		goal.setAvailablePosition();
		
		List<Point> Goals = goal.getGoalPoints();
		Map<Point, Symbol> startSet = start.getDynamicObjects();
		List<List<Integer>> Cost = new ArrayList<List<Integer>>();			
		List<Integer> DegreeOfOccupied = new ArrayList<Integer>(Goals.size());
		
		/**Create a 2D array to store the cost of box to Goals**/
		for (Point startPt : startSet.keySet()){
			if (isStartSymbol(start.get(startPt)))
			{
				List<Integer> EstimateCost = new ArrayList<Integer>(Goals.size());
				for(Point goalPt : Goals){
					/**If it is available position, calculate the Manhattan Heuristic**/
					if(goal.availablePosition.get(Goals.indexOf(goalPt)).contains(startPt)){
						EstimateCost.add(Math.abs(startPt.x - goalPt.x) + Math.abs(startPt.y - goalPt.y));						
						DegreeOfOccupied.set(Goals.indexOf(goalPt),DegreeOfOccupied.get(Goals.indexOf(goalPt))+1);
					}else{
					/**If no, then it is positive infinity**/
						EstimateCost.add(Integer.MAX_VALUE);
					}
				}			
				Cost.add(EstimateCost);
			}
		}
		/**Greedy Estimation, may overestimated but save Computational Time**/
		List<Integer> FinalEstimateCost = new ArrayList<Integer>(Goals.size());
		for (List<Integer> innerList : Cost) {
		
			int MinValue = Integer.MAX_VALUE;
			int MinIndex = 0;
			for (int i = 0; i < innerList.size(); i++) {
				int value = innerList.get(i);
				/**Fit box to the goals that has the fewest available box to reach**/
				if(value!=Integer.MAX_VALUE){
		        	if(DegreeOfOccupied.get(i)<DegreeOfOccupied.get(MinIndex)){
		        		MinIndex = i;
		        		MinValue = value;
		        	}
		        }
		        	
		    }
			/**If no goal can fit, it is deadlock**/
			if(MinValue == Integer.MAX_VALUE){
				System.out.println("It is deadlock");
			}
			FinalEstimateCost.set(MinIndex, MinValue);
			DegreeOfOccupied.set(MinIndex, DegreeOfOccupied.get(MinIndex)-1);
		}
		
		//FinalEstimateCost is an arraylist that stored the cost for each Box
		/*************************************/
		return utility(start.getDynamicObjects(), goal.getDynamicObjects());
	}
	
	

	@Override
	/** Returns an optimistic estimation of the coast to go from state start to state goal.
	 * 	
	 */
	public float utility(Map<Point, Symbol> start, Map<Point, Symbol> goal)
	{		
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
