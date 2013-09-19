/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

/**
 * An interface for heuristics which estimate the cost to get from a start point
 * to a goal point.
 * @param <T> The type on which to perform the computation of the cost estimate
 * @author michal
 */
interface Heuristic<T> {
    float utility(T start, T goal);
}
