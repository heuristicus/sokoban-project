/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import utilities.TestUtil;

/**
 *
 * @author michal
 */
public class StaticBoardTest {
    
    public StaticBoardTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    private void testMinValueCostMap(String testFile, String expectedOutputFile, boolean debug) throws IOException {
    	TestUtil.initBoard(testFile);
		StaticBoard sBoard = StaticBoard.getInstance();
		String[] mapLines = sBoard.toString().split("\n");
		
		for( int y = 0; y < sBoard.grid.length; ++y ) {
			StringBuilder currentLine = new StringBuilder(mapLines[y]);
			for( int x = 0; x < sBoard.grid[y].length; ++x ) {
				Point p = new Point(x, y);
				if(sBoard.get(p) != Symbol.Wall) {
					if (StaticBoard.isLocked(p)) {
						currentLine.setCharAt(x, 'X');
					} else {
						int minValue = Integer.MAX_VALUE;
						for (Point key : sBoard.goalDistanceCost.get(p).keySet()) {
							minValue = Math.min(minValue, sBoard.goalDistanceCost.get(p).get(key));
						}
						currentLine.setCharAt(x, Character.forDigit(minValue, 30)); // After 9, it goes in ascii order from a
					}
				}
			}
			mapLines[y] = currentLine.toString();	
		}
		
		if (debug) {
			StringBuilder sb = new StringBuilder();
			for( int i = 0; i < mapLines.length; ++i) {
				sb.append(mapLines[i]).append('\n');
			}
			System.out.println(sb.toString());			
		}
		
		List<String> expectedOutput = Files.readAllLines(Paths.get(BoardTest.testMapDir, expectedOutputFile), Charset.defaultCharset());
		assertEquals(expectedOutput, Arrays.asList(mapLines));
    }

	@Test
	public void testComputeCosts() {
		final String TEST_FILE = "searchTestStart.map";
        TestUtil.initBoard(TEST_FILE);
        Map<Point, Map<Point, Integer>> expectedResult = new HashMap<>();
        Point goal1 = new Point(6,1);
        Point goal2 = new Point(6,2);
        
        // Costs for reachable points
        for (int x = 2; x <= 6; ++x) {
        	for (int y = 1; y <= 3; ++y) {
        		Point p = new Point(x,y);
        		if (expectedResult.get(p) == null) expectedResult.put(p, new HashMap<Point, Integer>());
        		expectedResult.get(p).put(goal1, Math.abs(x-goal1.x) + Math.abs(y-goal1.y));
        		if (y >= 2) expectedResult.get(p).put(goal2, Math.abs(x-goal2.x) + Math.abs(y-goal2.y));
        	}
        }
                
        assertEquals(expectedResult,StaticBoard.getInstance().goalDistanceCost);
	}
	
	@Test
	@SuppressWarnings("serial")
	public void testMapDeepEquals() {
		// Check that equals works as expected on maps, even with nested ones.
		assertEquals(new HashMap<Point, Map<Point, Integer>>(){{
				put(new Point(2,1), new HashMap<Point, Integer>() {{
					put(new Point(4,1), 3);
					put(new Point(4,2), 8);
				}});
				put(new Point(3,1), new HashMap<Point, Integer>() {{
					put(new Point(4,1), 3);
					put(new Point(4,2), 0);
				}});
			}},
			new HashMap<Point, Map<Point, Integer>>(){{
				put(new Point(3,1), new HashMap<Point, Integer>() {{
					put(new Point(4,2), 0);
					put(new Point(4,1), 3);
				}});
				put(new Point(2,1), new HashMap<Point, Integer>() {{
					put(new Point(4,2), 8);
					put(new Point(4,1), 3);
				}});
			}});
	}
	
	@Test
	public void testCostMapMinValue() throws IOException {
		testMinValueCostMap("../test100/test000.in", "test000.costs.map", false);
	}
	
	@Test
	public void testCostMapMinValue1() throws IOException {
		testMinValueCostMap("testCosts.map", "testCosts.expected.map", false);
	}
	
	@Test
	public void testCostMapMinValue2() throws IOException {
		testMinValueCostMap("testCosts2.map", "testCosts2.expected.map", false);
	}
	
	
	class HackableBoard extends Board {

		public HackableBoard(Board original) {
			super(original);
		}
		
		public void set(Point p, Symbol s) {
			mObjects.put(p, s);
		}
		
	}
	
	@Test
	public void displayCostMap() {
//		final String TEST_FILE = "../test100/test000.in";
		final String TEST_FILE = "tiny.map";
		HackableBoard board = new HackableBoard(TestUtil.initBoard(TEST_FILE));
		StaticBoard sBoard = StaticBoard.getInstance();
		for( int y = 0; y < sBoard.grid.length; ++y ) {
			for( int x = 0; x < sBoard.grid[y].length; ++x ) {
				Point p = new Point(x, y);
				if(sBoard.get(p) != Symbol.Wall) {
					if (StaticBoard.isLocked(p)) {
						board.set(p, Symbol.Mark);
					}
				}
			}
			
		}
		System.out.println(board.toString());
	}

	@Test
    public void testInit() {
        TestUtil.initBoard("testPathCost.map");
        Map<Point, Map<Point, Integer> > costs = StaticBoard.getInstance().goalDistanceCost;
        for (Point point : StaticBoard.getInstance().goals) {
            Map<Point, Integer> gp = costs.get(point);
            assertTrue(gp.containsKey(point));
            if (point == new Point(1,3)){
                assertEquals(gp.get(new Point(1,3)).intValue(), 0);
                assertEquals(gp.get(new Point(3,1)).intValue(), 4);
            } else if (point == new Point(3,1)){
                assertEquals(gp.get(new Point(1,3)).intValue(), 4);
                assertEquals(gp.get(new Point(3,1)).intValue(), 0);
            }
        }
    }

}
