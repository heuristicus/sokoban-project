/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import utilities.SokobanUtil.Action;
import board.Board;

/**
 *
 * @author michal
 */
public class MultiGoalBreadthFirstSearch {
    
    /**
     * Find a path from the start state to all of the goals in the provided
     * arraylist. If the goals are not accessible, then we do not return paths
     * to them. 
     * @param start The starting state
     * @param goals An arraylist containing all the points to which paths are to be found
     * @return A hashmap where the key is the goal state,
     * and the value is a list of the actions taken to get to that goal state from the
     * start. Goals that are not contained in the hashmap were not reachable.
     */
    public static HashMap<Board, ArrayList<Action>> findPath(Board start, ArrayList<Board> goals) {
        // Queue for nodes not yet checked.
        Queue<SearchNode<Board,Action>> open = new LinkedList<>();
        // List of nodes which have already been checked, independent of the action taken to reach the state.
        HashSet<SearchNode<Board,Action>> closed = new HashSet<>();
        HashMap<Board, ArrayList<Action>> goalNodes = new HashMap<>();
        open.add(new SearchNode<>(start, null, null)); // Push the start node onto the queue
        // Keep going until the open list is empty - we have reached the limits of
        // the reachable area, or until the size of the goal list is equal to the size of
        // the hashmap containing the paths. If they are the same size, then that
        // means we have found a path to every goal, so we stop the search.
        while(!open.isEmpty() && goals.size() != goalNodes.size()){
            // Retrieve and remove the front of the queue
            SearchNode<Board, Action> front = open.remove();
            // If the front node is in the list of goals, we add the node state and
            // the list of actions to reach that state to the hashmap
            if(goals.contains(front.nodeState)){
                goalNodes.put(front.nodeState, front.actionUnwind());
            } else { // Not the goal, need to expand the node to look further
                // Look through the closed list to see if the node has already been expanded.
                // If it is already expanded, then just move onto the next loop.
                if (!closed.contains(front)){
                    // The front node has not yet been expanded. Expand it
                    ArrayList<SearchNode<Board,Action>> expanded = front.expand();
                    // Go through the closed list again for each successor
                    for (SearchNode<Board, Action> successor : expanded) {
                        if (!closed.contains(successor)){
                            open.add(successor);
                        }
                    }
                    // Push the expanded node onto the closed list.
                    closed.add(front);
                }
            }
        }
        return goalNodes;
    }
}