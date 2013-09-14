/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.util.ArrayList;

/**
 *
 * @author michal
 */
public interface Expandable<T, U> {
    public ArrayList<SearchNode<T,U>> expand();
}
