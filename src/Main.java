import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import search.BreadthFirstSearchNoDuplication;
import search.SearchMethod;
import search.SearchNode;
import utilities.SokobanUtil.Action;
import board.Board;
import board.StaticBoard;
import exceptions.IllegalMoveException;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Board board = Board.read(new BufferedReader(new InputStreamReader(System.in)));
		System.out.println("Static map only:");
		System.out.println(StaticBoard.getInstance());
		System.out.println("Dynamic board:");
		System.out.println(board);
        System.out.println("Board.equals(board) ? " + (board.equals(board)));
        

        
        Board tt, tt2;
        try {
            tt = board.applyAction(Action.UP, false);
            tt2 = tt.applyAction(Action.DOWN, false);
            System.out.println("board");
            System.out.println(board);
            System.out.println("tt");
            System.out.println(tt);
            System.out.println("tt2");
            System.out.println(tt2);

            System.out.println("tt==board?" + (tt==board));
            System.out.println("tt.equals(board)?" + (tt.equals(board)));
            System.out.println("tt2.equals(board)?" + (tt2.equals(board)));
            SearchNode<Board,Action> sb = new SearchNode<>(board, null, null);
            SearchNode<Board,Action> st2 = new SearchNode<>(tt2, null, null);
            SearchNode<Board,Action> st = new SearchNode<>(tt, null, null);
            System.out.println("searchnode sb is:");
            System.out.println(sb);
            System.out.println("searchnode st is:");
            System.out.println(st);
            System.out.println("searchnode st2 is:");
            System.out.println(st2);
            System.out.println("sb.equals(st)? " + sb.equals(st));
            System.out.println("sb.equals(st2)? " + sb.equals(st2));
            System.out.println("st.equals(st2)? " + st.equals(st2));
        } catch (IllegalMoveException ex) {
            
        }
        


        
		
		System.out.println("Free neighbours of (4,2): ");
		for (Point p : board.getFreeNeighbours(new Point(4, 2))) {
			System.out.println(p);
		}
		
		System.out.println("All box access points : ");
		for (Point p : board.getBoxAccessPoints()) {
			System.out.println(p);
		}

		System.out.println(board.get(new Point(4, 2)));
		System.out.println(board.get(new Point(4, 3)));
		System.out.println(board.get(new Point(6, 1)));

		board.moveElement(new Point(4, 2), new Point(4, 3));

		System.out.println("Updated board:");
		System.out.println(board);
		System.out.println("Moving player up");
		Board newBoard = null;

        Board start = Board.read(Files.newBufferedReader(Paths.get("./maps/test/searchTestStart.map"), Charset.defaultCharset()));
        Board goal = Board.read(Files.newBufferedReader(Paths.get("./maps/test/searchTestGoal.map"), Charset.defaultCharset()));
        SearchMethod<Board,Action> bfs = new BreadthFirstSearchNoDuplication<>();
        ArrayList<Action> path = bfs.findPath(start, goal);
        if (path != null){
            System.out.println("BFS completed, path length " + path.size());
            for (Action action : path) {
                System.out.print(action);
            }
            System.out.println("");
        } else {
            System.out.println("BFS could not find path.");
        }
        
		// Solution for map zero
		Action sa[] = { Action.RIGHT, Action.UP, Action.RIGHT, Action.UP,
						Action.LEFT, Action.LEFT, Action.LEFT, Action.UP, Action.LEFT,
						Action.LEFT, Action.DOWN, Action.RIGHT, Action.RIGHT,
						Action.RIGHT, Action.RIGHT, Action.DOWN, Action.RIGHT,
						Action.UP, Action.DOWN, Action.LEFT, Action.DOWN, Action.LEFT,
						Action.UP, Action.RIGHT, Action.UP, Action.LEFT, Action.LEFT,
						Action.UP, Action.LEFT, Action.LEFT, Action.DOWN, Action.RIGHT,
						Action.RIGHT, Action.RIGHT, Action.RIGHT };
		ArrayList<Action> solve = new ArrayList<>(Arrays.asList(sa));

		try {
			Board solvedBoard = board.applyActionChained(solve, false);
			System.out.println(solvedBoard);
			newBoard = board.applyAction(Action.UP, false);
			System.out.println(newBoard);
			newBoard.applyAction(Action.RIGHT, true);
			System.out.println(newBoard);
			newBoard.applyAction(Action.UP, true);
			System.out.println(newBoard);
		} catch (IllegalMoveException ex) {
			System.out.println("Could not move player.");
		}

	}
}
