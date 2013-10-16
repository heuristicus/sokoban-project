import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import utilities.SokobanUtil;
import utilities.SokobanUtil.Action;
import utilities.TestUtil;
import board.Board;

import static org.junit.Assert.*;


public class FullTests {

	@Test
	public void test000() {
		Board start = TestUtil.initBoard("../test100/test000.in");
		System.out.print(SokobanUtil.actionListAsString(Main.solveBoardBidirectional(start)));
	}
	
	@Test
	public void test100Maps() {
		final int startMap = 0;
		final int nbTests = 100 - startMap;
		
		
		Boolean[] results = new Boolean[nbTests];
		for (int i = startMap; i < nbTests; ++i) {
			Board start = TestUtil.initBoard(String.format("../test100/test%03d.in", i));
			Board copy = new Board(start);
			List<Action> moves = Main.solveBoardBidirectional(start);
			System.out.println("Board " + i + ": " + SokobanUtil.actionListAsString(moves));
			results[i] = copy.applyActionChained(moves, true).isSolved();
			
		}
		Boolean[] expected = new Boolean[nbTests];
		Arrays.fill(expected, true);
		assertArrayEquals(expected, results);
	}

}
