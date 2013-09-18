package board;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import utilities.SokobanUtil;
import utilities.SokobanUtil.Action;
import board.Symbol.Type;
import exceptions.IllegalMoveException;

/**
 * Dynamic representation of the world.
 * 
 * Disclaimer: The representation can become totally wrong as the number of box
 * is not checked, neither is the fact that there is only one player, etc. The
 * dynamic map must be modified with care.
 */
public class Board implements Cloneable {
	private final Map<Point, Symbol> mObjects;
	private Point playerPosition;

	private Board(Map<Point, Symbol> dynMap, Point playerPosition) {
		this.mObjects = new HashMap<>(dynMap);
		this.playerPosition = playerPosition;
	}

	public Map<Point, Symbol> getDynamicObjects() {
		return mObjects;
	}

	/** No search needed, the position is now always tracked. */
	public Point getPlayerPosition() {
		return playerPosition;
	}

	/** @return Ascii art representation of the board (static + dynamic) */
	@Override
	public String toString() {
		Symbol[][] grid = StaticBoard.getInstance().grid;
		Symbol[][] gridCopy = new Symbol[grid.length][];
		for (int i = 0; i < grid.length; i++) {
			gridCopy[i] = grid[i].clone();
		}

		for (Point p : mObjects.keySet()) {
			gridCopy[p.y][p.x] = mObjects.get(p);
		}
		return SokobanUtil.stringifyGrid(gridCopy);
	}

	/**
	 * Initializes a new board and the singleton static map.
	 * 
	 * @param inputStreamReader
	 *            input source
	 */
	public static Board read(InputStreamReader inputStreamReader) {
		BufferedReader br = new BufferedReader(inputStreamReader);

		// Step 1: read the input.
		List<String> tmpStrMap = new ArrayList<>();
		String line;
		try {
			while (br.ready()) {
				line = br.readLine();
				tmpStrMap.add(line);
			}
		} catch (IOException e) {
			// Crash the program, there is nothing much to do but give a better
			// input source.
			throw new RuntimeException(e);
		}

		// Step 2: create the structures to be stored.
		// To have the number of lines and initialize the array, it must be done
		// in two steps.
		Symbol[][] staticMap = new Symbol[tmpStrMap.size()][];
		Map<Point, Symbol> dynamicMap = new HashMap<>();
		List<Point> goals = new ArrayList<>();
		Point playerPosition = null;

		for (int y = 0; y < tmpStrMap.size(); ++y) {
			String row = tmpStrMap.get(y);
			Symbol[] outRow = new Symbol[row.length()];
			for (int x = 0; x < row.length(); ++x) {
				Symbol s = Symbol.get(row.charAt(x));
				if (s.type != Symbol.Type.None) {

					outRow[x] = Symbol.get(s.staticValue);
					dynamicMap.put(new Point(x, y), s);

					if (s.type == Symbol.Type.Player) {
						playerPosition = new Point(x, y);
					}
				} else {
					outRow[x] = s;
				}

				if (s == Symbol.Goal || s == Symbol.BoxOnGoal) {
					goals.add(new Point(x, y));
				}
			}

			staticMap[y] = outRow;
		}

		if (playerPosition == null)
			throw new RuntimeException("Player position not detected");

		StaticBoard.init(staticMap, goals);
		return new Board(dynamicMap, playerPosition);
	}

	/**
	 * Returns the Symbol obtained by combining the static and dynamic values at
	 * that point
	 */
	public Symbol get(Point p) {
		if (mObjects.containsKey(p)) {
			return mObjects.get(p);
		} else {
			return StaticBoard.getInstance().get(p);
		}
	}

