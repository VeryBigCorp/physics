package ryan.cphys;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import ryan.cphys.helper.Graph;
import ryan.cphys.helper.GraphSeries;
import ryan.cphys.helper.Point2D;

public class NBodyDisplay extends JPanel {
	private static final long serialVersionUID = 0L;
	
	final double DRAG_SCALE = .00825;
	final double ZOOM_SCALE = .1;
	
	int width;
	int height;
	Color color;
	float lineWidth;
	
	ArrayList<Body> bodies;
	
	Point2D xWindow = new Point2D();
	Point2D yWindow = new Point2D();
	
	int stepX, stepY;
	
	boolean displayAxes;
    public NBodyDisplay(int w, int h, Color col, float lineW, boolean showAxes, ArrayList<Body> bodies) {

        setBorder(BorderFactory.createLineBorder(Color.black));
        
        width = w;
        height = h;
        color = col;
        
        this.bodies = bodies;
        setXWindow(- 10,  10);
        setYWindow(- 10,  10);
        
        displayAxes = showAxes;
        
        
        addMouseMotionListener(new MouseMotionListener(){

        	int lastX, lastY;
			@Override
			public void mouseDragged(MouseEvent e) {
					setXWindow(xWindow.getX() + -DRAG_SCALE*(e.getX()-lastX), xWindow.getY() + -DRAG_SCALE*(e.getX()-lastX));
					setYWindow(yWindow.getX() + -DRAG_SCALE*(e.getY()-lastY), yWindow.getY() + -DRAG_SCALE*(e.getY()-lastY));
			        
					lastX = e.getX();
					lastY = e.getY();
					
					NBodyDisplay.this.validate();
					NBodyDisplay.this.repaint();
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
        for(Body b : bodies){
        	adj1 = getAdjustedPosition(b.position.cpy().div(.5*Math.pow(10,8)).proj2D());
        	
        	g.fillOval((int)adj1.x, (int)adj1.y, (int)b.radius*2,(int)b.radius*2);
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
}
