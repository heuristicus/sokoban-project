/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author michal
 * @param <T> 
 * @param <U> 
 */
public class DepthFirstSearch<T extends Expandable<T,U>, U> extends SearchMethod<T,U> {
    
    @Override
    public ArrayList<U> findPath(T start, T goal) {
        Stack<SearchNode<T,U>> list = new Stack<>();
        if (start == null || goal == null){
            return null;
        }
        list.add(new SearchNode<T,U>(start, null, null));
        while(!list.empty()){
            SearchNode<T,U> top = list.pop(); // Look at the top node on the stack
            if (goal.equals(top.nodeState)){ // Check if top is the goal
                return top.actionUnwind(); // Goal, so backtrace the path to get to it
            } else { // Not the goal, expand the node and push onto the stack
                list.addAll(top.expand());
            }
        }
        return null; // The list was empty and no goal was found - there is no path.
    }
}
