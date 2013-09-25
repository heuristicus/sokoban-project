/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import java.util.ArrayList;
import java.util.Stack;
import utilities.SokobanUtil.Action;

/**
 *
 * @author michal
 */
public class DepthFirstSearch extends SearchMethod {
    
    @Override
    public ArrayList<Action> findPath(Board start, Board goal, boolean boardSpace) {
        Stack<SearchNode> list = new Stack<>();
        if (start == null || goal == null){
            return null;
        }
        list.add(new SearchNode(start, null, null));
        while(!list.empty()){
            SearchNode top = list.pop(); // Look at the top node on the stack
            if (goal.equals(top.nodeState)){ // Check if top is the goal
                return top.actionUnwind(); // Goal, so backtrace the path to get to it
            } else { // Not the goal, expand the node and push onto the stack
                list.addAll(top.expand());
            }
        }
        return null; // The list was empty and no goal was found - there is no path.
    }
}
