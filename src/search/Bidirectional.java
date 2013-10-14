/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import java.util.ArrayList;
import utilities.BoardAction;

/**
 *
 * @author michal
 */
public class Bidirectional extends SearchMethod {

    SearchMethod forwards;
    SearchMethod backwards;
    
    public Bidirectional(SearchMethod forwards, SearchMethod backwards) {
        this.forwards = forwards;
        this.backwards = backwards;
    }

    @Override
    public ArrayList<BoardAction> findPath(Board start, Board goal, boolean boardSpace) {
        // A shared list of nodes. Here we store all the nodes which have been explored
        // by both the forwards and backwards searches.        
        ArrayList<SearchNode> shared = new ArrayList<>();
        
        return null;        
    }
    
}
