package ass1;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class LineGameObject extends GameObject{
		
		double[] lineColour;
		double x1;
		double x2;
		double y1;
		double y2;
		
	
	
	   public LineGameObject(GameObject parent,double [] lineColour) {
		super(parent);
		this.lineColour = lineColour;
		x1 = 0;
		x2 = 1;
		y1 = 0;
		y2 = 0;
	   }
	   
	   public LineGameObject(GameObject parent,double x1, double y1, double x2, double y2,double [] lineColour) {
			super(parent);
			this.lineColour = lineColour;
			this.x1 = x1;
			this.x2 = x2;
			this.y1 = y1;
			this.y2 = y2;
		   }
	   
		

	@Override
	    public void drawSelf(GL2 gl) {
	    	
	    	
	    	//draw line
	    	gl.glColor3f((float)lineColour[0],(float)lineColour[1],(float)lineColour[2]);
	    	gl.glBegin(GL2.GL_LINE_STRIP);
	    		gl.glVertex2d(x1, y1);
	    		gl.glVertex2d(x2, y2);
	    	gl.glEnd();
	   }

}
