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

import pathfinding.BoxMovement;
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
        Board tb = initBoard("boardTestLocked.map");
        
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
        Board blocked = initBoard("boardTestExpandBlocked.map");
        Board surrounded = initBoard("boardTestExpandSurrounded.map");
        // List of files containing expected expansion of blocked state
        String fileListBlocked[] = {"bs1.map", "bs2.map", "bs3.map"};
        // List of files containig expansion of surrounded state
        String fileListSurrounded[] = {"ss1.map", "ss2.map", "ss3.map", "ss4.map"};
        // The boards that are expected to be received upon expansion of the two states
        ArrayList<Board> blockedExpected = new ArrayList<>();
        ArrayList<Board> surroundedExpected = new ArrayList<>();
        	
        // Initialise the expected boards from file
        for (String fname : fileListBlocked) {
            blockedExpected.add(initBoard(fname));
        }
        for (String fname : fileListSurrounded) {
            surroundedExpected.add(initBoard(fname));
        }
        
        // Get the results of the expansion of each board
        ArrayList<SearchNode<Board, SokobanUtil.Action>> blockedResult = blocked.expand(null);
        ArrayList<SearchNode<Board, SokobanUtil.Action>> surroundedResult = surrounded.expand(null);
        
        // The two resulting lists should be the same size as the expected lists
        assertEquals(blockedExpected.size(), blockedResult.size());
        assertEquals(surroundedExpected.size(), surroundedResult.size());
                
        // Create arraylists for comparison convenience by extracting the state
        // from the returned nodes.
        ArrayList<Board> blockedResultArray = new ArrayList<>();
        ArrayList<Board> surroundedResultArray = new ArrayList<>();
        for (SearchNode<Board, SokobanUtil.Action> searchNode : surroundedResult) {
            surroundedResultArray.add(searchNode.getNodeState());
        }

        for (SearchNode<Board, SokobanUtil.Action> searchNode : blockedResult) {
            blockedResultArray.add(searchNode.getNodeState());
        }
        
        // The two lists are the same size - make sure they contain the same 
        assertTrue(surroundedExpected.containsAll(surroundedResultArray));
        assertTrue(blockedExpected.containsAll(blockedResultArray));
    }
    
    @Test
    public void testEquals(){
        Board b1 = initBoard("boardTestEq1.map");
        Board b2 = initBoard("boardTestEq2.map");
        
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
    public void testPrepareNextBoxMove() throws IllegalMoveException {
    	final String INPUT_TEST_FILE = "searchTestStart.map";
    	final String OUTPUT_TEST_FILE = "searchTestIntermediate1.map";
        Board start = initBoard(INPUT_TEST_FILE);
        start.prepareNextBoxMove(Action.RIGHT, new Point(4,2), true);
        assertEquals(start, initBoard(OUTPUT_TEST_FILE));
        
    }
    
    @Test
    public void testBuildFullPath() throws IllegalMoveException {
    	final String INPUT_TEST_FILE = "searchTestStart.map";
    	final String OUTPUT_TEST_FILE = "searchTestGoal.map";
        Board board = initBoard(INPUT_TEST_FILE);
        Board expectedResult = initBoard(OUTPUT_TEST_FILE);
        List<BoxMovement> boxActions = Arrays.asList(
        		new BoxMovement(Action.RIGHT, new Point(4,2)),
        		new BoxMovement(Action.RIGHT, new Point(5,2)),
        		new BoxMovement(Action.RIGHT, new Point(2,1)),
        		new BoxMovement(Action.RIGHT, new Point(3,1)),
        		new BoxMovement(Action.RIGHT, new Point(4,1)),
        		new BoxMovement(Action.RIGHT, new Point(5,1))
        		);
        
        List<Action> actions = board.generateFullActionList(boxActions);
        board.applyActionChained(actions, true);
        
        assertEquals(Arrays.asList(Action.UP, Action.LEFT, Action.UP, 
        		Action.RIGHT, Action.RIGHT, Action.LEFT, Action.LEFT, 
        		Action.LEFT, Action.LEFT, Action.UP, Action.RIGHT, 
        		Action.RIGHT, Action.RIGHT, Action.RIGHT), actions);
        
        // TODO: equals still doesn't properly ignore the player's position
        assertEquals(expectedResult, board);
    }
    
    @Test
    public void testGetAccessiblePoints(){
        Board accTest = initBoard("boardTestAccessible.map");
        String pl = null, tr = null, bl = null, tl = null;
        try {
            pl = SokobanUtil.readMapAsString(testMapDir + "boardTestAccessiblePL.map");
            tr = SokobanUtil.readMapAsString(testMapDir + "boardTestAccessibleTR.map");
            bl = SokobanUtil.readMapAsString(testMapDir + "boardTestAccessibleBL.map");
            tl = SokobanUtil.readMapAsString(testMapDir + "boardTestAccessibleTL.map");
        } catch (IOException ex) {
            fail("Could not find boardTestAccessible test maps.");
        } catch (RuntimeException ex){ // the player isn't on one of the boards, but we don't care
            
        }
        
        List<Point> playerAccess = accTest.getAccessiblePoints(accTest.getPlayerPosition());
        System.out.println(playerAccess.get(0));
        assertEquals("Expected top left position did not match received", new Point(4,1), playerAccess.get(0));
        assertTrue(pl.equals(accTest.toStringMarked(playerAccess)));
        
//        System.out.println("Player accessible points");
//        System.out.println(accTest.toStringMarked(playerAccess));
        List<Point> topRight = accTest.getAccessiblePoints(new Point(10,3));
        System.out.println(topRight.get(0));
        assertEquals("Expected top left position did not match received", new Point(7,1), topRight.get(0));
        assertTrue(tr.equals(accTest.toStringMarked(topRight)));
//        System.out.println("Top right block accessible");
//        System.out.println(accTest.toStringMarked(topRight));
        List<Point> bottomLeft = accTest.getAccessiblePoints(new Point(1,5));
        System.out.println(bottomLeft.get(0));
        assertEquals("Expected top left position did not match received", new Point(1,4), bottomLeft.get(0));
        assertTrue(bl.equals(accTest.toStringMarked(bottomLeft)));
//        System.out.println("Bottom left accessible");
//        System.out.println(accTest.toStringMarked(bottomLeft));
        List<Point> topLeft = accTest.getAccessiblePoints(new Point(1,1));
        System.out.println(topLeft.get(0));
        assertEquals("Expected top left position did not match received", new Point(1,1), topLeft.get(0));
        assertTrue(tl.equals(accTest.toStringMarked(topLeft)));
//        System.out.println("Top left accessible");
//        System.out.println(accTest.toStringMarked(topLeft));
    }
    
    @Test
    public void testGetBoxPushableDirections(){
        Board b = initBoard("boardTestPushable.map");
        HashMap<Point, List<Action>> expected = new HashMap<>();
        // point 1,1 in corner - not pushable
//        expected.put(new Point(1,1), new ArrayList<Action>());
        // point 9,1 in horizontal corridor - pushable left or right
        expected.put(new Point(10,1), Arrays.asList(Action.LEFT, Action.RIGHT));
        // point 7,3 in open space - pushable in all directions
        expected.put(new Point(7,3), Arrays.asList(Action.LEFT, Action.RIGHT, Action.UP, Action.DOWN));
        // Point 4,4 in vertical corridor - pushable up or down
        expected.put(new Point(4,4), Arrays.asList(Action.UP, Action.DOWN));
        // Point 11, 5 surrounded by 3 walls - not pushable
//        expected.put(new Point(11,5), new ArrayList<Action>());
        
        Map<Point, List<Action>> result = b.getBoxPushableDirections();
        
        for (Point p : expected.keySet()) {
            assertTrue("Result did not contain the expected box location " + p, result.containsKey(p));
            List<Action> expectedList = expected.get(p);
            List<Action> resultList = result.get(p);
            assertEquals("Action lists for " + p + "were not the same size.", expectedList.size(), resultList.size());
            assertTrue("The contents of the result action list did not match the expected list.", result.get(p).containsAll(expected.get(p)));
        }
        
    }
    
}