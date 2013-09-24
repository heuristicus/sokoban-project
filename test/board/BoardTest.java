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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import exceptions.IllegalMoveException;
import search.SearchNode;
import utilities.SokobanUtil;
import utilities.SokobanUtil.Action;

/**
 *
 * @author michal
 */
public class BoardTest {
    
    public static final String testMapDir = "./maps/test/";
    
    public static final Map<String, String> inlineMaps = new HashMap<>();
    
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
    
    public Board initBoardFromString(String map) {
    	return Board.read(new BufferedReader(new StringReader(map)));
    }
    
    @BeforeClass
    public static void setUpClass() {
    	inlineMaps.put("MAP_00", "" +
    			"########\n" +
    			"#   # .#\n" +
    			"#   $$.#\n" +
    			"####   #\n" +
    			"   #@ ##\n" +
    			"   ####");
    	inlineMaps.put("MAP_00_RESULT", "" +
    			"########\n" +
    			"#   # *#\n" +
    			"#    @*#\n" +
    			"####   #\n" +
    			"   #  ##\n" +
    			"   ####");
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
    	Board board = initBoardFromString(inlineMaps.get("MAP_00"));
        Board result = board.applyAction(Action.UP, false);
        assertFalse(board.equals(result));
        assertEquals(Symbol.Empty, board.get(new Point(4,3)));
        assertEquals(Symbol.Player, result.get(new Point(4,3)));
        
        result = board.applyAction(Action.UP, true);
        assertTrue(board.equals(result));
        assertEquals(board.get(new Point(4,3)), Symbol.Player);
    }

    /**
     * Test of applyActionChained method, of class Board.
     */
    @Test
    public void testApplyActionChained() throws Exception {
    	Board board = initBoardFromString(inlineMaps.get("MAP_00"));
        Board result = board.applyActionChained(Arrays.asList(Action.UP, Action.RIGHT, Action.RIGHT, Action.UP, Action.UP, Action.LEFT, Action.DOWN, Action.LEFT, Action.LEFT, Action.UP, Action.LEFT, Action.LEFT, Action.DOWN, Action.RIGHT, Action.RIGHT, Action.RIGHT, Action.RIGHT, Action.LEFT, Action.DOWN, Action.DOWN, Action.RIGHT, Action.UP, Action.RIGHT, Action.UP, Action.DOWN, Action.LEFT, Action.LEFT, Action.UP, Action.RIGHT), true);
        assertEquals(result.toString(), inlineMaps.get("MAP_00_RESULT"));
    }

    /**
     * Test of getFreeNeighbours method, of class Board.
     */
    @Test
    public void testGetFreeNeighbours() {
        Board board = initBoard("freeNeighboursTest.map");
        List<Point> expected, result;

        // Box in top left corner
        expected = Arrays.asList(new Point(2,1), new Point(1,2)); 
        result = board.getFreeNeighbours(new Point(1,1)); 
        assertEquals(expected.size(), result.size()); 
        assertTrue(expected.containsAll(result));
        
        // Called on an empty tile
        expected = Arrays.asList(new Point(3,2), new Point(2,3), new Point(4,3), new Point(3,4)); 
        result = board.getFreeNeighbours(new Point(3,3)); 
        assertEquals(expected.size(), result.size()); 
        assertTrue(expected.containsAll(result));
                
        // Next box to a player: not counted as an obstacle
        assertEquals(4, board.getFreeNeighbours(new Point(9,2)).size());
    }

    /**
     * Test of getMapBoxAccessPoints method, of class Board.
     */
    @Test
    public void testGetMapBoxAccessPoints() {
        Board board = initBoard("readTest1.map");
        Map<Point, List<Point>> expResult = new HashMap<>();
        expResult.put(new Point(1,1), Arrays.asList(new Point(2,1), new Point(1,2)));
        expResult.put(new Point(3,1), Arrays.asList(new Point(2,1), new Point(3,2), new Point(4,1)));
        Map<Point, List<Point>> result = board.getMapBoxAccessPoints();
        
        // Deep equals with unsignificative item order.
        assertEquals(expResult.keySet(), result.keySet());
        for (Point key : expResult.keySet()) {
        	assertTrue(result.get(key).size() == expResult.get(key).size());
        	assertTrue(expResult.get(key).containsAll(result.get(key)));
        }
    }

