package pathfinding;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import utilities.SokobanUtil.Action;

public class BoardAction {
	
	public final Action action;
	public final Point position;
	
	public BoardAction(Action a, Point p) {
		this.action = a;
		this.position = p;
	}
    
    public static List<Action> convertToActionList(List<BoardAction> bList){
        List<Action> aList = new ArrayList<>();
        for (BoardAction boardAction : bList) {
            aList.add(boardAction.action);
        }
        return aList;
    }

    /**
     * A boardAction object is considered to be equal to another if both the action
     * and the point it is being applied to are the same
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoardAction){
            BoardAction other = (BoardAction)obj;
            return other.action.equals(this.action) && other.position.equals(this.position);
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Action: " + action.toString() + ", Position: " + position.toString();
    }
    
    
    
}
