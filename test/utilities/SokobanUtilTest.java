/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import board.Board;
import board.Symbol;
import java.awt.Point;
import java.nio.file.Path;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michal
 */
public class SokobanUtilTest {
    
    public SokobanUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of pointMin method, of class SokobanUtil.
     */
    @Test
    public void testPointMin() {
        // Point with the lowest distance to 0,0 is the smallest. Distance should be calculated
        // by adding x and y coordinates for both points, since it is a discrete space.
        // 0,0 should be the minimum point in any case (assuming non-negative map coordinates)
        assertEquals(new Point(0,0), SokobanUtil.pointMin(new Point(0, 0), new Point(0,1)));
        assertEquals(new Point(20,1), SokobanUtil.pointMin(new Point(500, 0), new Point(20, 1)));
        // Points with the lowest y value should be returned if the distances are the same.
        assertEquals(new Point(10, 0), SokobanUtil.pointMin(new Point(10, 0), new Point(9, 1)));
        assertEquals(new Point(8, 1), SokobanUtil.pointMin(new Point(10, 0), new Point(8, 1)));
    }

    /**
     * Test of getSolvedBoard method, of class SokobanUtil.
     */
    @Test
    public void testGetSolvedBoard() {
        Board start = TestUtil.initBoard("boardTestSolveInit.map");
        Board expected = TestUtil.initBoard("boardTestSolved.map");
        
        Board result = SokobanUtil.getSolvedBoard(start);
        System.out.println(result);
        assertEquals(expected, result);
    }
}