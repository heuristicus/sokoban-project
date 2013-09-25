/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import utilities.SokobanUtil.Action;

/**
 *
 * @author michal
 */
public class BreadthFirstSearchNoDuplication extends SearchMethod {
    
    @Override
    public ArrayList<Action> findPath(Board start, Board goal) {
            // Queue for nodes not yet checked.
            Queue<SearchNode<Board,Action>> open = new LinkedList<>();
            // List of nodes which have already been checked, independent of the action taken to reach the state.
            ArrayList<SearchNode<Board,Action>> closed = new ArrayList<>();
            open.add(new SearchNode<>(start, null, null)); // Push the start node onto the queue
            while(!open.isEmpty()){
                // Retrieve and remove the front of the queue
//                System.out.println("closed size: " + closed.size());
//                System.out.println("open size: " + open.size());
                SearchNode<Board,Action> front = open.remove();
                if(goal.equals(front.nodeState)){ // If the front node is the goal, return the path to it
                    return front.actionUnwind();
                } else { // Not the goal, need to expand the node to look further
                    // Look through the closed list to see if the node has already been expanded.
                    // If it is already expanded, then just move onto the next loop.
                    if (!closed.contains(front)){
//                        System.out.println("closed does not contain");
//                        System.out.println(front);
                        // The front node has not yet been expanded. Expand it
                        ArrayList<SearchNode<Board,Action>> expanded = front.expand();
                        // Go through the closed list again for each successor
                        for (SearchNode<Board, Action> successor : expanded) {
                            if (!closed.contains(successor)){
                                open.add(successor);
                            }
                        }
                        // Push the expanded node onto the closed list.
//                        System.out.println("adding to closed list:");
//                        System.out.println(front);
                        closed.add(front);
                    }
                }
            }
            // Only reach this point if the open list was empty - no goal was found.
            System.out.println("Open list empty");
            return null;
        }
}
