/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.SokobanUtil.Action;

/**
 *
 * @author michal
 */
public class SearchAlgorithmTest {
    
    SearchMethod<Board, Action> aStar;
    SearchMethod<Board, Action> noDupBFS;
    SearchMethod<Board, Action> BFS;
    
    public SearchAlgorithmTest() {
        aStar = new AStar<>(new ManhattanHeuristic());
        noDupBFS = new BreadthFirstSearchNoDuplication<>();
        BFS = new BreadthFirstSearch<>();
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
        
    }
    
    @Test
    public void testMultiGoalBreadthFirstSearch(){
        
    }
    
    @Test
    public void testBreadthFirstSearch(){
    }
    
    @Test
    public void testBreadthFirstSearchNoDuplication(){
        
    }
    
}
