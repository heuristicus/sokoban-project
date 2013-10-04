/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

import java.awt.Point;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;
        
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
    public void testInit() {
        Board t = TestUtil.initBoard("testPathCost.map");
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
