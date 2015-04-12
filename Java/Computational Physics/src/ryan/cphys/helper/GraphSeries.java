package ryan.cphys.helper;

import java.awt.Color;
import java.util.ArrayList;

public class GraphSeries {
	ArrayList<Point2D> points;
	
	ArrayList<Point2D> visiblePoints;
	
	Graph g;
	
	public GraphSeries(Graph g, Color color){
		points = new ArrayList<Point2D>();
		this.g = g;
	}
	
	public void addPoints(ArrayList<Point2D> points){
		this.points.addAll(points);
	}
	
	public void addPoint(Point2D p){
		points.add(p);
	}
	
	public ArrayList<Point2D> getVisiblePoints(){
		return visiblePoints;
	}
	
	Point2D adj;
	public void populateVisible(){
		for(int i = 0; i < points.size(); i++){
			adj = g.getAdjustedPosition(points.get(i));
		}
	}
}
