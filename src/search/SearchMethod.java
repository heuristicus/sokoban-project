package search;

import board.Board;
import java.util.ArrayList;
import utilities.SokobanUtil.Action;


// T is an object which is used to represent the state. U should represent the set of actions which can be taken
// in any state T.
// The successor function should return a vector of nodes which contains all states reachable in a single
// action from the state which is represented by the node passed to the function. If step or action costs are
// to be taken into account, the cost should be non-zero.
public abstract class SearchMethod
{

    public SearchMethod() {}
    
    /**
     * Find the path between two board states. The implementation
     * is defined by classes extending this interface.
     * @param start The state of the board from which to start searching.
     * @param goal The state of the board which should be found
     * @param boardSpace Determines whether to search in the board space or the 
     * player motion space. In the board space, it is assumed that the player can
     * teleport to anywhere in its accessible area, so each subsequent state has
     * a box in a different place. In the player motion space, the player is unable
     * to teleport, and so the individual motions of the player are considered.
     * @return A path from the start state to the goal state, in reverse order.
     */
    public abstract ArrayList<Action> findPath(Board start, Board goal, boolean boardSpace);
    
}