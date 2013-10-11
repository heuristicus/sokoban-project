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
		Heuristic.MinMatching2Heuristic h = new Heuristic.MinMatching2Heuristic();
        Board solvedBoard = SokobanUtil.getSolvedBoard(TestUtil.initBoard("MinMatchingTest1.map"));
        assertEquals(h.utility(solvedBoard, null), 0, 0.001); 
	}
	
	@Test
	public void test1() {
		// Simple test
		Heuristic.MinMatching2Heuristic h = new Heuristic.MinMatching2Heuristic();
        Board b = TestUtil.initBoard("MinMatchingTest1.map");        
        assertEquals(2 + 2, h.utility(b, null), 0.001);
	}
	
	@Test
	public void test2() {
		// Testing against a board in a locked state
		Heuristic.MinMatching2Heuristic h = new Heuristic.MinMatching2Heuristic();
        Board b = TestUtil.initBoard("MinMatchingTest2.map");        
        assertEquals(Float.POSITIVE_INFINITY, h.utility(b, null), 0.001);
	}
	
	@Test
	public void testSimpleSmall() {
		Heuristic.MinMatching2Heuristic h = new Heuristic.MinMatching2Heuristic();
        Board b = TestUtil.initBoard("simpleSmall.map");        
        assertEquals(2 + 2 + 1 + 0, h.utility(b, null), 0.001);
	}
	
	@Test
	public void test3() {
		// Also: a box is on the way
		Heuristic.MinMatching2Heuristic h = new Heuristic.MinMatching2Heuristic();
        Board b = TestUtil.initBoard("MinMatchingTest3.map");        
        assertEquals(3 + 1, h.utility(b, null), 0.001);
	}
	
	@Test
	public void test4() {
		// Testing if the order of the boxes/goals has an effect
		Heuristic.MinMatching2Heuristic h = new Heuristic.MinMatching2Heuristic();
        Board b1 = TestUtil.initBoard("MinMatchingTest4.map");
        Board b2 = TestUtil.initBoard("MinMatchingTest5.map");        
        float b1u = h.utility(b1, null);
        float b2u = h.utility(b2, null);
        assertEquals(b1u, b2u, 0.001);
        assertEquals(3 + 4, b1u, 0.001);
	}
}
