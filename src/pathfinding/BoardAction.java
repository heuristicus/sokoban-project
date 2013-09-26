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
    
}
