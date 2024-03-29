/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import utilities.BoardAction;
import utilities.ProfilingUtil;

/**
 *
 * @author michal
 */
public class BestFirst extends MemoSearchMethod {

    HashSet<SearchNode> closed;
    List<Board> startPoints;
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
    	this(Arrays.asList(start), goal, h, searchDirection, boardSpace);
    }
    
    public BestFirst(List<Board> multipleStarts, Board goal, Heuristic<Board> h, Direction searchDirection, boolean boardSpace){
        this.h = h;
        this.searchDirection = searchDirection;
        this.startPoints = multipleStarts;
        this.goal = goal;
        this.boardSpace = boardSpace;
        
        init();
    }
    
    
    private void init()
    {
    	open = new PriorityQueue<>();
        closed = new HashSet<>();
        endPoint = null;
        
        // Add the start states as nodes with zero path cost
        for (Board start : startPoints)
        {
        	open.add(new SearchNode(start, null, null, 0, (int) h.utility(start, goal), boardSpace));
        }
    }

    @Override
    public ArrayList<SearchNode> step() {
         
            SearchNode front = open.remove();
            closed.add(front);
            
            ProfilingUtil.expandedNodes++;
            
            ArrayList<SearchNode> successors = front.expand(searchDirection);
            ProfilingUtil.openedNodes += successors.size();
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
    	init();
        
        // Check if start==goal, return an empty list if true
        if (goalState.goalEquals(open.peek())) 
            return new ArrayList<>();
        
        while (!open.isEmpty()){
            // Do one step of the algorithm, adding the front of the queue to the closed
            // list after expanding it, and adding successors to the open list depending
            // on whether or not they were already there.
            ArrayList<SearchNode> successors = step();
            
            // Examine the successors of the front of the queue generated by the
            // step method to see if a goal has been reached.
            for (SearchNode succ : successors) {
               if (goalState.goalEquals(succ)) {
                   return succ.actionUnwind();
               }
            }
            if (ProfilingUtil.checkTimeOut())
                return null;
        }
        
        return null;
    }

    public SearchNode getEndPoint() {
        return endPoint;
    }
    
}
