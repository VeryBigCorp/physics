package ryan.cphys.helper;

public class Point2D {
	public double x;
	public double y;
	public boolean visible = true;
	
	public Point2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Point2D(){
		x = 0;
		y = 0;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
}
