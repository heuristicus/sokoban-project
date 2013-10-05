/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.awt.Point;

/**
 *
 * @author michal
 */
public class WeightedPoint {
    public int cost;
    public Point point;
    
    public WeightedPoint(Point point, int cost)
    {
        this(point.x,point.y,cost);
    }
    
    public WeightedPoint(int x, int y, int cost)
    {
        point = new Point(x,y);
        this.cost = cost;
    }
    
    @Override
    public boolean equals(Object o)
    {
        return (o instanceof WeightedPoint && ((WeightedPoint)o).point.x == point.x && ((WeightedPoint)o).point.y == point.y);
    }
}
