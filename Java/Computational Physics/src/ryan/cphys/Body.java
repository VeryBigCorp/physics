package ryan.cphys;

import ryan.cphys.helper.Vector3D;


public class Body {
	public String name;
	public Vector3D position;
	public Vector3D velocity;
	public Vector3D acceleration;
	private Vector3D centerOfMass;
	public double mass;
	public double charge;
	public double radius;
	
	public Body(String name, Vector3D position, Vector3D velocity, Vector3D acceleration, double mass, double charge, double radius){
		this.name = name;
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.mass = mass;
		this.charge = charge;
		this.radius = radius;
	}
	
	public Body(String name, Vector3D position, Vector3D velocity, Vector3D acceleration, double mass, double charge, String densityFunction, double radius){
		this(name,position,velocity,acceleration,mass,charge, radius);
		
	}
	
	public Body(String name, double x, double y, double z, double vx, double vy, double vz, double ax, double ay, double az, double mass, double charge, double radius){
		this(name, new Vector3D(x,y,z), new Vector3D(vx,vy,vz), new Vector3D(ax,ay,az), mass, charge, radius);
	}
	
	public void simulate(double dt){
		velocity.add(acceleration.cpy().scl(dt));
		//System.out.println("Acceleration: "+acceleration+" * dt = "+dt + " = "+acceleration.cpy().scl(dt));
		position.add(velocity.cpy().scl(dt));
	}
	
	public void orbitBody(Body center, double periapsis, double apoapsis, boolean clockwise, double inclination){
		double v_apoapsis = periapsis == apoapsis ? Math.sqrt(NBodySim.G*center.mass/apoapsis) : Math.sqrt((2*NBodySim.G*center.mass*(1/apoapsis-1/periapsis))/(1-Math.pow(apoapsis/periapsis, 2)));
		position = center.position.cpy().add(apoapsis, 0,0);
		velocity.y = v_apoapsis * (clockwise ? -1 : 1) * Math.cos(inclination);
		velocity.z = velocity.y * Math.sin(inclination);
	}
	
	public Vector3D forceTo(Body b){
		Vector3D displacement = position.vectorTo(b.position);
		Vector3D gravity = displacement.cpy().norm().scl(NBodySim.G*mass*b.mass).div(displacement.len2());
		Vector3D electric = displacement.cpy().norm().scl(NBodySim.k*charge*b.charge).div(displacement.len2());
		
		//System.out.println("Force of "+name + " on "+ b.name + " is "+gravity.cpy().add(electric));
		return gravity.add(electric);
	}
	
	
	public Vector3D centerOfMass(){
		return null;
	}
}
