/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author michal
 */
public class SearchNodeTest {
    
    public SearchNodeTest() {
    }

    @Test
    public void testNodeUnwind() {
        fail();
    }

    @Test
    public void testActionUnwind() {
        fail();
    }

    @Test
    public void testExpand() {
        fail();
    }

    @Test
    public void testGetNodeState() {
        fail();
    }

    @Test
    public void testEquals() {
        fail();
    }

    @Test
    public void testHashCode() {
        fail();
    }

    @Test
    public void testCompareTo() {
        SearchNode a = new SearchNode(null, null, null, 0, 0, true);
        SearchNode a1 = new SearchNode(null, null, null, 0, 0, true);
        SearchNode b = new SearchNode(null, null, null, 0, 1, true);
        SearchNode c = new SearchNode(null, null, null, 0, 5, true);
        
        assertEquals(0, a.compareTo(a1));
        assertEquals(-1, a.compareTo(b));
        assertEquals(-1, a.compareTo(c));
        assertEquals(0, a1.compareTo(a));
        assertEquals(1, c.compareTo(a));
        assertEquals(1, b.compareTo(a));
        assertEquals(1, c.compareTo(b));
        assertEquals(-1, b.compareTo(c));
    }

    @Test
    public void testToString() {
        fail();
    }
}