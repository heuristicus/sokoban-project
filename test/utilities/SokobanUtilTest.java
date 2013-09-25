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
        fail("The test case is a prototype.");
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