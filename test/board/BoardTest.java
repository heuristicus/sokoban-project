/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Point;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
    
    public Board initBoard(String mapName) {
    	Path path = Paths.get(testMapDir, mapName);
    	try {
			return Board.read( Files.newBufferedReader(path, Charset.defaultCharset()));
		} catch (IOException e) {
			e.printStackTrace();
			fail("File not found: " + path.toAbsolutePath());
			return null;
		}
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
     * Test of read method, compare the result using the toString method
     */
    @Test
    public void testRead() throws IOException {
    	final String TEST_FILE = "readTest1.map";
        Board board = initBoard(TEST_FILE);
        String referenceMap = new String(Files.readAllBytes(Paths.get(testMapDir, TEST_FILE)));
        assertEquals(referenceMap, board.toString());
    }
    
    /**
     * Test of read method, test the result using the getters
     */
    @Test
    public void testRead2() throws IOException {
    	final String TEST_FILE = "readTest2.map";
        Board board = initBoard(TEST_FILE);
        assertEquals("Wrong player position", new Point(1,2), board.getPlayerPosition());
        Map<Point, Symbol> dynMap = new HashMap<>();
        dynMap.put(new Point(1,2), Symbol.PlayerOnGoal);
        dynMap.put(new Point(1,1), Symbol.Box);
        dynMap.put(new Point(3,1), Symbol.BoxOnGoal);
        assertEquals("Wrong dynamic objects", dynMap, board.getDynamicObjects());
        assertEquals("Wrong get(0,0)", Symbol.Wall, board.get(new Point(0,0)));
        assertEquals("Wrong get(3,2)", Symbol.Empty, board.get(new Point(3,2)));
        assertEquals("Wrong get(3,1)", Symbol.BoxOnGoal, board.get(new Point(3,1)));
    }
    
    /** 
     * Test the static map generated while reading the board. The output is tested using
     * StaticMap#toString() 
     */
    @Test
    public void testReadStatic() throws IOException {
    	final String INPUT_TEST_FILE = "readTest1.map";
    	final String OUTPUT_TEST_FILE = "readTest1Static.map";
        initBoard(INPUT_TEST_FILE);
        String referenceMap = new String(Files.readAllBytes(Paths.get(testMapDir, OUTPUT_TEST_FILE)));
        assertEquals(referenceMap, StaticBoard.getInstance().toString());
    }

    /**
     * Test of moveElement method, of class Board.
     */
    @Test
    public void testMoveElement() {
        Board board = initBoard("readTest1.map");
        boolean result;
        Point from, to;
        
        // Moving a box to an empty tile
        from = new Point(1,1);
        to = new Point(4,1);
        result = board.moveElement(from, to);
        assertEquals(true, result);
        assertEquals(Symbol.Empty, board.get(from));
        assertEquals(Symbol.Box, board.get(to));
        
        // Moving the box to a goal
        from = to;
        to = new Point(2,1);
        result = board.moveElement(from, to);
        assertEquals(true, result);
        assertEquals(Symbol.Empty, board.get(from));
        assertEquals(Symbol.BoxOnGoal, board.get(to));
        
        // Moving the box on top of the player (should fail)
        from = to;
        to = new Point(1,2);
        result = board.moveElement(from, to);
        assertEquals(false, result);
        assertEquals(Symbol.BoxOnGoal, board.get(from));
        assertEquals(Symbol.Player, board.get(to));
        
        // Moving the box on top of a non walkable tile (wall) (should fail)
        to = new Point(0,0);
        result = board.moveElement(from, to);
        assertEquals(false, result);
        assertEquals(Symbol.BoxOnGoal, board.get(from));
        assertEquals(Symbol.Wall, board.get(to));
        
        // Moving the box back to its initial position
        result = board.moveElement(from, new Point(1,1));
        
        // Moving the player to an empty tile
        from = new Point(1,2);
        to = new Point(4,1);
        result = board.moveElement(from, to);
        assertEquals(true, result);
        assertEquals(Symbol.Empty, board.get(from));
        assertEquals(Symbol.Player, board.get(to));
        
        // Moving the player to a goal
        from = to;
        to = new Point(2,1);
        result = board.moveElement(from, to);
        assertEquals(true, result);
        assertEquals(Symbol.Empty, board.get(from));
        assertEquals(Symbol.PlayerOnGoal, board.get(to));
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
        Board board = initBoard("freeNeighboursTest.map");
        // Box in top left corner
        assertEquals(Arrays.asList(new Point(2,1), new Point(1,2)), 
        		board.getFreeNeighbours(new Point(1,1)));
        
        // Called on an empty tile
        assertEquals(Arrays.asList(new Point(3,2), new Point(2,3), new Point(4,3), new Point(3,4)),
        		board.getFreeNeighbours(new Point(3,3)));
                
        // Next box to a player: not counted as an obstacle
        assertEquals(4, board.getFreeNeighbours(new Point(10,2)).size());
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
        Board tb = SokobanUtil.readMap(Paths.get(testMapDir,"boardTestLocked.map"));
        
        // True cases - the box should be considered blocked
        // top left corner - completely walled in
        assertTrue(tb.isBoxLockedAtPoint(new Point(1,1)));
        // bottom left corner - three walls
        assertTrue(tb.isBoxLockedAtPoint(new Point(5,1)));
        // bottom right corner - two walls
        assertTrue(tb.isBoxLockedAtPoint(new Point(5,6)));
        
        // False cases - the box should not be considered blocked
        // single wall left
        assertFalse(tb.isBoxLockedAtPoint(new Point(4,1)));
        // single wall right
        assertFalse(tb.isBoxLockedAtPoint(new Point(4,6)));
        // single wall down
        assertFalse(tb.isBoxLockedAtPoint(new Point(5,5)));
        // single wall up
        assertFalse(tb.isBoxLockedAtPoint(new Point(1,4)));
        // up-down corridor
        assertFalse(tb.isBoxLockedAtPoint(new Point(3,8)));
        // left-right corridor
        assertFalse(tb.isBoxLockedAtPoint(new Point(3,15)));
        
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
        	
        blocked = SokobanUtil.readMap(Paths.get(testMapDir,"boardTestExpandBlocked.map"));
        surrounded = SokobanUtil.readMap(Paths.get(testMapDir,"boardTestExpandSurrounded.map"));
        for (String fname : fileListBlocked) {
            blockedSol.add(SokobanUtil.readMap(testMapDir + fname));
        }
        for (String fname : fileListSurrounded) {
            surroundedSol.add(SokobanUtil.readMap(testMapDir + fname));
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
        
        assertEquals(ssol, blockedSol);
        assertEquals(ssol, surroundedSol);
        
        
        fail("The test case is a prototype.");
    }
}