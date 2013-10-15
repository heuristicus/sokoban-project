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
    	
    	SearchNode forwardKey = pickFromQueue(forwards.getOpenList(),keyNode);
    	SearchNode backwardsKey = pickFromQueue(backwards.getOpenList(),keyNode);
    	ArrayList<BoardAction> forwardList = forwardKey.actionUnwind();
    	ArrayList<BoardAction> backwardList = backwardsKey.actionUnwind();
    	
    	//shifting actions onto next state on backwardList
//    	for (int i=0 ; i<backwardList.size() ; i++)
//    	{
//    		backwardList.get(i).action = backwardList.get(i+1).action;
//    	}
    	
//    	backwardList.remove(backwardList.size()-1);
    	
    	//reverting backward sequence
    	Collections.reverse(backwardList);
    	
    	
    	
    	//merging the two lists
    	forwardList.addAll(backwardList);
    	
    	return forwardList;
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
