package ryan.cphys.helper;

import ryan.cphys.NBodySim;


public class SimThread implements Runnable {
	int index;
	public boolean running;
	NBodySim sim;
	public SimThread(int index, NBodySim sim){
		this.index = index;
		this.sim = sim;
	}
	
	public void run(){
		running = true;
		Vector3D netForce = new Vector3D();
		//System.out.println("Started...");
		for(int n = 0; n < sim.bodies.size(); n++){
			if(index == n) continue;
			netForce.add(sim.bodies.get(index).forceTo(sim.bodies.get(n)));
		}
		sim.bodies.get(index).acceleration = netForce.div(sim.bodies.get(index).mass);
		//System.out.println("Finished");
		//System.out.println("Acceleration for body "+sim.bodies.get(index).name + " is "+sim.bodies.get(index).acceleration);
		running = false;
	}
}
