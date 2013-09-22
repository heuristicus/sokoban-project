/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author michal
 */
@SuppressWarnings("serial")
public class IllegalMoveException extends Exception {
    public IllegalMoveException(){
        
    }

    public IllegalMoveException(String message) {
        super(message);
    }
}
