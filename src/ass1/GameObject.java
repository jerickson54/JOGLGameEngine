package ass1;

import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSpinnerUI;

import com.jogamp.opengl.GL2;




/**
 * A GameObject is an object that can move around in the game world.
 * 
 * GameObjects form a scene tree. The root of the tree is the special ROOT object.
 * 
 * Each GameObject is offset from its parent by a rotation, a translation and a scale factor. 
 *
 * TODO: The methods you need to complete are at the bottom of the class
 *
 * @author malcolmr
 */
public class GameObject {

    // the list of all GameObjects in the scene tree
    public final static List<GameObject> ALL_OBJECTS = new ArrayList<GameObject>();
    
    // the root of the scene tree
    public final static GameObject ROOT = new GameObject();
    
    // the links in the scene tree
    private GameObject myParent;
    private List<GameObject> myChildren;

    // the local transformation
    //myRotation should be normalised to the range [-180..180)
    private double myRotation;
    private double myScale;
    private double[] myTranslation;
    
    // is this part of the tree showing?
    private boolean amShowing;

    /**
     * Special private constructor for creating the root node. Do not use otherwise.
     */
    private GameObject() {
        myParent = null;
        myChildren = new ArrayList<GameObject>();

        myRotation = 0;
        myScale = 1;
        myTranslation = new double[2];
        myTranslation[0] = 0;
        myTranslation[1] = 0;

        amShowing = true;
 
        
        ALL_OBJECTS.add(this);
    }

    /**
     * Public constructor for creating GameObjects, connected to a parent (possibly the ROOT).
     *  
     * New objects are created at the same location, orientation and scale as the parent.
     *
     * @param parent
     */
    public GameObject(GameObject parent) {
        myParent = parent;
        myChildren = new ArrayList<GameObject>();

        parent.myChildren.add(this);

        myRotation = 0;
        myScale = 1;
        myTranslation = new double[2];
        myTranslation[0] = 0;
        myTranslation[1] = 0;

        // initially showing
        amShowing = true;

        ALL_OBJECTS.add(this);
    }

    /**
     * Remove an object and all its children from the scene tree.
     */
    public void destroy() {
	List <GameObject> childrenList = new ArrayList<GameObject>(myChildren);
        for (GameObject child : childrenList) {
            child.destroy();
        }
        if(myParent != null)
                myParent.myChildren.remove(this);
        ALL_OBJECTS.remove(this);
    }

    /**
     * Get the parent of this game object
     * 
     * @return
     */
    public GameObject getParent() {
        return myParent;
    }

    /**
     * Get the children of this object
     * 
     * @return
     */
    public List<GameObject> getChildren() {
        return myChildren;
    }

    /**
     * Get the local rotation (in degrees)
     * 
     * @return
     */
    public double getRotation() {
        return myRotation;
    }

    /**
     * Set the local rotation (in degrees)
     * 
     * @return
     */
    public void setRotation(double rotation) {
        myRotation = MathUtil.normaliseAngle(rotation);
    }

    /**
     * Rotate the object by the given angle (in degrees)
     * 
     * @param angle
     */
    public void rotate(double angle) {
        myRotation += angle;
        myRotation = MathUtil.normaliseAngle(myRotation);
    }

    /**
     * Get the local scale
     * 
     * @return
     */
    public double getScale() {
        return myScale;
    }

    /**
     * Set the local scale
     * 
     * @param scale
     */
    public void setScale(double scale) {
        myScale = scale;
    }

    /**
     * Multiply the scale of the object by the given factor
     * 
     * @param factor
     */
    public void scale(double factor) {
        myScale *= factor;
    }

    /**
     * Get the local position of the object 
     * 
     * @return
     */
    public double[] getPosition() {
        double[] t = new double[2];
        t[0] = myTranslation[0];
        t[1] = myTranslation[1];

        return t;
    }

    /**
     * Set the local position of the object
     * 
     * @param x
     * @param y
     */
    public void setPosition(double x, double y) {
        myTranslation[0] = x;
        myTranslation[1] = y;
    }

    /**
     * Move the object by the specified offset in local coordinates
     * 
     * @param dx
     * @param dy
     */
    public void translate(double dx, double dy) {
        myTranslation[0] += dx;
        myTranslation[1] += dy;
    }

    /**
     * Test if the object is visible
     * 
     * @return
     */
    public boolean isShowing() {
        return amShowing;
    }

    /**
     * Set the showing flag to make the object visible (true) or invisible (false).
     * This flag should also apply to all descendents of this object.
     * 
     * @param showing
     */
    public void show(boolean showing) {
        amShowing = showing;
    }

    /**
     * Update the object. This method is called once per frame. 
     * 
     * This does nothing in the base GameObject class. Override this in subclasses.
     * 
     * @param dt The amount of time since the last update (in seconds)
     */
    public void update(double dt) {
        // do nothing
    }

    /**
     * Draw the object (but not any descendants)
     * 
     * This does nothing in the base GameObject class. Override this in subclasses.
     * 
     * @param gl
     */
    public void drawSelf(GL2 gl) {
        // do nothing
    }

    
    // ===========================================
    // COMPLETE THE METHODS BELOW
    // ===========================================
    
    /**
     * Draw the object and all of its descendants recursively.
     * 
     * TODO: Complete this method
     * 
     * @param gl
     */
    public void draw(GL2 gl) {
        
    	
    	
        // don't draw if it is not showing
        if (!amShowing) {
            return;
        }

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        
       //shouldnt i only be calling this once?
        //doesnt work if i wrap in if clause for only root
        
        
        //only display if i load identity
        //gl.glLoadIdentity();
        
       
        
        gl.glPushMatrix();
        gl.glTranslated(myTranslation[0],myTranslation[1],0);
        //just 2d so want to rotate around z axis
        gl.glRotated(myRotation,0,0,1);
        gl.glScaled(myScale,myScale,1);
        drawSelf(gl);
        
        //loop through all children and draw
        for(GameObject c: myChildren)
        	c.draw(gl); //draw children recursively
        
        //finished drawing so now we pop the matrix
        gl.glPopMatrix();
        
        
       
        
    }

