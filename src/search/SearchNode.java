/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;


// T is something which represents the state of the node, U is the set of actions
// which can be taken in any given state. The node represents a distinct state of the
// world which was reached through a set of actions which can be recovered by using
// the unwind method

/**
 *
 * @author michal
 */
public class SearchNode<T,U> implements Comparable<SearchNode<T,U>>{

    protected Float pathCost;
    protected Float estimatedCost;
    protected T nodeState;
    protected U generatingAction;
    protected SearchNode<T,U> parent;
    
    /**
     * Initialise a SearchNode with the given node state, parent node and generating action.
     * Node cost is set to zero.
     * @param nodeState The state which this node represents
     * @param parent The parent node of this state. If this is the root node, this parameter
     * should be null.
     * @param generatingAction The action which generated this state when applied to the
     * previous state. If this is the root node, this parameter should be null.
     */
    public SearchNode(T nodeState, SearchNode<T,U> parent, U generatingAction) {
        this.nodeState = nodeState;
        this.parent = parent;
        this.generatingAction = generatingAction;
        pathCost = null;
    }
    
    /**
     * Initialise a SearchNode with the given node state, parent node, generating action and cost.
     * @param nodeState The state which this node represents
     * @param parent The parent node of this state. If this is the root node, this parameter
     * should be null.
     * @param generatingAction The action which generated this state when applied to the
     * previous state. If this is the root node, this parameter should be null.
     * @param cost The cost to get to this node !FROM THE START NODE!
     */
     public SearchNode(T nodeState, SearchNode<T,U> parent, U generatingAction, float pathCost) {
        this.nodeState = nodeState;
        this.parent = parent;
        this.generatingAction = generatingAction;
        this.pathCost= pathCost;
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
     */
     public SearchNode(T nodeState, SearchNode<T,U> parent, U generatingAction, float pathCost, float estimatedCost) {
        this.nodeState = nodeState;
        this.parent = parent;
        this.generatingAction = generatingAction;
        this.pathCost= pathCost;
        this.estimatedCost = estimatedCost;
    }
     
     
     /*
      * Get the path taken to reach the given node by following the parent nodes
      * until a null pointer is found - this indicates the root of the tree.
      */
     public ArrayList<SearchNode<T,U>> nodeUnwind(){
         if (this.parent == null){
             // Base case. If the parent is null we reach the initial state.
             // Construct a path vector and then return the empty vector.
             ArrayList<SearchNode<T,U>> path = new ArrayList<>();
             path.add(this);
             return path;
         } else {
             // Recursive case. Get the unwound path from the parent and then
             // push the action to get to this node onto the path.
             ArrayList<SearchNode<T,U>> path = this.parent.nodeUnwind();
             path.add(this);
             return path;
         }
     }
     
     /*
      * Get the path taken to reach the given node by following the parent nodes
      * until a null pointer is found - this indicates the root of the tree.
      */
     public ArrayList<U> actionUnwind(){
         if (this.parent == null){
             // Base case. If the parent is null we reach the initial state.
             // Construct a path vector and then return the empty vector.
             ArrayList<U> path = new ArrayList<>();
             path.add(this.generatingAction);
             return path;
         } else {
             // Recursive case. Get the unwound path from the parent and then
             // push the action to get to this node onto the path.
             ArrayList<U> path = this.parent.actionUnwind();
             path.add(this.generatingAction);
             return path;
         }
     }
     

    public T getNodeState() {
        return nodeState;
    }

    /**
     * Nodes are assumed to be equal if the states they contain are identical.
     * Uses the equals method of the T class.
     * @param node
     * @return 
     */
    public boolean equals(SearchNode<T,U> node) {
        return this.nodeState.equals(node.nodeState);
    }
    
    @Override
    public int compareTo(SearchNode<T,U> o) {
        if (this.estimatedCost == o.estimatedCost)
            return 0;
        return this.estimatedCost < o.estimatedCost ? -1 : 1;
    }

    @Override
    public String toString() {
        return this.nodeState.toString() + ", cost " + this.pathCost;
    }
    
}