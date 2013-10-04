package board;

import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import utilities.TestUtil;

public class StaticBoardTest {

	@Test
	public void testComputeCosts() {
		final String TEST_FILE = "readTest1.map";
        TestUtil.initBoard(TEST_FILE);
        assertTrue(new HashMap<Point, Map<Point, Integer>>().equals(StaticBoard.costMap) );
	}

}
