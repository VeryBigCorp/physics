package ryan.cphys;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ryan.cphys.helper.Graph;
import ryan.cphys.helper.Point2D;
import ryan.cphys.helper.Vector3D;

public class Test extends JFrame {
	/**
	 * w
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Test t = new Test();
            	t.createAndShowGUI();
            }
        });
    }

    private void createAndShowGUI() {
        //System.out.println("Created GUI on EDT? "+ SwingUtilities.isEventDispatchThread());
    	setTitle("N-Body");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ArrayList<Point2D> moonX = new ArrayList<Point2D>();
        
        
        
        final ArrayList<Body> bodies = new ArrayList<Body>();
		//Body sun = new Body("Sun", 0,0,0,0,0,0,0,0,0,1.98855*Math.pow(10,30), 0);
		//bodies.add(new Body("Earth",147.09*Math.pow(10,9)+696342*Math.pow(10,3),0,0,30.29*Math.pow(10,3),0,0,5.9726*Math.pow(10,24)));
		Body earth = new Body("Earth",0,0,0,0,0,0,0,0,0,5.9726*Math.pow(10,24),0, 10);
		Body ISS = new Body("ISS", 0,0,0,0,0,0,0,0,0,450000,0, 7);
		Body moon = new Body("Moon",0,0,0,0,0,0,0,0,0,.07342*Math.pow(10,24),0, 5);
		//Body ball = new Body("ball", 6371.1*Math.pow(10,3)+1000,0,0,0,0,0,0,0,0,3,0);

		//earth.orbitBody(sun, 152098232*Math.pow(10, 3), 147098290*Math.pow(10,3), false, 0);
		ISS.orbitBody(earth, 420000+6371.1*Math.pow(10, 3), 425000+6371.1*Math.pow(10,3), false,0);
		moon.orbitBody(earth, .36333*Math.pow(10,9)+6371.1*Math.pow(10, 3), .4055*Math.pow(10,9)+6371.1*Math.pow(10, 3),false,0);
		bodies.add(earth);
		//bodies.add(ball);
		//ball.position.set(earth.position).add(6371.1*Math.pow(10,3)+1000, 0, 0);
		bodies.add(moon);
		//bodies.add(sun);
		bodies.add(ISS);
		
		//System.out.println("Vector from earth to ball: "+earth.position.vectorTo(ball.position));
		//System.out.println("Force vector from ball to earth: "+ball);
		
		final NBodySim sim = new NBodySim();
		final NBodyDisplay d = new NBodyDisplay(1000,600,Color.BLUE, 3, true, bodies);
		add(d);
		pack();
		
		new Thread(new Runnable(){
			public void run(){
				sim.simulate(bodies,3, 27*24*60*60,d); // 27*24*60*60);//365.256363004*24*60*60);
			}
		}).start();
        
        /*final Graph moonGraph = new Graph(1000,300,Color.BLUE,20, false);
        add(moonGraph, BorderLayout.NORTH);
        final Graph earthGraph = new Graph(1000,300, Color.GREEN, 5, true);
        add(earthGraph, BorderLayout.SOUTH);*/
        pack();
        setVisible(true);
        
        addComponentListener(new ComponentListener(){

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent e) {
				//moonGraph.setSize(getContentPane().getWidth(), getContentPane().getHeight()/2);
				//earthGraph.setSize(getContentPane().getWidth(), getContentPane().getHeight()/2);
				//f.pack();
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        addMouseWheelListener(new MouseWheelListener(){

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				//System.out.println("ZOOOMEMEEED "+ e.getPreciseWheelRotation());
				//earth.zoom(e.getPreciseWheelRotation());
			}
        	
        });
        
        setLocationRelativeTo(null);
    }
}
