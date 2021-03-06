package ryan.cphys;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ryan.cphys.helper.SimThread;

public class NBodySim {
	
	public static double G = 6.6731*Math.pow(10, -11);
	public static double ε_o = 8.854187817*Math.pow(10,-12);
	public static double k = 1/(4*Math.PI*ε_o);
	public static double SECONDS_TO_DAYS = 1.0/60/60/24;
	public ArrayList<Body> bodies;
	ExecutorService _pool;
	
	public void simulate(ArrayList<Body> blist, double step, double simTime, NBodyDisplay g){
		_pool = Executors.newCachedThreadPool();
		this.bodies = blist;
		SimThread[] threads = new SimThread[blist.size()];
		try {
			
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(blist.size() + "_body_"+simTime+".csv")));
			double time = 0;
			initThreads(threads);
			long startTime = System.currentTimeMillis();
			System.out.println("Going for "+(int)(simTime/step)+" iterations");
			for(int iteration = 0; iteration <= (int)(simTime/step);iteration++, time+= step){
				/*for(Body b : bodies){
					b.acceleration.scl(0);
				}*/
				/*for(SimThread t : threads)
					t.join();*/
				for(SimThread t : threads){
					_pool.execute(t);
					//System.out.println("Started threade");
				}
				boolean waiting = false;
				do {
					waiting = false;
					for(int i = threads.length; --i >= 0;)
						if(threads[i].running)
							waiting = true;
				} while(waiting);
				
				/*for(int i = 0; i < bodies.size(); i++){
					Vector3D netForce = new Vector3D();
					//System.out.println("Started...");
					for(int n = 0; n < bodies.size(); n++){
						if(i != n){
							netForce.add(bodies.get(i).forceTo(bodies.get(n)));
						}
					}
					bodies.get(i).acceleration = netForce.div(bodies.get(i).mass);
					//System.out.println("Net force "+bodies.get(i).name + " is "+netForce);
					//System.out.println("Acceleration ("+bodies.get(i).name+"): "+bodies.get(i).acceleration + ", len: "+bodies.get(i).acceleration.len());
				}*/
				for(Body b : bodies)
					b.simulate(step);
				
				if(iteration % 100 == 0 || iteration == (int)(simTime/step)){
					g.invalidate();
					g.repaint();
					System.out.println("Time: "+time + " ("+time*SECONDS_TO_DAYS+" days)");
				}
				
				Thread.sleep(10, 0);
			}
			long diff = System.currentTimeMillis()-startTime;
			
			System.out.println("Completed "+simTime + " seconds of "+bodies.size()+"-body simulation in "+diff/1000.0f + " seconds real-time");
			_pool.shutdown();
			pw.flush();
			pw.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void initThreads(SimThread[] threads){
		for(int i = threads.length; --i >= 0;)
			threads[i] = new SimThread(i, this);
	}
	
}

