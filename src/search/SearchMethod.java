package search;

import java.util.ArrayList;
import utilities.BoardAction;


// T is an object which is used to represent the state. U should represent the set of actions which can be taken
// in any state T.
// The successor function should return a vector of nodes which contains all states reachable in a single
// action from the state which is represented by the node passed to the function. If step or action costs are
// to be taken into account, the cost should be non-zero.
public abstract class SearchMethod
{

    public enum Direction { FORWARDS, BACKWARDS };
    
    public SearchMethod() {}
    
    /**
     * Find the path between two board states. The implementation
     * is defined by classes extending this class. Should find the path
     * between two states.
     * @param start The state of the board from which to start searching.
     * @param goal The state of the board which should be found
     * @param boardSpace Determines whether to search in the board space or the 
     * player motion space. In the board space, it is assumed that the player can
     * teleport to anywhere in its accessible area, so each subsequent state has
     * a box in a different place. In the player motion space, the player is unable
     * to teleport, and so the individual motions of the player are considered.
     * @return A path from the start state to the goal state, in reverse order.
     */
    public abstract ArrayList<BoardAction> findPath();
    
    /**
     * A function to step through the search one 'level' at a time. This may
     * correspond to a single iteration of a search which uses a loop, or something
     * else if the search does not use a loop. Returns null by default, and should
     * be overwritten by any search method which may require the step functionality.
     * @return A list containing the successors of the front node, which were just
     * added to the open list. If the goal was found, then null is returned.
     */
    public ArrayList<SearchNode> step(){
        return null;
    }
    
   }