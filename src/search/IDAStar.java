/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import java.util.ArrayList;
import pathfinding.BoardAction;
import utilities.Pair;

/**
 *
 * @author michal
 */
public class IDAStar extends SearchMethod {

    Heuristic<Board> h;
    
    public IDAStar(Heuristic<Board> h) {
        this.h = h;
    }
    
    int count = 0;

        
    @Override
    public ArrayList<BoardAction> findPath(Board start, Board goal, boolean boardSpace) {
        float lowerBound = h.utility(start, goal);
        
        SearchNode currentNode = new SearchNode(start, null, null, 0, boardSpace);
        SearchNode goalNode = new SearchNode(goal, null, null, boardSpace);
        do {
            // Check to see if we found the goal or a new lower bound
            Pair<SearchNode, Float> result = helper(currentNode, goalNode, lowerBound);
            // if the first item in the pair is non-null, the goal was found.
            if (result.first != null)
                return result.first.actionUnwind();
            // If the lowest bound found in the search was infinite, the search failed.
            if (result.second == Float.POSITIVE_INFINITY)
                return null;
            // If no goal was found and the lowest bound in the search was not infinite,
            // update the bound and do the search again.
            lowerBound = result.second;
            System.out.println("-------------------- LOOP END ---------------------");
        } while (true);
        
    }
    
    /**
     * Does a search to attempt to find a new lower bound for the search, or find
     * a path to the goal within the bound provided.
     * @param node The node from which to do the search
     * @param goal The node which we want to reach
     * @param bound The current lower bound on the search. If the estimate for
     * the node exceeds this value, the function will return.
     * @return If a goal has been found, a pair with a non-null first element,
     * and a zero second element. If a new bound has been found or the bound
     * was exceeded, a pair with a null first element and some floating point value
     * in the second element.
     */
    private Pair<SearchNode, Float> helper(SearchNode node, SearchNode goal, float bound){
        System.out.println("path cost " + node.pathCost);
        System.out.println("utility " + h.utility(node.nodeState, goal.nodeState));
        float cheapestPathEst = node.pathCost + h.utility(node.nodeState, goal.nodeState);
        // If the cheapest path estimate for this node exceeds the bound, return the bound
        System.out.println("bound is " + bound + " cheapest path estimate " + cheapestPathEst);
        if (cheapestPathEst > bound){
            System.out.println("BOUND EXCEEDED");
            return new Pair<>(null, cheapestPathEst);
        }
        // If we found a goal, return a pair with the searchnode filled in - this
        // is the only case where this should happen.
        if (node.equals(goal))
            return new Pair<>(node, 0f);
        // Keep track of the lowest bound.
        float minimum = Float.POSITIVE_INFINITY;
        ArrayList<SearchNode> successors = node.expand();
        System.out.println("Current node");
        System.out.println(node);
        System.out.println("num successors " + successors.size());
        for (SearchNode successor : successors) {
            count++;
            
            // Continue down the tree until a bound exceeding the current one is found,
            // or we reach the goal.
            Pair<SearchNode, Float> ret = helper(successor, goal, bound);
            if (ret.first != null) // Found a goal, so return it
                return ret;
            if (ret.second < minimum) // Didn't find a goal, but updated the minimum bound.
                minimum = ret.second;
        }
        // Return the new minimum bound.
        return new Pair<>(null, minimum);
    }
    
}
