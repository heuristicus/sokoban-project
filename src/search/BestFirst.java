/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;


import utilities.BoardAction;

/**
 *
 * @author michal
 */
public class BestFirst extends SearchMethod {

    Heuristic<Board> h;

    public BestFirst(Heuristic<Board> h) {
        this.h = h;
    }
    
    @Override
    public ArrayList<BoardAction> findPath(Board start, Board goal, boolean boardSpace) {
        Queue<SearchNode> open = new PriorityQueue<>();
        HashSet<SearchNode> closed = new HashSet<>();
        
        SearchNode first = new SearchNode(start, null, null, 0, (int) h.utility(start, goal), boardSpace);
        open.add(first);
        
        while (!open.isEmpty()){
            
            SearchNode front = open.remove();
            
            closed.add(front);
            ArrayList<SearchNode> successors = front.expand();
            
            // Quick check to see if any of the successors are goals. Don't need
            // to look at others if the goal is present.
            for (SearchNode successor : successors) {

                if (successor.getNodeState().isSolved()){
                    return successor.actionUnwind();
                }
            }
            
            for (SearchNode successor : successors) {
                if (!open.contains(successor) && !closed.contains(successor)){
                    successor.estimatedCost = (int) h.utility(successor.nodeState, goal);
                    open.add(successor);
                }
            }
            
        }
        
        return null;
    }
    
}
