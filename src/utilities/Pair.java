/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author michal
 */
public class Pair<T,U> {
    
    public T first;
    public U second;
    
    public Pair(T first, U second){
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "First: " + first.toString() + ", Second: " + second.toString();
    }

    /**
     * Two pairs are equal if both the first and second objects of each pair
     * are equal.
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Pair){
            Pair other = (Pair) obj;
            return other.first.equals(this.first) && other.second.equals(this.second);
        }
        return false;
    }
        
}
