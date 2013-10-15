/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import utilities.BoardAction;

/**
 *
 * @author michal
 */
public class BestFirst extends MemoSearchMethod {

    HashSet<SearchNode> closed;
    Board start;
    Board goal;
    boolean boardSpace;
    SearchNode goalState;
    SearchNode endPoint;
    Heuristic<Board> h;
    Direction searchDirection;
    
    public BestFirst(Board start, Board goal, Heuristic<Board> h, boolean boardSpace) {
        this(start, goal, h, Direction.FORWARDS, boardSpace);
    }
    
    public BestFirst(Board start, Board goal, Heuristic<Board> h, Direction searchDirection, boolean boardSpace){
        this.h = h;
        this.searchDirection = searchDirection;
        this.start = start;
        this.goal = goal;
        this.boardSpace = boardSpace;
    }

    @Override
    public ArrayList<SearchNode> step() {
         
            SearchNode front = open.remove();
            
            closed.add(front);
            ArrayList<SearchNode> successors = front.expand(searchDirection);
            
            // Quick check to see if any of the successors are goals. Don't need
            // to look at others if the goal is present.
            for (SearchNode successor : successors) {

                if (successor.getNodeState().isSolved()){
                    endPoint = successor;
                    return null;
                }
            }
            
            for (SearchNode successor : successors) {
                if (!open.contains(successor) && !closed.contains(successor)){
                    successor.estimatedCost = (int) h.utility(successor.nodeState, goal);
                    open.add(successor);
                }
            }
            return successors;
    }
    
    @Override
    public ArrayList<BoardAction> findPath() {
        open = new PriorityQueue<>();
        closed = new HashSet<>();
        endPoint = null;
        
        SearchNode first = new SearchNode(start, null, null, 0, (int) h.utility(start, goal), boardSpace);
        open.add(first);
        
        while (!open.isEmpty()){
           step();
           
           if (endPoint != null)
               return endPoint.actionUnwind();
           
        }
        
        return null;
    }

    public SearchNode getEndPoint() {
        return endPoint;
    }
    
}
