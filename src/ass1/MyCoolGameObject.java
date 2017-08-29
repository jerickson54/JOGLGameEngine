package ass1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jogamp.opengl.GL2;

public class MyCoolGameObject extends GameObject {
	
	  private double[] myFillColour;
	  private double[] myLineColour;
	  public PolygonalGameObject initial;
	  private PolygonalGameObject firstChild;
	  private PolygonalGameObject secondChild;
	  
	 
	public MyCoolGameObject(){
		//makes the triforce!
		super(GameObject.ROOT);
		double yellow[] = {1,1,0,1};
		double points[][] = { {0,0}, {1,0}, {0.5,1} };
		initial = new PolygonalGameObject(this,Arrays.asList(points),yellow,yellow);
		firstChild =  new PolygonalGameObject(initial,Arrays.asList(points),yellow,yellow);
		firstChild.translate(0.5, -1);
		secondChild =  new PolygonalGameObject(firstChild,Arrays.asList(points),yellow,yellow);
		secondChild.translate(-1, 0);
		
		
	}
	    

	public MyCoolGameObject(GameObject parent, double[] myFillColour, double[] myLineColour) {
		//makes the triforce!
		super(parent);
		this.myFillColour = myFillColour;
		this.myLineColour = myLineColour;
		double points[][] = { {0,0}, {1,0}, {0.5,1} };
		initial = new PolygonalGameObject(this,Arrays.asList(points),myFillColour,myLineColour);
		firstChild =  new PolygonalGameObject(initial,Arrays.asList(points),myFillColour,myLineColour);
		firstChild.translate(0.5, -1);
		secondChild =  new PolygonalGameObject(firstChild,Arrays.asList(points),myFillColour,myLineColour);
		secondChild.translate(-1, 0);
		
		
	}
	
	@Override
	public void drawSelf(GL2 gl){
	
		
	}

	
}
