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
import utilities.TestUtil;

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
        noDupBFS = new BreadthFirstSearchNoDuplication();
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
    public void testMultiGoalBreadthFirstSearch(){
        
    }
    
    @Test
    public void testBreadthFirstSearch(){
//        ArrayList<Action> foundPath = BFS.findPath(testMapStart, testMapGoal);
    }
    
    @Test
    public void testBreadthFirstSearchNoDuplication(){
//        ArrayList<Action> findPath = noDupBFS.findPath(testMapStart, testMapGoal);
    }
    
}
