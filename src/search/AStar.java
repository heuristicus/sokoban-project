/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;

import utilities.BoardAction;
import board.Board;


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
public class AStar extends MemoSearchMethod {

    Heuristic<Board> h;
    Board start;
    Board goal;
    SearchNode goalState;
    SearchNode endPoint;
    Direction searchDirection;
    boolean boardSpace;
    HashSet<SearchNode> closed = new HashSet<>();
    public boolean printTrace = false;
    
    /**
     * Basic constructor
     * @param h The heuristic to use for state evaluation
     */
    public AStar(Board start, Board end, Heuristic<Board> h, boolean boardSpace){
        this(start, end, h, Direction.FORWARDS, boardSpace);
    }
    
    public AStar(Board start, Board goal, Heuristic<Board> h, Direction searchDirection, boolean boardSpace){
        super();
        this.h = h;
        this.searchDirection = searchDirection;
        this.start = start;
        this.goal = goal;
        this.boardSpace = boardSpace;
        
        open = new PriorityQueue<>();
        closed = new HashSet<>();
        goalState = new SearchNode(goal, null, null, boardSpace);
        endPoint = null;
        
        // Add the start state as a node with zero path cost
        open.add(new SearchNode(start, null, null, 0, (int) h.utility(start, goal), boardSpace));
    }

    @Override
    public ArrayList<SearchNode> step() {
            SearchNode front = open.remove(); // The best node in the queue

            if (front.equals(goalState)){
                endPoint = front;
                return null;
            }

            if (!closed.contains(front)){ // #TODO Is this check really necessary?
                closed.add(front);
            }
            
            ArrayList<SearchNode> successors = front.expand(searchDirection);
            
            for (SearchNode successor : successors) {
                Iterator<SearchNode> it = open.iterator();
                boolean inOpen = false;
                while(it.hasNext()){
                    SearchNode element = it.next();
                    if (element.equals(successor)){
                        if (element.pathCost > successor.pathCost){
                            // If the current state is in the open list and the path cost to that
                            // state node is greater than that of the path cost to this node,
                            // remove it from the open list - the path via this node is better
                            //System.out.println("open list already contained successor, but this path is better.");
                            it.remove();
                            break;
                        }
                        // we set this if the successor is in the open list, but does
                        // not have a better path cost so that we don't have to check again.
                        // do this after the above check instead of before so that we don't
                        // say that it's present and then remove it.
                        inOpen = true;
                    }
                }
                // If neither of the open or closed lists contains the successor, then
                // we compute the estimated cost to get to the goal from this node state,
                // add it to the path cost and then add the node to the open list
                boolean inClosed = closed.contains(successor);
                if (!inOpen && !inClosed){
                    successor.estimatedCost = successor.pathCost + (int) h.utility(successor.nodeState, goal);
                    open.add(successor);
                }
            }	
            return successors;
    }
        
    @Override
    public ArrayList<BoardAction> findPath() {
        open = new PriorityQueue<>();
        closed = new HashSet<>();
        goalState = new SearchNode(goal, null, null, boardSpace);
        endPoint = null;
        
        while(!open.isEmpty()){
            // Do one step of the search to check the front of the queue
            // and add successors to the open list.
            step(); 
            // Check if the step discovered the endpoint of the search, that is,
            // if the front node was equal to the goal node. If it was found,
            // unwind the path from it.
            if (endPoint != null)
                return endPoint.actionUnwind();
        }
        // Went through all nodes without finding the goal - there is no path.
        return null;
    }

    /**
     * Returns the end point of the search. If the 
     * @return 
     */
    public SearchNode getEndPoint() {
        return endPoint;
    }
    
    

}
