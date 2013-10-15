/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author michal
 */
public abstract class MemoSearchMethod extends SearchMethod {
    
    Queue<SearchNode> open;

    public MemoSearchMethod() {
        open = new PriorityQueue<>();
    }
    
    public Queue<SearchNode> getOpenList(){
        return open;
    }
    
    
}