    /**
     * Test of getBoxAccessPoints method, of class Board.
     */
    @Test
    public void testGetBoxAccessPoints() {
    	Board board = initBoard("readTest1.map");
        Set<Point> expResult = new HashSet<>(Arrays.asList(
        		new Point(2,1), new Point(1,2), new Point(3,2), new Point(4,1)));
        Set<Point> result = board.getBoxAccessPoints();
        
        assertEquals(expResult, result);
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
        
        // The two lists are the same size - make sure they contain the same 
        assertTrue(surroundedSol.containsAll(ssol));
        assertTrue(blockedSol.containsAll(bsol));
    }
    
    @Test
    public void testEquals(){
        Board b1 = null, b2 = null;
        b1 = SokobanUtil.readMap(testMapDir + "boardTestEq1.map");
        b2 = SokobanUtil.readMap(testMapDir + "boardTestEq2.map");
        
        assertTrue(b1.equals(b2));
        assertTrue(b2.equals(b1));
        Board b3 = new Board(b1);
        b3.moveElement(new Point(2,3), new Point(2,4));
        System.out.println(b1);
        System.out.println(b3);
        assertFalse(b1.equals(b3));
        assertFalse(b2.equals(b3));
    }
    
    @Test
    public void testApplyBoxMove() throws IllegalMoveException {
    	final String INPUT_TEST_FILE = "searchTestStart.map";
    	final String OUTPUT_TEST_FILE = "searchTestIntermediate1.map";
        Board start = initBoard(INPUT_TEST_FILE);
        start.applyBoxMove(Action.RIGHT, new Point(4,2), true);
        assertEquals(start, initBoard(OUTPUT_TEST_FILE));
        
    }
    
    @Test
    public void testGetAccessiblePoints(){
        Board accTest = null;
        String pl = null, tr = null, bl = null, tl = null;
        try {
            accTest = SokobanUtil.readMap(testMapDir + "boardTestAccessible.map");
            pl = SokobanUtil.readMapAsString(testMapDir + "boardTestAccessiblePL.map");
            tr = SokobanUtil.readMapAsString(testMapDir + "boardTestAccessibleTR.map");
            bl = SokobanUtil.readMapAsString(testMapDir + "boardTestAccessibleBL.map");
            tl = SokobanUtil.readMapAsString(testMapDir + "boardTestAccessibleTL.map");
        } catch (IOException ex) {
            fail("Could not find boardTestAccessible test maps.");
        } catch (RuntimeException ex){ // the player isn't on one of the boards, but we don't care
            
        }

        
        List<Point> playerAccess = accTest.getAccessiblePoints(accTest.getPlayerPosition());

        assertTrue(pl.equals(accTest.toStringMarked(playerAccess)));
        
//        System.out.println("Player accessible points");
//        System.out.println(accTest.toStringMarked(playerAccess));
        List<Point> topRight = accTest.getAccessiblePoints(new Point(10,3));
        assertTrue(tr.equals(accTest.toStringMarked(topRight)));
//        System.out.println("Top right block accessible");
//        System.out.println(accTest.toStringMarked(topRight));
        List<Point> bottomLeft = accTest.getAccessiblePoints(new Point(1,5));
        assertTrue(bl.equals(accTest.toStringMarked(bottomLeft)));
//        System.out.println("Bottom left accessible");
//        System.out.println(accTest.toStringMarked(bottomLeft));
        List<Point> topLeft = accTest.getAccessiblePoints(new Point(1,1));
        assertTrue(tl.equals(accTest.toStringMarked(topLeft)));
//        System.out.println("Top left accessible");
//        System.out.println(accTest.toStringMarked(topLeft));
    }
    
}