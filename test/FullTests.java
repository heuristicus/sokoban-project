import org.junit.Test;

import utilities.SokobanUtil;
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
		for (int i = 0; i < 100; ++i) {
			Board start = TestUtil.initBoard("../test100/test000.in");
			System.out.print(SokobanUtil.actionListAsString(Main.solveBoardBidirectional(start)));
		}
	}

}
