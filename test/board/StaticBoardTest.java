package board;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import utilities.TestUtil;

public class StaticBoardTest {

	@Test
	public void testComputeCosts() {
		final String TEST_FILE = "searchTestStart.map";
        TestUtil.initBoard(TEST_FILE);
        Map<Point, Map<Point, Integer>> expectedResult = new HashMap<>();
        Map<Point, Integer> goal1Costs = new HashMap<>();
        Map<Point, Integer> goal2Costs = new HashMap<>();
        Point goal1 = new Point(6,1);
        Point goal2 = new Point(6,2);

        // MAX_VALUE everywhere: locked points
        // The walkable area is a rectangle
        for (int x = 1; x <= 6; ++x) {
        	for (int y = 1; y <= 4; ++y) {
        		goal1Costs.put(new Point(x,y), Integer.MAX_VALUE);
        		goal2Costs.put(new Point(x,y), Integer.MAX_VALUE);
        	}
		}
        
        // Costs for reachable points
        for (int x = 2; x <= 6; ++x) {
        	for (int y = 1; y <= 3; ++y) {
        		goal1Costs.put(new Point(x,y), Math.abs(x-goal1.x) + Math.abs(y-goal1.y));
        	}
        }
        for (int x = 2; x <= 6; ++x) {
        	for (int y = 2; y <= 3; ++y) {
        		goal2Costs.put(new Point(x,y), Math.abs(x-goal2.x) + Math.abs(y-goal2.y));
        	}
        }
        
        expectedResult.put(goal1, goal1Costs);
        expectedResult.put(goal2, goal2Costs);
        
        System.out.println(expectedResult);
        
        assertEquals(expectedResult,StaticBoard.getInstance().costMap);
	}
	
	@Test
	public void testComputeCosts2() {
		final String TEST_FILE = "readTest1.map";
		TestUtil.initBoard(TEST_FILE);
		Map<Point, Map<Point, Integer>> expectedResult = new HashMap<>();
		Map<Point, Integer> goal1Costs = new HashMap<>();
		goal1Costs.put(new Point(1,1), Integer.MAX_VALUE);
		goal1Costs.put(new Point(2,1), 0);
		goal1Costs.put(new Point(3,1), Integer.MAX_VALUE);
		goal1Costs.put(new Point(4,1), Integer.MAX_VALUE);
		goal1Costs.put(new Point(5,1), Integer.MAX_VALUE);
		goal1Costs.put(new Point(1,2), Integer.MAX_VALUE);
		goal1Costs.put(new Point(2,2), Integer.MAX_VALUE);
		goal1Costs.put(new Point(3,2), Integer.MAX_VALUE);
		expectedResult.put(new Point(2,1), goal1Costs);
		Map<Point, Integer> goal2Costs = new HashMap<>();
		goal2Costs.put(new Point(1,1), Integer.MAX_VALUE);
		goal2Costs.put(new Point(2,1), 1);
		goal2Costs.put(new Point(3,1), 0);
		goal2Costs.put(new Point(4,1), Integer.MAX_VALUE);
		goal2Costs.put(new Point(5,1), Integer.MAX_VALUE);
		goal2Costs.put(new Point(1,2), Integer.MAX_VALUE);
		goal2Costs.put(new Point(2,2), Integer.MAX_VALUE);
		goal2Costs.put(new Point(3,2), Integer.MAX_VALUE);
		expectedResult.put(new Point(3,1), goal2Costs);
		
		
		assertEquals(expectedResult,StaticBoard.getInstance().costMap);
	}
	
	@Test
	public void testComputeCosts3() {
		final String TEST_FILE = "../test100/test000.in";
		TestUtil.initBoard(TEST_FILE);
		
		// Register the goals and the cost maps
		Map<Point, Map<Point, Integer>> expectedResult = new HashMap<>();
		for (int x = 7; x <= 9; ++x) {
        	for (int y = 7; y <= 9; ++y) {
        		expectedResult.put(new Point(x,y), new HashMap<Point, Integer>());
        	}
		}
    	expectedResult.put(new Point(6,8), new HashMap<Point, Integer>());
    	
    	// They all are in the center of the room, locked points are all the same.
    	for(Point goal : expectedResult.keySet()) {
    		Map<Point, Integer> costs = expectedResult.get(goal);
    		// Main room
    		for (int x = 5; x <= 10; ++x) {
            	for (int y = 6; y <= 10; ++y) {
            		costs.put(new Point(x,y), Integer.MAX_VALUE);
            	}
    		}
    		// rest of the points
    		costs.put(new Point(1,1), Integer.MAX_VALUE);
    		costs.put(new Point(1,2), Integer.MAX_VALUE);
    		costs.put(new Point(1,3), Integer.MAX_VALUE);
    		costs.put(new Point(2,3), Integer.MAX_VALUE);
    		costs.put(new Point(3,3), Integer.MAX_VALUE);
    		costs.put(new Point(1,4), Integer.MAX_VALUE);
    		costs.put(new Point(4,4), Integer.MAX_VALUE);
    		costs.put(new Point(2,5), Integer.MAX_VALUE);
    		costs.put(new Point(5,5), Integer.MAX_VALUE);
    		costs.put(new Point(3,6), Integer.MAX_VALUE);
    		costs.put(new Point(4,7), Integer.MAX_VALUE);

    	}
    	
    	// Wierdly placed points that are not locked states:
    	Point[] weirdPoints = new Point[] {
			new Point(2,4),
			new Point(3,4),
			new Point(3,5),
			new Point(4,5),
			new Point(4,6),
			new Point(5,6),
			new Point(5,7)	
    	};
    	// Costs
    	for(Point goal : expectedResult.keySet()) {
    		Map<Point, Integer> costs = expectedResult.get(goal);
    		// for the main room
    		for (int x = 6; x <= 9; ++x) {
    			for (int y = 7; y <= 9; ++y) {
    				costs.put(new Point(x,y), Math.abs(x-goal.x) + Math.abs(y-goal.y));
    			}
    		}
    		// for the weird points
    		for (Point p : weirdPoints) {
    			costs.put(p, Math.abs(p.x-goal.x) + Math.abs(p.y-goal.y));
    		}
    		
    	}
		
		
		assertEquals(expectedResult,StaticBoard.getInstance().costMap);
	}

}
