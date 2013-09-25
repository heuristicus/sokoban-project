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

/**
 *
 * @author michal
 */
public class SearchAlgorithmTest {
    
    SearchMethod aStar;
    SearchMethod noDupBFS;
    SearchMethod BFS;
    Board testMapStart;
    Board testMapGoal;
    Board testMapIntermediate1;
        
    public SearchAlgorithmTest() {
        aStar = new AStar(new DiagonalDistanceHeuristic());
        noDupBFS = new BreadthFirstSearchNoDuplication();
        BFS = new BreadthFirstSearch();
        testMapStart = SokobanUtil.readMap("./maps/test/searchTestStart.map");
        testMapGoal = SokobanUtil.readMap("./maps/test/searchTestGoal.map");
        testMapIntermediate1 = SokobanUtil.readMap("./maps/test/searchTestIntermediate1.map");
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
        ArrayList<Action> foundPath = aStar.findPath(testMapStart, testMapIntermediate1);
        assertEquals(Arrays.asList(Action.UP, Action.LEFT, Action.UP), foundPath);
        
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
