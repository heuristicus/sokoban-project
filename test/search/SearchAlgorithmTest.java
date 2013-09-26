/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.SokobanUtil;
import utilities.SokobanUtil.Action;
import board.Board;
import board.BoardTest;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilities.TestUtil;
import static org.junit.Assert.fail;

/**
 *
 * @author michal
 */
public class SearchAlgorithmTest {
    
    SearchMethod aStarPlayerPath;
    SearchMethod aStarBoxPath;
    SearchMethod noDupBFS;
    SearchMethod BFS;
    Board testMapStart;
    Board testMapGoal;
    Board testMapIntermediate1;
    Board testMapBoardSpaceStart;
    Board testMapBoardSpaceGoal;
        
    public SearchAlgorithmTest() {
        aStarPlayerPath = new AStar(new DiagonalDistanceHeuristic());
        aStarBoxPath = new AStar(new ManhattanClosestHeuristic());
        noDupBFS = new BFSNoDuplication();
        BFS = new BreadthFirstSearch();
        testMapStart = TestUtil.initBoard("searchTestStart.map");
        testMapGoal = TestUtil.initBoard("searchTestGoal.map");
        testMapIntermediate1 = TestUtil.initBoard("searchTestIntermediate1.map");
        testMapBoardSpaceStart = TestUtil.initBoard("boardTestBoardSpaceStart.map");
        testMapBoardSpaceGoal = TestUtil.initBoard("boardTestBoardSpaceGoal.map");
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
    
    @Test
    public void testAStar() {
        ArrayList<Action> foundPath = aStarPlayerPath.findPath(testMapStart, testMapIntermediate1, false);
        assertEquals(Arrays.asList(Action.UP, Action.LEFT, Action.UP), foundPath);
        ArrayList<Action> path2 = aStarBoxPath.findPath(testMapBoardSpaceStart, testMapBoardSpaceGoal, true);
        assertEquals(Arrays.asList(Action.RIGHT, Action.RIGHT, Action.RIGHT), path2);
    }
    
    @Test
    public void testFullFunctionality(){
        Board start = null;
        try {
            start = Board.read(new BufferedReader(new InputStreamReader(new FileInputStream(BoardTest.testMapDir + "fullTest.map"))));
        } catch (FileNotFoundException ex) {
            fail("Could not find test map.");
        }
        System.out.println("start map");
        System.out.println(start);
        Board goal = SokobanUtil.getSolvedBoard(start);
        System.out.println("goal map");
        System.out.println(goal);
        
        SearchMethod as = new AStar(new ManhattanClosestHeuristic());
        SearchMethod bfs = new BFSNoDuplication();
//        ArrayList<Action> actions = as.findPath(start, goal, false);
//        ArrayList<Action> actions = bfs.findPath(start, goal, false);
//        SokobanUtil.actionListAsString(actions);
    }
    
    @Test
    public void testBreadthFirstSearch(){
//        ArrayList<Action> foundPath = BFS.findPath(testMapStart, testMapGoal, false);
//        System.out.println(SokobanUtil.actionListAsString(foundPath));
    }
    
    @Test
    public void testBreadthFirstSearchNoDuplication(){
//        Board start = TestUtil.initBoard("searchTestStart.map");
//        Board goal = TestUtil.initBoard("searchTestGoal.map");
        System.out.println(testMapStart);
        System.out.println(testMapGoal);
        ArrayList<Action> findPath = noDupBFS.findPath(testMapStart, testMapGoal, false);
        System.out.println(SokobanUtil.actionListAsString(findPath));
    }
    
}
