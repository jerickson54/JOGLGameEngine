package ass1;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class CircularGameObject extends GameObject {

	double[] fillColour;
	double[] lineColour;
	double radius;
	
	public CircularGameObject(GameObject parent,double[]fillColour,double[]lineColour) {
		super(parent);
		this.fillColour = fillColour;
		this.lineColour = lineColour;
		radius = 1;
	}
	
	public CircularGameObject(GameObject parent, double radius,double[]fillColour,double[]lineColour) {
		super(parent);
		this.fillColour = fillColour;
		this.lineColour = lineColour;
		this.radius = radius;
	}

	public double[] getFillColour() {
		return fillColour;
	}

	public void setFillColour(double[] fillColour) {
		this.fillColour = fillColour;
	}

	public double[] getLineColour() {
		return lineColour;
	}

	public void setLineColour(double[] lineColour) {
		this.lineColour = lineColour;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
    @Override
    public void drawSelf(GL2 gl) {
    	
    	int numVertices = 32;
    	double angle = 0;
    	double angleIncrement = 2*Math.PI/numVertices;
    	
    	
    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_FILL);
    	gl.glColor3f((float) fillColour[0],(float)fillColour[1],(float)fillColour[2]);
    	gl.glBegin(GL2.GL_TRIANGLE_FAN);
    		//center of the circle
    		gl.glVertex2d(0,0);
    		for(int i = 0; i <= numVertices; ++i){
    			angle = i*angleIncrement;
    			double x = radius * Math.cos(angle);
    			double y = radius * Math.sin(angle);
    			gl.glVertex2d(x,y);
    		}
    	gl.glEnd();
    	
    	if(lineColour!=null){
    	gl.glBegin(GL2.GL_LINE_STRIP);
    	gl.glPolygonMode(GL2.GL_FRONT_AND_BACK,GL2.GL_LINE);
    	gl.glColor3f((float) lineColour[0],(float)lineColour[1],(float)lineColour[2]);
    	
		for(int i = 0; i <= numVertices; ++i){
			angle = i*angleIncrement;
			double x = radius * Math.cos(angle);
			double y = radius * Math.sin(angle);
			gl.glVertex2d(x,y);
		}
    	gl.glEnd();
    	}
    		
    		
    		
    }

	
}
