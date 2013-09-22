/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import board.Board;
import board.Symbol;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

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

    /**
     * Applies an action to the given point, returning the point which would be
     * moved to if the action was legal.
     * @param a An action
     * @param p The point to which to apply the action
     * @return A new point with the action applied.
     */
    public static Point applyActionToPoint(Action a, Point p){
        switch(a){
            case UP: return new Point(p.x, p.y - 1);
            case DOWN: return new Point(p.x, p.y + 1);
            case LEFT: return new Point(p.x - 1, p.y);
            case RIGHT: return new Point(p.x + 1, p.y);
            default: return null;
        }
    }
    
    public static char actionToString(Action a){
        if (a == null)
            return ' ';
        switch(a){
            case UP: return 'U';
            case DOWN: return 'D';
            case LEFT: return 'L';
            case RIGHT: return 'R';
            default: return ' ';
        }
    }
    
    public static Board readMap(String filename) throws FileNotFoundException{
        return Board.read(new InputStreamReader(new FileInputStream(filename)));
    }
    
    public static String readMapAsString(String filename) throws FileNotFoundException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));

		// Step 1: read the input.
		StringBuilder sb = new StringBuilder();
        String line;
		try {
			while (br.ready()) {
				line = br.readLine();
                sb.append(line);
                sb.append('\n');
			}
		} catch (IOException e) {
			// Crash the program, there is nothing much to do but give a better
			// input source.
			throw new RuntimeException(e);
		}
        return sb.toString();
    }

}
