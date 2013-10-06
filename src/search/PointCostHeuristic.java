/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import board.StaticBoard;
import board.Symbol;
import java.awt.Point;
import java.util.List;
import java.util.Map;

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
public class PointCostHeuristic implements Heuristic<Board>{

    @Override
    public float utility(Board start, Board goal) {
        float totalCost = 0;
        Map<Point, Symbol> dynamic = start.getDynamicObjects();
        Map<Point, Map<Point, Integer> > pointCosts = StaticBoard.getInstance().goalDistanceCost;
        // Go through each point in the dynamic portion of the map, ignoring the player.
        for (Point point : dynamic.keySet()) {
            Symbol thisSymbol = start.get(point);
            // We are not interested in the player position.
            if (thisSymbol == Symbol.Player || thisSymbol == Symbol.PlayerOnGoal)
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
