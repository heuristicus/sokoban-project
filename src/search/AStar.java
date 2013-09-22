/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * An A* implementation. Some assumptions are made:
 * The expand function of T should be such that the path cost of each successor is
 * initialised with the cost of the parent plus the cost to get from the parent
 * to the successor
 * @author michal
 * @param <T> A type which has some function for generating successors
 * @param <U> A type (usually an enum) which specifies the actions that can be
 * applied to the T type
 */
public class AStar<T extends Expandable<T, U>, U, S> extends SearchMethod<T, U>{

    Heuristic<T> h;
    
    /**
     * Basic constructor
     * @param h The heuristic to use for state evaluation
     */
    AStar(Heuristic<T> h){
        this.h = h;
    }
    
    @Override
    public ArrayList<U> findPath(T start, T goal) {
        // Store already visited nodes
        HashSet<SearchNode<T,U>> closed = new HashSet<>();
        // Store as yet unvisited nodes in a priority queue - we expand from the best
        Queue<SearchNode<T,U>> open = new PriorityQueue<>();
        
        // Add the start state as a node with zero path cost
        open.add(new SearchNode<T, U>(start, null, null, 0, h.utility(start, goal)));
        
        while(!open.isEmpty()){
            SearchNode<T,U> front = open.remove(); // The best node in the queue
            // If front is the goal, return the action sequence.
            if (front.nodeState.equals(goal)){
                return front.actionUnwind();
            }
            
            // Add the parent to the closed list - we do not need to expand it more than once
            if (!closed.contains(front)){ // #TODO Is this check really necessary?
                closed.add(front);
            }
            
            ArrayList<SearchNode<T,U>> successors = front.expand();
            for (SearchNode<T, U> successor : successors) {
                // Look through the open list to see if the successor is
                // already present
                Iterator<SearchNode<T,U>> it = open.iterator();
                while(it.hasNext()){
                    SearchNode<T,U> element = it.next();
                    if (element.equals(successor) && element.pathCost > successor.pathCost){
                        // If the current state is in the open list and the path cost to that
                        // state node is greater than that of the path cost to this node,
                        // remove it from the open list - the path via this node is better
                        it.remove();
                        break;
                    }
                }
                // If neither of the open or closed lists contains the successor, then
                // we compute the estimated cost to get to the goal from this node state,
                // add it to the path cost and then add the node to the open list
                if (!open.contains(successor) && !closed.contains(successor)){
                    successor.estimatedCost = successor.pathCost + h.utility(successor.nodeState, goal);
                    open.add(successor);
                }
            }
        }
        
        // Went through all nodes without finding the goal - there is no path.
        return null;
    }

}
