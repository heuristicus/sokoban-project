package search;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import utilities.SokobanUtil;
import utilities.TestUtil;
import board.Board;

public class MinMatchingHeuristicTest {
	
	@Test 
	public void testSolved() {
		// Testing against a solved board
		Heuristic.MinMatching2Heuristic h = new Heuristic.MinMatching2Heuristic(true);
        Board solvedBoard = SokobanUtil.getSolvedBoard(TestUtil.initBoard("MinMatchingTest1.map"));
        assertEquals(h.utility(solvedBoard, null), 0, 0.001); 
	}
	
	@Test
	public void test1() {
		// Simple test
		Heuristic.MinMatching2Heuristic h = new Heuristic.MinMatching2Heuristic(true);
        Board b = TestUtil.initBoard("MinMatchingTest1.map");        
        assertEquals(2 + 2, h.utility(b, null), 0.001);
	}
	
	@Test
	public void test2() {
		// Testing against a board in a locked state
		Heuristic.MinMatching2Heuristic h = new Heuristic.MinMatching2Heuristic(true);
        Board b = TestUtil.initBoard("MinMatchingTest2.map");        
        assertEquals(Float.POSITIVE_INFINITY, h.utility(b, null), 0.001);
	}
	
	@Test
	public void testSimpleSmall() {
        Board b = TestUtil.initBoard("simpleSmall.map");
        Board solution = SokobanUtil.getSolvedBoard(b);
        Heuristic.MinMatching2Heuristic h1 = new Heuristic.MinMatching2Heuristic(true);
        Heuristic.MinMatching2Heuristic h2 = new Heuristic.MinMatching2Heuristic(false);
        assertEquals(2 + 2 + 1 + 0, h1.utility(b, null), 10e-5);
        assertEquals(0, h1.utility(solution, null), 10e-5);
        assertEquals(2 + 2 + 1 + 0, h2.utility(solution, null), 10e-5);
        assertEquals(0, h2.utility(b, null), 10e-5);
	}
	
	@Test
	public void test3() {
		// Also: a box is on the way
        Board b = TestUtil.initBoard("MinMatchingTest3.map");
        Board solution = SokobanUtil.getSolvedBoard(b);
        Heuristic.MinMatching2Heuristic h1 = new Heuristic.MinMatching2Heuristic(true);
        Heuristic.MinMatching2Heuristic h2 = new Heuristic.MinMatching2Heuristic(false);
        assertEquals(3 + 1, h1.utility(b, null), 10e-5);
        assertEquals(3 + 1, h2.utility(solution, null), 10e-5);
	}
	
	@Test
	public void test4() {
		// Testing if the order of the boxes/goals has an effect
		Heuristic.MinMatching2Heuristic h = new Heuristic.MinMatching2Heuristic(true);
        Board b1 = TestUtil.initBoard("MinMatchingTest4.map");
        Board b2 = TestUtil.initBoard("MinMatchingTest5.map");        
        float b1u = h.utility(b1, null);
        float b2u = h.utility(b2, null);
        assertEquals(b1u, b2u, 0.001);
        assertEquals(3 + 4, b1u, 0.001);
	}
	
}
