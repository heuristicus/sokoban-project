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
    
    public abstract ArrayList<Action> findPath(Board start, Board goal);
    
}