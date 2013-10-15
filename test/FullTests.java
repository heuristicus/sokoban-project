import org.junit.Test;

import utilities.TestUtil;
import board.Board;


public class FullTests {

	@Test
	public void test000() {
		Board start = TestUtil.initBoard("../test100/test000.in");
        Main.solveBoardBidirectional(start);
	}

}
