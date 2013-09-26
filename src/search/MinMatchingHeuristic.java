/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import board.Symbol;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author michal
 */
public class MinMatchingHeuristic implements Heuristic<Board> {

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
