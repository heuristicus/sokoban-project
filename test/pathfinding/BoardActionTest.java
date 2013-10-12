/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfinding;

import utilities.BoardAction;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.SokobanUtil;
import utilities.SokobanUtil.Action;

/**
 *
 * @author michal
 */
public class BoardActionTest {
    
    public BoardActionTest() {
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

    /**
     * Test of convertToActionList method, of class BoardAction.
     */
    @Test
    public void testConvertToActionList() {
        ArrayList<Action> expected = new ArrayList<>(Arrays.asList(Action.UP, Action.UP, Action.RIGHT, Action.DOWN, Action.LEFT, Action.LEFT));
        ArrayList<BoardAction> test = new ArrayList<>(
                Arrays.asList(
                new BoardAction(Action.UP, new Point(0,0)), 
                new BoardAction(Action.UP, new Point(0,1)), 
                new BoardAction(Action.RIGHT, new Point(0,2)), 
                new BoardAction(Action.DOWN, new Point(0,3)), 
                new BoardAction(Action.LEFT, new Point(0,4)), 
                new BoardAction(Action.LEFT, new Point(0,5))
                )
                );
        assertEquals(expected, BoardAction.convertToActionList(test));
    }
}