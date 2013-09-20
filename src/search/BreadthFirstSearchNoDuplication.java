/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author michal
 * @param <T> 
 * @param <U> 
 */
public class BreadthFirstSearchNoDuplication<T extends Expandable<T,U>, U> extends SearchMethod<T, U> {
    
    @Override
    public ArrayList<U> findPath(T start, T goal) {
            // Queue for nodes not yet checked.
            Queue<SearchNode<T,U>> open = new LinkedList<>();
            // List of nodes which have already been checked, independent of the action taken to reach the state.
            ArrayList<SearchNode<T,U>> closed = new ArrayList<>();
            open.add(new SearchNode<T,U>(start, null, null)); // Push the start node onto the queue
            while(!open.isEmpty()){
                // Retrieve and remove the front of the queue
                SearchNode<T,U> front = open.remove();
                if(goal.equals(front.nodeState)){ // If the front node is the goal, return the path to it
                    return front.actionUnwind();
                } else { // Not the goal, need to expand the node to look further
                    // Look through the closed list to see if the node has already been expanded.
                    // If it is already expanded, then just move onto the next loop.
                    if (!closed.contains(front)){
                        // The front node has not yet been expanded. Expand it
                        ArrayList<SearchNode<T,U>> expanded = front.expand();
                        // Go through the closed list again for each successor
                        for (SearchNode<T, U> successor : expanded) {
                            if (!closed.contains(successor)){
                                open.add(successor);
                            }
                        }
                        // Push the expanded node onto the closed list.
                        closed.add(front);
                    }
                }
            }
            
            // Only reach this point if the open list was empty - no goal was found.
            return null;
        }
}
