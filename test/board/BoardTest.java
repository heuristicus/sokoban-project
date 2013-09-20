/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

import java.awt.Point;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import search.SearchNode;
import utilities.SokobanUtil;

/**
 *
 * @author michal
 */
public class BoardTest {
    
    public BoardTest() {
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
     * Test of getDynamicObjects method, of class Board.
     */
    @Test
    public void testGetDynamicObjects() {
        System.out.println("getDynamicObjects");
        Board instance = null;
        Map expResult = null;
        Map result = instance.getDynamicObjects();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPlayerPosition method, of class Board.
     */
    @Test
    public void testGetPlayerPosition() {
        System.out.println("getPlayerPosition");
        Board instance = null;
        Point expResult = null;
        Point result = instance.getPlayerPosition();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isInvalid method, of class Board.
     */
    @Test
    public void testIsInvalid() {
        System.out.println("isInvalid");
        Board instance = null;
        boolean expResult = false;
        boolean result = instance.isInvalid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Board.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Board instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of read method, of class Board.
     */
    @Test
    public void testRead() {
        System.out.println("read");
        InputStreamReader inputStreamReader = null;
        Board expResult = null;
        Board result = Board.read(inputStreamReader);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class Board.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        Point p = null;
        Board instance = null;
        Symbol expResult = null;
        Symbol result = instance.get(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of moveElement method, of class Board.
     */
    @Test
    public void testMoveElement() {
        System.out.println("moveElement");
        Point from = null;
        Point to = null;
        Board instance = null;
        boolean expResult = false;
        boolean result = instance.moveElement(from, to);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyAction method, of class Board.
     */
    @Test
    public void testApplyAction() throws Exception {
        System.out.println("applyAction");
        SokobanUtil.Action a = null;
        boolean destructive = false;
        Board instance = null;
        Board expResult = null;
        Board result = instance.applyAction(a, destructive);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of applyActionChained method, of class Board.
     */
    @Test
    public void testApplyActionChained() throws Exception {
        System.out.println("applyActionChained");
        ArrayList<SokobanUtil.Action> actionList = null;
        boolean destructive = false;
        Board instance = null;
        Board expResult = null;
        Board result = instance.applyActionChained(actionList, destructive);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFreeNeighbours method, of class Board.
     */
    @Test
    public void testGetFreeNeighbours() {
        System.out.println("getFreeNeighbours");
        Point p = null;
        Board instance = null;
        List expResult = null;
        List result = instance.getFreeNeighbours(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMapBoxAccessPoints method, of class Board.
     */
    @Test
    public void testGetMapBoxAccessPoints() {
        System.out.println("getMapBoxAccessPoints");
        Board instance = null;
        Map expResult = null;
        Map result = instance.getMapBoxAccessPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoxAccessPoints method, of class Board.
     */
    @Test
    public void testGetBoxAccessPoints() {
        System.out.println("getBoxAccessPoints");
        Board instance = null;
        Set expResult = null;
        Set result = instance.getBoxAccessPoints();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isBoxLockedAtPoint method, of class Board.
     */
    @Test
    public void testIsBoxLockedAtPoint() {
        System.out.println("isBoxLockedAtPoint");
        Point p = null;
        Board instance = null;
        boolean expResult = false;
        boolean result = instance.isBoxLockedAtPoint(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of expand method, of class Board.
     */
    @Test
    public void testExpand() {
        System.out.println("expand");
        SearchNode<Board, SokobanUtil.Action> parent = null;
        Board instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.expand(parent);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}