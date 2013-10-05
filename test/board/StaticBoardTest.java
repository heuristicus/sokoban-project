/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.util.ArrayList;
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

	@Test
	public void testComputeCosts() {
		final String TEST_FILE = "searchTestStart.map";
        TestUtil.initBoard(TEST_FILE);
        Map<Point, Map<Point, Integer>> expectedResult = new HashMap<>();
        Point goal1 = new Point(6,1);
        Point goal2 = new Point(6,2);

        // MAX_VALUE everywhere: locked points
        // The walkable area is a rectangle
        for (int x = 1; x <= 6; ++x) {
        	for (int y = 1; y <= 4; ++y) {
        		Map<Point, Integer> costs = new HashMap<>();
        		costs.put(goal1, Integer.MAX_VALUE);
        		costs.put(goal2, Integer.MAX_VALUE);
        		expectedResult.put(new Point(x,y), costs);
        	}
		}
        
        // Costs for reachable points
        for (int x = 2; x <= 6; ++x) {
        	for (int y = 1; y <= 3; ++y) {
        		expectedResult.get(new Point(x,y)).put(goal1, Math.abs(x-goal1.x) + Math.abs(y-goal1.y));
        		if (y >= 2) expectedResult.get(new Point(x,y)).put(goal2, Math.abs(x-goal2.x) + Math.abs(y-goal2.y));
        	}
        }
        
        System.out.println(expectedResult);
        
        assertEquals(expectedResult,StaticBoard.getInstance().goalDistanceCost);
	}
	
	@Test
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
	public void testComputeCosts3() {
		final String TEST_FILE = "../test100/test000.in";
		TestUtil.initBoard(TEST_FILE);
		
		Map<Point, Map<Point, Integer>> expectedResult = new HashMap<>();

		// Register the goals
		List<Point> goals = new ArrayList<Point>();
		for (int x = 7; x <= 9; ++x) {
        	for (int y = 7; y <= 9; ++y) {
        		goals.add(new Point(x,y));
        	}
		}
    	goals.add(new Point(6,8));//, new HashMap<Point, Integer>());
    	
    	// Fill with max value
		// Main room
		for (int x = 5; x <= 10; ++x) {
        	for (int y = 6; y <= 10; ++y) {
        		Map<Point, Integer> costs = new HashMap<>();
        		for(Point goal : goals) {
        			costs.put(goal,  Integer.MAX_VALUE);
        		}
        		expectedResult.put(new Point(x,y), costs);
        		
        	}
		}
		
		for (Point p : Arrays.asList(new Point(1,1), new Point(1,2), new Point(1,3), 
				new Point(2,3), new Point(3,3), new Point(1,4), new Point(4,4), 
				new Point(2,5), new Point(5,5), new Point(3,6), new Point(4,7))) {
    		// rest of the points
			Map<Point, Integer> costs = new HashMap<>();
    		for(Point goal : goals) {
    			costs.put(goal,  Integer.MAX_VALUE);
    		}
    		expectedResult.put(p, costs);

    	}
    	
    	// Costs
		// for the main room
		for (int x = 6; x <= 9; ++x) {
			for (int y = 7; y <= 9; ++y) {
				for(Point goal: goals) {
					expectedResult.get(new Point(x,y)).put(goal, Math.abs(x-goal.x) + Math.abs(y-goal.y));
				}
			}
		}
		// for the weird points
		for (Point p : Arrays.asList(new Point(2,4), new Point(3,4), new Point(3,5),
				new Point(4,5), new Point(4,6), new Point(5,6), new Point(5,7))) {
			for(Point goal: goals) {
				expectedResult.get(p).put(goal, Math.abs(p.x-goal.x) + Math.abs(p.y-goal.y));
			}
		}		
		
		assertEquals(expectedResult,StaticBoard.getInstance().goalDistanceCost);
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
