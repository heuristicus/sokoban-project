import java.awt.Point;
import java.io.IOException;
import java.io.InputStreamReader;

import board.Board;
import board.StaticBoard;


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
		
		System.out.println("U R R U");
		
	} // main
} // End Main