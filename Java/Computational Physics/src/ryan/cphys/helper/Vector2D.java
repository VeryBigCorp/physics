package ryan.cphys.helper;

public class Vector2D {
	public double x = 0;
	public double y = 0;
	
	public static Vector2D ZERO = new Vector2D(0,0);
	
	public Vector2D(){}
	
	public Vector2D(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Vector2D original){
		this(original.x, original.y);
	}
	
	public Vector2D add(Vector2D other){
		return add(other.x, other.y);
	}
	
	public Vector2D add(double dx, double dy){
		x += dx;
		y += dy;
		return this;
	}
	
	public Vector2D sub(Vector2D other){
		return sub(other.x, other.y);
	}
	
	public Vector2D sub(double dx, double dy){
		x -= dx;
		y -= dy;
		return this;
	}
	
	public double dot(Vector2D b){
		return b.x* x + b.y * y;
	}
	
	public double angle(){
		return Math.atan2(y, x);
	}
	
	public Vector2D norm(){
		double len = len();
		if(len == 0) return this;
		x /= len;
		y /= len;
		return this;
	}
	
	public Vector2D scl(double scale){
		x *= scale;
		y *= scale;
		return this;
	}
	
	public Vector2D div(double scl){
		x /= scl;
		y /= scl;
		return this;
	}
	
	public Vector2D vectorTo(Vector2D other){
		return other.cpy().sub(this);
	}
	
	public Vector3D crs(Vector2D other){
		return new Vector3D(this.x, this.y, 0).crs(new Vector3D(other.x,other.y,0));
	}
	
	public double len(){
		return Math.sqrt(dot(this));
	}
	
	public double len2(){
		return dot(this);
	}
	
	public Vector2D dir(Vector2D other){
		return other.cpy().norm().scl(len());
	}
	
	public Vector2D cpy(){
		return new Vector2D(this);
	}
	
	public String toString(){
		return "["+x+", "+y+"]";
	}
}
