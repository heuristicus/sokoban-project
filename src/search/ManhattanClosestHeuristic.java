package search;

import java.awt.Point;
import java.util.Map;

import board.Board;
import board.Symbol;


public class ManhattanClosestHeuristic implements Heuristic<Board>
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
