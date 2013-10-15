/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import utilities.BoardAction;

/**
 *
 * @author michal
 */
public class Bidirectional extends SearchMethod {

    MemoSearchMethod forwards;
    MemoSearchMethod backwards;
    
    public Bidirectional(MemoSearchMethod forwards, MemoSearchMethod backwards) {
        this.forwards = forwards;
        this.backwards = backwards;
    }

    @Override  
    public ArrayList<BoardAction> findPath()
    {
    	MemoSearchMethod currentSide = forwards;
    	MemoSearchMethod oppositeSide = backwards;
    	
    	
    	ArrayList<SearchNode> newNodes;
    	SearchNode keyNode;
    	do
    	{
    		//inverting roles
    		MemoSearchMethod tmp = oppositeSide;
    		oppositeSide = currentSide;
    		currentSide = tmp;
    		
    		//doing step
    		newNodes = currentSide.step();
    		keyNode = checkGoal(newNodes, oppositeSide.getOpenList());
    	}while(keyNode == null);
    	
    	//constructing BoardAction list
    	ArrayList<BoardAction> result = new ArrayList<>();
    	
    	SearchNode forwardKey = pickFromQueue(forwards.getOpenList(),keyNode);
    	SearchNode backwardsKey = pickFromQueue(backwards.getOpenList(),keyNode);
    	LinkedList<BoardAction> forwardList = new LinkedList<BoardAction>(forwardKey.actionUnwind());
    	LinkedList<BoardAction> backwardList = new LinkedList<BoardAction>( backwardsKey.actionUnwind());
    	
    	//removing one of the two keys
    	backwardList.removeLast();
    	
    	//reverting backward sequence
    	Collections.reverse(backwardList);
    	
    	//merging the two lists
    	forwardList.addAll(backwardList);
    	
    	return new ArrayList<>(forwardList);
    }
    
    public static SearchNode checkGoal(ArrayList<SearchNode> newNodes, Queue<SearchNode> openList)
    {
    	for (SearchNode newNode : newNodes)
    	{
    		if (openList.contains(newNode))
    			return newNode;
    	}
    	return null;
    }
    
    
    public static SearchNode pickFromQueue(Queue<SearchNode> queue, SearchNode matching)
    {
    	for (SearchNode comp : queue)
    	{
    		if (comp.equals(matching))
    		{
    			return comp;
    		}
    	}
    	return null;
    }
    
}
