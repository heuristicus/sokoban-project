/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import java.util.ArrayList;
import search.SearchMethod.Direction;
import utilities.BoardAction;


// T is something which represents the state of the node, U is the set of actions
// which can be taken in any given state. The node represents a distinct state of the
// world which was reached through a set of actions which can be recovered by using
// the unwind method

/**
 *
 * @author michal
 */
public class SearchNode implements Comparable<SearchNode>{

    protected Integer pathCost;
    protected Integer estimatedCost;
    protected Board nodeState;
    protected BoardAction generatingAction;
    protected SearchNode parent;
    protected boolean boardSpaceExpansion;
    
    /**
     * Initialise a SearchNode with the given node state, parent node and generating action.
     * Node cost is set to zero.
     * @param nodeState The state which this node represents
     * @param parent The parent node of this state. If this is the root node, this parameter
     * should be null.
     * @param generatingAction The action which generated this state when applied to the
     * previous state. If this is the root node, this parameter should be null.
     * @param boardSpaceExpansion Defines whether this node stores a player motion space
     * expansion or a board space expansion. This defines which method of the board class is used
     * when the equals method of this class is called.
     */
    public SearchNode(Board nodeState, SearchNode parent, BoardAction generatingAction, boolean boardSpaceExpansion) {
        this(nodeState, parent, generatingAction, null, boardSpaceExpansion);
    }
    
    /**
     * Initialise a SearchNode with the given node state, parent node, generating action and cost.
     * @param nodeState The state which this node represents
     * @param parent The parent node of this state. If this is the root node, this parameter
     * should be null.
     * @param generatingAction The action which generated this state when applied to the
     * previous state. If this is the root node, this parameter should be null.
     * @param cost The cost to get to this node !FROM THE START NODE!
     * @param boardSpaceExpansion Defines whether this node stores a player motion space
     * expansion or a board space expansion. This defines which method of the board class is used
     * when the equals method of this class is called.
     */
     public SearchNode(Board nodeState, SearchNode parent, BoardAction generatingAction, Integer pathCost, boolean boardSpaceExpansion) {
         this(nodeState, parent, generatingAction, pathCost, null, boardSpaceExpansion);
    }
    
         /**
     * Initialise a SearchNode with the given node state, parent node, generating action and cost.
     * @param nodeState The state which this node represents
     * @param parent The parent node of this state. If this is the root node, this parameter
     * should be null.
     * @param generatingAction The action which generated this state when applied to the
     * previous state. If this is the root node, this parameter should be null.
     * @param cost The cost to get to this node !FROM THE START NODE!
     * @param estimatedCost The estimated cost to get from the start to the goal via this node,
     * calculated by adding a heuristic estimate of the distance to the goal from this node to the
     * pathCost
     * @param boardSpaceExpansion Defines whether this node stores a player motion space
     * expansion or a board space expansion. This defines which method of the board class is used
     * when the equals method of this class is called.
     */
     public SearchNode(Board nodeState, SearchNode parent, BoardAction generatingAction, Integer pathCost, Integer estimatedCost, boolean boardSpaceExpansion) {
        this.nodeState = nodeState;
        this.parent = parent;
        this.generatingAction = generatingAction;
        
        if (parent == null)
        	this.pathCost = pathCost;
        else
        	this.pathCost= pathCost + parent.pathCost;
        
        this.estimatedCost = estimatedCost;
        this.boardSpaceExpansion = boardSpaceExpansion;
    }
     
     
     /*
      * Get the path taken to reach the given node by following the parent nodes
      * until a null pointer is found - this indicates the root of the tree.
      */
     public ArrayList<SearchNode> nodeUnwind(){
         if (this.parent == null){
             // Base case. If the parent is null we reach the initial state.
             // Construct a path vector and then return the empty vector.
             ArrayList<SearchNode> path = new ArrayList<>();
             path.add(this);
             return path;
         } else {
             // Recursive case. Get the unwound path from the parent and then
             // push the action to get to this node onto the path.
             ArrayList<SearchNode> path = this.parent.nodeUnwind();
             path.add(this);
             return path;
         }
     }
     
     /*
      * Get the path taken to reach the given node by following the parent nodes
      * until a null pointer is found - this indicates the root of the tree.
      */
     public ArrayList<BoardAction> actionUnwind(){
         if (this.parent == null){
             // Base case. If the parent is null we reach the initial state.
             // Construct a path vector and then return the empty vector.
             ArrayList<BoardAction> path = new ArrayList<>();
             return path;
         } else {
             // Recursive case. Get the unwound path from the parent and then
             // push the action to get to this node onto the path.
             ArrayList<BoardAction> path = this.parent.actionUnwind();
             path.add(this.generatingAction);
             return path;
         }
     } 
     
     /**
      * Expand this node and get a list of its successor nodes.
      * @return 
      */
     public ArrayList<SearchNode> expand(){
         return expand(Direction.FORWARDS);
     }
     
     /**
      * Expand this node and get a list of its successor nodes. The actual expansion
      * is done by the board class.
      * @param searchDirection The direction in which the search is being done.
      * @return A list of SearchNode which contains the expanded states with the
      * expansion done in the requested direction.
      */
     public ArrayList<SearchNode> expand(Direction searchDirection){
         if (searchDirection == Direction.FORWARDS){
             if (boardSpaceExpansion){
                 return this.nodeState.expandBoardSpace(this);
             } else {
                 return this.nodeState.expandPlayerMoves(this);
             }
         } else if (searchDirection == Direction.BACKWARDS){
             if (boardSpaceExpansion){
                 return this.nodeState.expandBoardSpaceBackwards(this);
             } else {
                 return null; // No reverse player expansion yet!
//                 return this.nodeState.expandPlayerMoves(this);
             }
         } else {
             return null;
         }
         
     }
     
    public Board getNodeState() {
        return nodeState;
    }

    /**
     * Nodes are equal if they contain the same state
     * 2 modes: 
     * 	- boardSpaceExpansion mode: checks using {@link Board#equalsHash(Object)}
     * (top left player position check)
     *  - regular mode: checks using {@link Board#equals(Object)} (real player position check)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SearchNode){
            SearchNode node = (SearchNode)obj;
            if (boardSpaceExpansion){
//                return this.nodeState.equalsPlayerFill(node.nodeState);
                return this.nodeState.equalsHash(node.nodeState);
            } else {
                return this.nodeState.equals(node.nodeState);
            }
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return nodeState.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }
        
    @Override
    public int compareTo(SearchNode o) {
        if (this.estimatedCost == o.estimatedCost)
            return 0;
        return this.estimatedCost < o.estimatedCost ? -1 : 1;
    }

    @Override
    public String toString() {
        return this.nodeState.toString() + ", cost " + this.pathCost + ", estimated cost " + this.estimatedCost;
    }

    /**
     * Same as {@link SearchNode#equals(Object)}, except that in
     * boardSpaceExpansion mode, the player location is totally irrelevant
     * (not even the top left position)
     */
	public boolean goalEquals(SearchNode obj) {
		if (obj instanceof SearchNode){
            SearchNode node = (SearchNode)obj;
            if (boardSpaceExpansion){
            	return this.nodeState.equalsGoalHash(node.nodeState);
	        } else {
        		return this.nodeState.equals(node.nodeState);
	        }
        }
        return false;
	}
    
}