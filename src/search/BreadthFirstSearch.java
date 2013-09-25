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
public class BreadthFirstSearch extends SearchMethod {

    @Override
    public ArrayList<Action> findPath(Board start, Board goal) {
        Queue<SearchNode<Board,Action>> list = new LinkedList<>();
        if (start == null || goal == null)
            return null;
        list.add(new SearchNode<>(start, null, null)); // Put the initial state onto the queue
        while(!list.isEmpty()){
            SearchNode<Board,Action> front = list.remove();
            if(goal.equals(front.getNodeState())){ // Check if the popped node is the goal state
                return front.actionUnwind(); // If so, return the list of actions taken to get to this point
            } else { // Front node is not the goal
                list.addAll(front.expand()); // Expand the nodes to generate successors, and then add to the list
            }
        }
        return null; // Tried searching, but the list ended up being empty - there was no path.
    }
}	