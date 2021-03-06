package ryan.cphys.helper;

public class Vector3D {
	public double x,y,z;
	
	public static Vector3D ZERO = new Vector3D(0,0,0);
	
	public Vector3D(){}
	
	public Vector3D(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector3D(Vector3D original){
		this(original.x, original.y, original.z);
	}
	
	public Vector3D add(Vector3D other){
		return add(other.x, other.y, other.z);
	}
	
	public Vector3D add(double dx, double dy, double dz){
		x += dx;
		y += dy;
		z += dz;
		return this;
	}
	
	public Vector3D sub(Vector3D other){
		return sub(other.x, other.y, other.z);
	}
	
	public Vector3D sub(double dx, double dy, double dz){
		x -= dx;
		y -= dy;
		z -= dz;
		return this;
	}
	
	public double dot(Vector3D b){
		return b.x* x + b.y * y + b.z * z;
	}
	
	public Vector3D norm(){
		double len = len();
		if(len == 0) return this;
		x /= len;
		y /= len;
		z /= len;
		return this;
	}
	
	public Vector3D scl(double scale){
		x *= scale;
		y *= scale;
		z *= scale;
		return this;
	}
	
	public Vector3D div(double scl){
		x /= scl;
		y /= scl;
		z /= scl;
		return this;
	}
	
	public Vector3D vectorTo(Vector3D other){
		return other.cpy().sub(this);
	}
	
	public Vector3D crs(Vector3D other){
		return new Vector3D(y*other.z - z*other.y, z*other.x - x * other.z, x*other.y - y * other.x);
	}
	
	public double len(){
		return Math.sqrt(dot(this));
	}
	
	public double len2(){
		return dot(this);
	}
	
	public Vector3D cpy(){
		return new Vector3D(this);
	}
	
	public Vector3D set(Vector3D other){
		x = other.x;
		y = other.y;
		z = other.z;
		return this;
	}
	
	public Point2D proj2D(){
		return new Point2D(x,y);
	}
	
	public String toString(){
		return "<"+x+", "+y+","+z+">";
	}
}
