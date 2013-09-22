/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    Board testMapStart;
    Board testMapGoal;
        
    public SearchAlgorithmTest() {
        aStar = new AStar<>(new ManhattanHeuristic());
        noDupBFS = new BreadthFirstSearchNoDuplication<>();
        BFS = new BreadthFirstSearch<>();
        InputStreamReader mapStream;
        try {
            mapStream = new InputStreamReader(new FileInputStream("./maps/test/searchTestStart.map"));
            testMapStart = Board.read(mapStream);
            mapStream.close();
            mapStream = new InputStreamReader(new FileInputStream("./maps/test/searchTestGoal.map"));
            testMapGoal = Board.read(mapStream);
        } catch (FileNotFoundException ex) {
            System.out.println("Exception reading test map:" + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Exception reading test map:" + ex.getMessage());
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
