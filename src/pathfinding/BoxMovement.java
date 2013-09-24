package pathfinding;

import java.awt.Point;

import utilities.SokobanUtil.Action;

public class BoxMovement {
	
	public final Action action;
	public final Point position;
	
	public BoxMovement(Action a, Point p) {
		this.action = a;
		this.position = p;
	}
}
