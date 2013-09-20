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
public interface Expandable<T extends Expandable<T,U>, U> {
    public ArrayList<SearchNode<T,U>> expand(SearchNode<T,U> parent);
}
