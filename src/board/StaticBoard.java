package board;

import java.awt.Point;
import java.util.Collections;
import java.util.List;

import utilities.SokobanUtil;

public class StaticBoard {

	private static StaticBoard INSTANCE = null;
	
	/** Warning, grid[y][x] is the value at the point (x,y)*/
	public final Symbol[][] grid; 
	public final List<Point> goals;
	
	public static void init(Symbol[][] grid, List<Point> goals) {
		INSTANCE = new StaticBoard(grid, goals);
	}
	
	public static StaticBoard getInstance() {
		if (INSTANCE == null) throw new RuntimeException("The static map has not been initialized");
		return INSTANCE;
	}
	
	private StaticBoard(Symbol[][] grid, List<Point> goals) {
		this.grid = grid;
		this.goals = Collections.unmodifiableList(goals);
	}
	
	public Symbol get(Point point) {
		return get(point.x, point.y);
	}
	
	public Symbol get(int x, int y) {
		return grid[y][x];
	}
	
	/** @return Ascii art representation of the map (static only) */
	public String toString() {
		return SokobanUtil.stringifyGrid(grid);
	}
	
}
