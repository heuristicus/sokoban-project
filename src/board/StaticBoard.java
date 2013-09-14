/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package board;

/**
 *
 * @author michal
 */
public class StaticBoard {
    private static final StaticBoard INSTANCE = new StaticBoard();
    
    private StaticBoard(){};
    
    public static void initialiseStaticBoard(){ }
    
    public static StaticBoard getInstance(){
        return INSTANCE;
    }
    
}
