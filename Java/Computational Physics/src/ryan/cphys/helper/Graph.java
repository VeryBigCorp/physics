package ryan.cphys.helper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class Graph extends JPanel {
	
	final double DRAG_SCALE = .00825;
	final double ZOOM_SCALE = .1;
	
	int width;
	int height;
	Color color;
	float lineWidth;
	
	ArrayList<GraphSeries> series;
	
	Point2D xWindow = new Point2D();
	Point2D yWindow = new Point2D();
	
	int stepX, stepY;
	
	boolean displayAxes;
    public Graph(int w, int h, Color col, float lineW, boolean showAxes) {

        setBorder(BorderFactory.createLineBorder(Color.black));
        
        width = w;
        height = h;
        color = col;
        
        series = new ArrayList<GraphSeries>();
        setXWindow(-10, 10);
        setYWindow(-10, 10);
        
        displayAxes = showAxes;
        
        
        addMouseMotionListener(new MouseMotionListener(){

        	int lastX, lastY;
			@Override
			public void mouseDragged(MouseEvent e) {
					setXWindow(xWindow.getX() + -DRAG_SCALE*(e.getX()-lastX), xWindow.getY() + -DRAG_SCALE*(e.getX()-lastX));
					setYWindow(yWindow.getX() + -DRAG_SCALE*(e.getY()-lastY), yWindow.getY() + -DRAG_SCALE*(e.getY()-lastY));
			        
					lastX = e.getX();
					lastY = e.getY();
					
					Graph.this.validate();
					Graph.this.repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				lastX = e.getX();
				lastY = e.getY();
			}
        	
        });
        
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(width,height);
    }
    
    public void setXWindow(double min, double max){
    	xWindow.x = min;
    	xWindow.y = max;
    	fixOrigin();
    }
    
    public void setYWindow(double min, double max){
    	yWindow.x = min;
    	yWindow.y = max;
    	fixOrigin();
    }
    
    public void shiftXWindow(double shift){
    	xWindow.x += shift;
    	xWindow.y += shift;
    	fixOrigin();
    }
    
    public void shiftYWindow(double shift){
    	yWindow.x += shift;
    	yWindow.y += shift;
    	fixOrigin();
    }
    
    public void setXAxisStep(double step){
    	
    }
    
    public void zoom(double amt){
    	shiftXWindow(ZOOM_SCALE*amt);
    	shiftYWindow(-ZOOM_SCALE*amt);
    	System.out.println(xWindow);
    	
    	validate();
    	repaint();
    	
    	fixOrigin();
    	
    }
    
    public void setSize(int w, int h){
    	super.setSize(w, h);
    	
    	width = w;
    	height = h;
    	
    	fixOrigin();
    }
    
    Point2D o;
    public Point2D getOrigin(){
    	return o;
    }
    
    public void fixOrigin(){
    	if(o == null)
    		o = new Point2D();
    	o.x = -width*xWindow.getX()/(xWindow.getY()-xWindow.getX());
    	o.y = -height*yWindow.getX()/(yWindow.getY()-yWindow.getX());
   

        stepX = (int) Math.round(width/Math.abs((xWindow.getY()-xWindow.getX()))); // Find step in each direction
        stepY = (int) Math.round(height/Math.abs((yWindow.getY()-yWindow.getX())));
    	
    }
    
    Point2D adj1 = new Point2D(), adj2 = new Point2D();
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);    
        // Draw axes
        final Graphics2D g = (Graphics2D)gr;
        
        
        g.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        
        
        if(displayAxes){
	        g.setColor(Color.BLACK);
	        g.setStroke(new BasicStroke(3));
	        g.draw(new Line2D.Double(-xWindow.getX()*stepX, 0, -xWindow.getX()*stepX, height));
	        g.draw(new Line2D.Double(0, -yWindow.getX()*stepY, width, -yWindow.getX()*stepY));
        }
        
        g.setColor(color);
        g.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 3, 1 }, 0));
        for(int k = 0; k < series.size(); k++){
	        for(int i = 0; i < series.get(i).points.size()-1; i++){
	        	//if(points.get(i).visible || points.get(i+1).visible){
	        	//System.out.println("Visible points "+ visible.size());
	        		g.draw(new Line2D.Double(getOrigin().getX() + series.get(i).points.get(i).x*stepX, getOrigin().getY() - series.get(i).points.get(i).getY()*stepY, getOrigin().getX() + series.get(i).points.get(i+1).x*stepX, getOrigin().getY() - series.get(i).points.get(i+1).getY()*stepY));
	        	//}
	        }
        }
    }
    
    Point2D cpy = new Point2D();
    public Point2D getAdjustedPosition(Point2D pos){
    	pos.x = getOrigin().getX() + pos.getX()*stepX;
    	pos.y = getOrigin().getY() - pos.getY()*stepY;
    	return pos;
    }
    
    Point2D vCpy;
    public boolean pointVisible(Point2D c){
    	vCpy = c;
    	return (vCpy.x >= 0 && vCpy.y >= 0 && vCpy.x <= width && vCpy.y <= height);
    }
    
   /*class VisibleThread extends Thread {
    	boolean checking;
    	int i = 0;
    	public void run(){
    		while(true){
    			System.out.println("Points: "+points.size());
    			if(checking){
    				try {
    				//System.out.println("The size is "+points.size());
			    		for (i = 0; i < points.size(); i++) {
			    			//System.out.println("Checking "+points.size()+" points");
							//points.get(i).visible = pointVisible(getAdjustedPosition(points.get(i)));
							if(points.get(i).visible){
								//System.out.println("Visible!");
								if(!visible.contains(points.get(i))){
									visible.add(points.get(i));
									//System.out.println("Adding!");
								}
							} else {
								if(visible.contains(points.get(i)))
									visible.remove(visible.indexOf(points.get(i)));
							}
						}
			    		checking = false;
    				} catch(Exception e){
    					e.printStackTrace();
    				}
    			}
    		}
    	}
    	
    	public void reset(){
    		i = points.size();
    		checking = true;
    		//System.out.println("Reset");
    	}
    }*/
}

