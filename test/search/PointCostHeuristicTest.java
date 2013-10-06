/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import board.Board;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.SokobanUtil;
import utilities.TestUtil;

/**
 *
 * @author michal
 */
public class PointCostHeuristicTest {
    
    public PointCostHeuristicTest() {
    }

    @Test
    public void testUtility() {
        PointCostHeuristic h = new PointCostHeuristic();
        Board b1 = TestUtil.initBoard("pointHeuristic1.map");
        Board goal = SokobanUtil.getSolvedBoard(b1);
        Board b2 = TestUtil.initBoard("pointHeuristic2.map");
        
        assertEquals(h.utility(b1, goal), 4 + 2 + 4 + 7, 0.001); // boxes not on goals.
        assertEquals(h.utility(b2, goal), 0, 0.001); // All boxes on goals, should be zero
        
    }
}