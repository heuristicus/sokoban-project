/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

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
    
    SearchMethod<Board, Action> aStar;
    SearchMethod<Board, Action> noDupBFS;
    SearchMethod<Board, Action> BFS;
    Board testMapStart;
    Board testMapGoal;
        
    public SearchAlgorithmTest() {
        aStar = new AStar<>(new ManhattanHeuristic());
        noDupBFS = new BreadthFirstSearchNoDuplication<>();
        BFS = new BreadthFirstSearch<>();
        testMapStart = SokobanUtil.readMap("./maps/test/searchTestStart.map");
        testMapGoal = SokobanUtil.readMap("./maps/test/searchTestGoal.map");
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
    
    public void testAStar() {
//        ArrayList<Action> foundPath = aStar.findPath(testMapStart, testMapGoal);
        
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
