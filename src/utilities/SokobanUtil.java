/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import board.Board;
import board.Symbol;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author michal
 */
public class SokobanUtil {
    
    public enum Action { UP, DOWN, LEFT, RIGHT };
    
    /**
     * Returns the inverse of the given action object.
     * @param a The action to invert
     * @return The action needed undo the given action
     */
    public static Action inverseAction(Action a){
        switch(a){
            case UP: return Action.DOWN;
            case DOWN: return Action.UP;
            case LEFT: return Action.RIGHT;
            case RIGHT: return Action.LEFT;
            default: return null;
        }
    }
    
    /**
     * See which points around a given point on the specified board are accessible, that is,
     * are not blocked by something.
     * @param p A point on the board.
     * @param b A board.
     * @return A list of points with distance 1 from the original which are not blocked.
     */
    public static ArrayList<Point> accessiblePositions(Point p, Board b){
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * @param aGrid
     * @return an ascii representation of the grid.
     */
    public static String stringifyGrid(Symbol[][] aGrid) {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < aGrid.length; y++) {
			for (int x = 0; x < aGrid[y].length; x++) {
				sb.append(aGrid[y][x]);
			}
			sb.append('\n');
		}
		
		return sb.toString();
	}
    
}