	/**
	 * Moves the element (box or player only!) from one point to another.
	 * 
	 * @return whether operation was successful
	 */
	public boolean moveElement(Point from, Point to) {
		if (!mObjects.containsKey(from))
			return false;

		Symbol.Type element = mObjects.remove(from).type;
		Symbol destination = StaticBoard.getInstance().get(to);
		Symbol finalState;

		if (destination.isWalkable && !mObjects.containsKey(to)) {
			if (destination == Symbol.Empty) {
				switch (element) {
				case Player:
					finalState = Symbol.Player;
					break;
				case Box:
					finalState = Symbol.Box;
					break;
				default:
					return false;
				}
			} else if (destination == Symbol.Goal) {
				switch (element) {
				case Player:
					finalState = Symbol.PlayerOnGoal;
					break;
				case Box:
					finalState = Symbol.BoxOnGoal;
					break;
				default:
					return false;
				}
			} else {
				return false;
			}

			mObjects.put(to, finalState);
			if (element == Symbol.Type.Player) {
				playerPosition = to;
			}

			return true;
		}

		return false;
	}

	/**
	 * Moves the player in one of the directions according to the chosen action.
	 * If as a result he moves to a box, the box will be pushed.
	 * 
	 * @param a
	 *            The action to be applied to the board
	 * @param destructive
	 *            If true, the action is applied directly to the board that the
	 *            function is called on, destroying the previous board state. If
	 *            false, the board is cloned and the action is applied to the
	 *            cloned board
	 * @throws Exception
	 *             If it is impossible to apply the action (moving into a wall,
	 *             moving into a box that can't be pushed)
	 * @return The original object if destructive is false, a cloned board
	 *         otherwise
	 * */
	public Board applyAction(Action a, boolean destructive)
			throws IllegalMoveException {
		Board newBoard;
		if (destructive) {
			newBoard = this;
		} else {
			newBoard = this.clone();
		}

		Point player = newBoard.getPlayerPosition();

		Point destination = SokobanUtil.applyActionToPoint(a, player);
		Symbol destObject = newBoard.get(destination);
		if (destObject.type == Type.Box) {
			Point boxDestination = SokobanUtil.applyActionToPoint(a,
					destination);
			Symbol boxDest = newBoard.get(boxDestination);
			if (!boxDest.isWalkable) {
				throw new IllegalMoveException();
			}
			// Move the box first, and then the player, so that the box
			// symbol is not overwritten.
			newBoard.moveElement(destination, boxDestination);
			newBoard.moveElement(player, destination);
		} else if (!destObject.isWalkable) {
			throw new IllegalMoveException();
		} else {
			newBoard.moveElement(player, destination);
		}

		return newBoard;
	}

	/**
	 * Applies a series of actions to the board
	 * 
	 * @param aList
	 *            A list of actions to apply
	 * @param destructive
	 *            If true, modify the board state, otherwise use a clone.
	 * @return A board with all actions in the actionList applied.
	 */
	public Board applyActionChained(ArrayList<Action> actionList,
			boolean destructive) throws IllegalMoveException {
		Board newBoard;
		if (destructive) {
			newBoard = this;
		} else {
			newBoard = this.clone();
		}

		for (Action action : actionList) {
			// Don't care about modifying board state anymore, so use the
			// destructive method
			newBoard.applyAction(action, true);
		}

		return newBoard;
	}

	@Override
	public Board clone() {
		return new Board(new HashMap<Point, Symbol>(mObjects), playerPosition);
	}
	
	/** 
	 * Returns which of the points around the one provided are free.
	 * No check is done on whether the point is a box, a wall or anything else.
	 */
	public List<Point> getFreeNeighbours(Point p) {
		List<Point> freeNeighbours = new ArrayList<>();
		for (Action a : Action.values()) {
			Point neighbour = SokobanUtil.applyActionToPoint(a, p);
			if (get(neighbour).isWalkable) {
				freeNeighbours.add(neighbour);
			}
		}
		return freeNeighbours;
	}
	
	/** Returns the all the walkable that allow to be next to a box */
	public Set<Point> getBoxAccessPoints() {
		Set<Point> freeNeighbours = new HashSet<>();
		for (Point p : mObjects.keySet()) {
			if (get(p).type != Symbol.Type.Box) continue;

			for (Action a : Action.values()) {
				Point neighbour = SokobanUtil.applyActionToPoint(a, p);
				if (get(neighbour).isWalkable) {
					freeNeighbours.add(neighbour);
				}
			}
		}
		return freeNeighbours;
	}
	

}
