import java.awt.Point;
import java.io.IOException;
import java.io.InputStreamReader;

import board.Board;
import board.StaticBoard;
import exceptions.IllegalMoveException;
import java.util.ArrayList;
import java.util.Arrays;

import utilities.SokobanUtil.Action;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		Board board = Board.read(new InputStreamReader(System.in));
		System.out.println("Static map only:");
		System.out.println(StaticBoard.getInstance());
		System.out.println("Dynamic board:");
		System.out.println(board);
		
		System.out.println(board.get(new Point(4,2)));
		System.out.println(board.get(new Point(4,3)));
		System.out.println(board.get(new Point(6,1)));
		
		board.moveElement(new Point(4,2), new Point(4, 3));

		System.out.println("Updated board:");
		System.out.println(board);
                System.out.println("Moving player up");
                Board newBoard = null;
                
                // Solution for map zero
                Action sa[] = {Action.RIGHT, Action.UP, Action.RIGHT, Action.UP,
                    Action.LEFT, Action.LEFT, Action.LEFT, Action.UP, Action.LEFT,
                    Action.LEFT, Action.DOWN, Action.RIGHT, Action.RIGHT, Action.RIGHT,
                    Action.RIGHT, Action.DOWN, Action.RIGHT, Action.UP, Action.DOWN, Action.LEFT,
                    Action.DOWN, Action.LEFT, Action.UP, Action.RIGHT, Action.UP, Action.LEFT, Action.LEFT,
                    Action.UP, Action.LEFT,Action.LEFT, Action.DOWN, Action.RIGHT, Action.RIGHT, Action.RIGHT, Action.RIGHT};
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