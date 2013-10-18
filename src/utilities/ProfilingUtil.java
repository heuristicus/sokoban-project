/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author michal
 */
public class ProfilingUtil {
    
    public static boolean useProfiling = false;
    public static int discardedNodes = 0;
    public static int expandedNodes = 0;
    public static int openedNodes = 0;
    public static long timeOut = 0; // The time in nanos when the search should time out
    static int count;
    
    
    /**
     * Reset the tracked variables to some initial value.
     */
    public static void reset(){
        discardedNodes = 0;
        expandedNodes = 0;
        openedNodes = 0;
        timeOut = 0;
    }
    
     /**
     * 
     * @return true if timed out
     */
    public static boolean checkTimeOut(){
        if (useProfiling){
             if (count++ == 20){
                count = 0;
                if (System.nanoTime() > timeOut)
                    return true;
            }
            return false;
        }
        return false;
    }
    
}
