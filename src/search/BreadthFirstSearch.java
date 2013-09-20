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
public class BreadthFirstSearch<T extends Expandable<T,U>, U> extends SearchMethod<T,U> {

    @Override
    public ArrayList<U> findPath(T start, T goal) {
        Queue<SearchNode<T,U>> list = new LinkedList<>();
        if (start == null || goal == null)
            return null;
        list.add(new SearchNode<>(start, null, null)); // Put the initial state onto the queue
        while(!list.isEmpty()){
            SearchNode<T,U> front = list.remove();
            if(goal.equals(front.getNodeState())){ // Check if the popped node is the goal state
                return front.actionUnwind(); // If so, return the list of actions taken to get to this point
            } else { // Front node is not the goal
                list.addAll(front.expand()); // Expand the nodes to generate successors, and then add to the list
            }
        }
        return null; // Tried searching, but the list ended up being empty - there was no path.
    }
}	