    /**
     * Compute the object's position in world coordinates
     * 
     * TODO: Write this method
     * 
     * @return a point in world coordinates in [x,y] form
     */
    public double[] getGlobalPosition() {
    	
    	
    	//initialize as root to start
    	double [] parentPosition = ROOT.getPosition();
    	double parentRotation = ROOT.getRotation();
    	double parentScale = ROOT.getScale();
    	
    	if(this.myParent == null)
    		return parentPosition;
    	
    	else{
    		parentPosition = myParent.getGlobalPosition();
    		parentRotation = myParent.getGlobalRotation();
    		parentScale = myParent.getGlobalScale();
    		
    		//m*t*r*s = result
    		//m*t
    		double[][]result = MathUtil.translationMatrix(parentPosition);
    		//*r
    		result = MathUtil.multiply(result, MathUtil.rotationMatrix(parentRotation));
    		//*s
    		result = MathUtil.multiply(result, MathUtil.scaleMatrix(parentScale));
    		//now multiply current third column by result
    		double [] thirdColumn = new double[3];
    		thirdColumn[0] = myTranslation[0];
    		thirdColumn[1] = myTranslation[1];
    		//third column always 1, but we need it
    		thirdColumn[2] = 1;
    		
    		return MathUtil.multiply(result,thirdColumn);
    		
    		
    		
    		
    	}
    	
        
     
    	
    }

    /**
     * Compute the object's rotation in the global coordinate frame
     * 
     * TODO: Write this method
     * 
     * @return the global rotation of the object (in degrees) and 
     * normalized to the range (-180, 180) degrees. 
     */
    public double getGlobalRotation() {
    	
    	if(this.getParent() == null)
    		return this.getRotation();
    	else //make sure the angle is normalized
    	return MathUtil.normaliseAngle(myParent.myRotation + myRotation);
    	
    }

    /**
     * Compute the object's scale in global terms
     * 
     * TODO: Write this method
     * 
     * @return the global scale of the object 
     */
    public double getGlobalScale() {
    	
    	//if there is no parent, simply return my own scale
    	if(this.getParent() == null)
    		return this.myScale;
    	else
    	return myScale * myParent.getGlobalScale();
    }

    /**
     * Change the parent of a game object.
     * 
     * TODO: add code so that the object does not change its global position, rotation or scale
     * when it is reparented. You may need to add code before and/or after 
     * the fragment of code that has been provided - depending on your approach
     * 
     * @param parent
     */
    public void setParent(GameObject parent) {
    	
    	
        
    	//MAKE SURE THIS IS ABOVE. CANT MESS UP RELATIONSHIPS
    	double[] globalPosition = getGlobalPosition();
        double globalRotation = getGlobalRotation();
        double globalScale = getGlobalScale();
        
    	//first remove the children of the to be parent
        myParent.myChildren.remove(this);
        //next set the parent
        myParent = parent;
        //give the parent caller as child
        myParent.myChildren.add(this);
        
        
        
        //update Rotation, inverse of addition is subtraction
        myRotation = MathUtil.normaliseAngle(globalRotation-myParent.getGlobalRotation());
        //update scale, inverse of multiplication is division
        myScale = globalScale/myParent.getGlobalScale();
        
        double parentGPosition [] = myParent.getGlobalPosition();
        //need to inverse to find result
        //vector so we have to make variable for this
        double invertParentPosition[] = new double [3];
        invertParentPosition[0] = parentGPosition[0]*-1;
        invertParentPosition[1] = parentGPosition[1] * -1;
        invertParentPosition[2] = 1;
        
        //s^-1 * r^-1 * t^-1
        double inverseMatrix [][] = MathUtil.scaleMatrix(1/myParent.getGlobalScale());
        //*r^-1
        inverseMatrix = MathUtil.multiply(inverseMatrix,MathUtil.rotationMatrix(myParent.getGlobalRotation()*-1));
        //*t^-1
        inverseMatrix = MathUtil.multiply(inverseMatrix, MathUtil.translationMatrix(invertParentPosition));
        
        //now take result and multiply with globalPosition
        myTranslation = MathUtil.multiply(inverseMatrix,globalPosition);
        
        
        
        
        
        
        
    }
    
    
    
    public void printMatrix(double[][] printMe){
    	for(int i = 0; i < 3; ++i){
    		for(int j = 0; j < 3; ++j){
    			System.out.print(printMe[i][j] + " , ");
    		}
    		System.out.println();
    	}
    	
    }
    
    /*
    public static void main(String args[]){
    	GameObject parent = new GameObject(GameObject.ROOT);
        GameObject child = new GameObject(parent);
        
        
        System.out.println("First TIME:::");
        
        parent.translate(-2, 3);
        parent.rotate(90);
        parent.scale(2);
        
        // the child is also moved:
        
        double[] p = child.getGlobalPosition();
        double r = child.getGlobalRotation();
        double s = child.getGlobalScale();
        
        System.out.println("Second TIME:::");
        
        child.translate(1, 0);
        child.rotate(-90);
        child.scale(0.5);

        p = child.getGlobalPosition();
        r = child.getGlobalRotation();
        s = child.getGlobalScale();
        
        p = parent.getGlobalPosition();
        r = parent.getGlobalRotation();
        s = parent.getGlobalScale();
        
    }
   	*/

}
