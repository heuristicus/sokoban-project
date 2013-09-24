package search;

import board.Board;

public class DiagonalDistanceHeuristic implements Heuristic<Board>{

    /**
     * This heuristic is used to calculate the player's path on the board, between the boxes.
     * It assumes no box is affected by the player's movement.
     * 
     * The value returned is actually the square of the diagonal distance, but since it's only used
     * as comparison values, avoiding to compute the square root is probably better
     * 
     * @param start
     * @param goal
     * @return a value of distance between the two points
     */
    @Override
    public float utility(Board start, Board goal) {
    	return (float)goal.getPlayerPosition().distanceSq(start.getPlayerPosition());
    }
    
}