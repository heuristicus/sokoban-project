import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

import utilities.SokobanUtil;
import utilities.SokobanUtil.Action;
import utilities.TestUtil;
import board.Board;


public class FullTests {

	@Test
	public void test000() {
		Board start = TestUtil.initBoard("../test100/test000.in");
		System.out.print(SokobanUtil.actionListAsString(Main.solveBoardBidirectional(start)));
	}
	
	@Test
	public void test100Maps() {
		final int startMap = 0;
		final int endMap = 10;
		
		final int nbTests = endMap - startMap;
		Boolean[] results = new Boolean[nbTests];
		Map<Integer, String> times = new TreeMap<Integer, String>();
		for (int i = 0; i < nbTests; ++i) {
			Board start = TestUtil.initBoard(String.format("../test100/test%03d.in", i + startMap));
			Board copy = new Board(start);
			List<Action> moves = null;
			

			
			try {
				long startTime = System.nanoTime();
				moves = Main.solveBoardBidirectional(start);				
				long endTime = System.nanoTime();
				times.put(i + startMap, String.format("%.2g",(endTime - startTime) / 10e9));
			} catch(Exception e) {
				System.err.println("Exception on map " + (i + startMap) );
				e.printStackTrace();
			}
			if (moves != null) {
				System.out.println("Board " + (i + startMap) + ": " + SokobanUtil.actionListAsString(moves));
				results[i] = copy.applyActionChained(moves, true).isSolved();				
			} else {
				results[i] = false;
			}
			
		}
		Boolean[] expected = new Boolean[nbTests];
		Arrays.fill(expected, true);
		assertArrayEquals(expected, results);
		System.out.println(times);
	}

}
