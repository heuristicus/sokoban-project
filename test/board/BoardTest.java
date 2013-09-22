/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public static final String testMapDir = "./maps/test/";
    
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
        Board tb = null;
        try {
            tb = SokobanUtil.readMap(testMapDir + "boardTestLocked.map");
        } catch (FileNotFoundException ex) {
            fail("Could not find test file boardTestLocked.map");
        }
        // True cases - the box should be considered blocked
        // top left corner - completely walled in
        assertTrue(tb.isBoxLockedAtPoint(new Point(1,1)));
        // bottom left corner - three walls
        assertTrue(tb.isBoxLockedAtPoint(new Point(1,5)));
        // bottom right corner - two walls
        assertTrue(tb.isBoxLockedAtPoint(new Point(6,5)));
        
        // False cases - the box should not be considered blocked
        // single wall left
        assertFalse(tb.isBoxLockedAtPoint(new Point(1,4)));
        // single wall right
        assertFalse(tb.isBoxLockedAtPoint(new Point(6,4)));
        // single wall down
        assertFalse(tb.isBoxLockedAtPoint(new Point(5,5)));
        // single wall up
        assertFalse(tb.isBoxLockedAtPoint(new Point(4,1)));
        // up-down corridor
        assertFalse(tb.isBoxLockedAtPoint(new Point(8,3)));
        // left-right corridor
        assertFalse(tb.isBoxLockedAtPoint(new Point(15,3)));
        
    }

    /**
     * Test of expand method, of class Board.
     */
    @Test
    public void testExpand() {
        Board blocked = null, surrounded = null;
        // List of files containing expected expansion of blocked state
        String fileListBlocked[] = {"bs1.map", "bs2.map", "bs3.map"};
        // List of files containig expansion of surrounded state
        String fileListSurrounded[] = {"ss1.map", "ss2.map", "ss3.map", "ss4.map"};
        // The boards that are expected to be received upon expansion of the two states
        ArrayList<Board> blockedSol = new ArrayList<>();
        ArrayList<Board> surroundedSol = new ArrayList<>();
        try {
            blocked = SokobanUtil.readMap(testMapDir + "boardTestExpandBlocked.map");
            surrounded = SokobanUtil.readMap(testMapDir + "boardTestExpandSurrounded.map");
            for (String fname : fileListBlocked) {
                blockedSol.add(SokobanUtil.readMap(testMapDir + fname));
            }
            for (String fname : fileListSurrounded) {
                surroundedSol.add(SokobanUtil.readMap(testMapDir + fname));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Exception in testExpand: " + ex.getMessage());
            fail("Could not find map file boardTestExpandBlocked.map or boardTestExpandSurrounded.map, or solution maps");
        }
        
        ArrayList<SearchNode<Board, SokobanUtil.Action>> bexp = blocked.expand(null);
        ArrayList<SearchNode<Board, SokobanUtil.Action>> sexp = surrounded.expand(null);
        
        assertEquals(blockedSol.size(), bexp.size());
        assertEquals(surroundedSol.size(), sexp.size());
                
        System.out.println("Surrounded expansions :");
        ArrayList<Board> bsol = new ArrayList<>();
        ArrayList<Board> ssol = new ArrayList<>();
        for (SearchNode<Board, SokobanUtil.Action> searchNode : sexp) {
            ssol.add(searchNode.getNodeState());
        }
        System.out.println("Blocked expansions: ");
        for (SearchNode<Board, SokobanUtil.Action> searchNode : bexp) {
            bsol.add(searchNode.getNodeState());
        }
        
        // The two lists are the same size - make sure they contain the same 
            assertTrue(surroundedSol.containsAll(ssol));
            assertTrue(blockedSol.containsAll(bsol));
    }
    
    @Test
    public void testEquals(){
        Board b1 = null, b2 = null;
        try {
            b1 = SokobanUtil.readMap(testMapDir + "boardTestEq1.map");
            b2 = SokobanUtil.readMap(testMapDir + "boardTestEq2.map");
        } catch (FileNotFoundException ex) {
            fail("Could not find maps boardTestEq[12].map");
        }
        
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1));
        Board b3 = new Board(b1);
        b3.moveElement(new Point(2,3), new Point(2,4));
        System.out.println(b1);
        System.out.println(b3);
        assertFalse(b1.equals(b3));
        assertFalse(b2.equals(b3));

        
    }
    
}