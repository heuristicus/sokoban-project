/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michal
 */
public class SearchNodeTest {
    
    public SearchNodeTest() {
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
     * Test of nodeUnwind method, of class SearchNode.
     */
    @Test
    public void testNodeUnwind() {
        System.out.println("nodeUnwind");
        SearchNode instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.nodeUnwind();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actionUnwind method, of class SearchNode.
     */
    @Test
    public void testActionUnwind() {
        System.out.println("actionUnwind");
        SearchNode instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.actionUnwind();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of expand method, of class SearchNode.
     */
    @Test
    public void testExpand() {
        System.out.println("expand");
        SearchNode instance = null;
        ArrayList expResult = null;
        ArrayList result = instance.expand();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNodeState method, of class SearchNode.
     */
    @Test
    public void testGetNodeState() {
        System.out.println("getNodeState");
        SearchNode instance = null;
        Object expResult = null;
        Object result = instance.getNodeState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class SearchNode.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        SearchNode instance = null;
        boolean expResult = false;
        boolean result = instance.equals(null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class SearchNode.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        SearchNode instance = null;
        int expResult = 0;
        int result = instance.compareTo(null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class SearchNode.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SearchNode instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}