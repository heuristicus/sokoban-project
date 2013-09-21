/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinding;

import java.util.ArrayList;
import java.util.HashMap;

import search.SearchMethod;
import utilities.SokobanUtil.Action;
import board.Board;

/**
 *
 * @author michal
 */
public class Pathfinding {

    SearchMethod<Board, Action> search;
    
    /**
     * Creates a pathfinding object which uses the given search method for all
     * searches.
     * @param search A search method which can find a path between two points.
     */
    public Pathfinding(SearchMethod<Board, Action> search) {
        this.search = search;
    }
        
    /**
     * Find a path between the start and goal states.
     * @param start
     * @param goal
     * @return Arraylist of actions which need to be taken to get from the start
     * state to the goal.
     */
    public ArrayList<Action> pointToPointPath(Board start, Board goal){
        return search.findPath(start, goal);
    }
    
    /**
     * Find a path from the start state to multiple goal states.
     * @param start
     * @param goals A list of goal states to which paths are to be found.
     * @return A hashmap associating board states with lists of actions required to
     * get from the start state to a given goal state. Goal states which were not
     * reachable are not included in the hashmap.
     */
    public static HashMap<Board, ArrayList<Action>> pointToMultiPointPath(Board start, ArrayList<Board> goals){
        return MultiGoalBreadthFirstSearch.findPath(start, goals);
    }
    
}
