package Model; 

import java.util.ArrayList;

/**
 * Represents a <code>Robot</code> that moves and picks up items
 * 
 * @author Daniel Ahmed and Arun Bahra
 * @version 1.0
 */

public class Robot extends SimElement implements Actor {
	private static final double MAX_RATIO_BEFORE_RECALCULATE_PATH = 0.15;
	// The robot's level of charge
	private int initialChargeLevel;
	private int speedLevel;
	//Where the robot was before it moved
	private Location previousLocation;
	// Where the robot needs to go
	private ArrayList<Location> path;
	//The robot's current battery level
	private int currentChargeLevel;
	
	/**
	 * A constuctor for a <code>Robot</code> object
	 * @param idNum the unique id for a <code>Robot</code>
	 * @param currentLoc A <code>Robot's</code> starting {@linkLocation}
	 * @param charge The initial battery level for a <code>Robot</code>
	 * @param speedlevel Indicates how fast a <code>Robot</code> wil charge in one tick
	 */
	public Robot(int idNum, Location currentLoc, int charge, int speedlevel) {
		super(idNum, "r", currentLoc);
		this.initialChargeLevel = charge;
		this.currentChargeLevel = charge;
		this.speedLevel = speedlevel;
		this.path = new ArrayList<Location>();
		this.previousLocation = null;
	}

	/**
	 * An accessor method for the robot's fields
	 * 
	 * @return The robot's charge level
	 */
	public int getCurrentChargelevel() {
		return currentChargeLevel;
	}
	
	/**
	 * 
	 * @return
	 */
	
	public int getSpeedlevel() {
		return speedLevel;
	}

	/**
	 * Moves the robot to a new {@link Location} using the {@link AStarPathFinder}
	 * 
	 * @param destinations where the <code>Robot</code> needs to go
	 * @param obstacles what the <code>Robot</code> needs to avoid
	 * @param xDim the horizontal dimension of where the <code>Robot</code> is operating
	 * @param yDim the vertical dimension of where the <code>Robot</code> is operating 
	 */

	public void move(ArrayList<Location> destinations, ArrayList<Location> obstacles, int xDim, int yDim) {
		if(path.isEmpty() || needToRecalculatePath(obstacles, xDim, yDim))
		{
			AStarPathFinder pf = new AStarPathFinder(location, destinations, obstacles, xDim, yDim);
			pf.start();
			setPath(pf.getTotalPath());
		}
		
		if(!path.isEmpty())
		{
			previousLocation = location;
			setLocation(path.get(0));
			path.remove(0);
			path.trimToSize();
		}
	}
	/**
	 * A mutator the <code>Robot's</code> fields
	 * @param location the previous location of the <code>Robot</code> to set
	 */

	public void setPreviousLocation(Location location) {
		this.previousLocation = location;
	}
	
	
	/** 
	 * An accessor for the <code>Robot's</code> fields
	 * @return the <code>Robot's</code> previous location
	 */
	public Location getPreviousLocation()
	{
		return previousLocation;
	}
	
	/**
	 * Checks whether the <code>Robot</code> needs ton recalculate its path
	 * @param destinations where the <code>Robot</code> needs to go
	 * @param obstacles what the <code>Robot</code> needs to avoid
	 * @param xDim the horizontal dimension of where the <code>Robot</code> is operating
	 * @param yDim the vertical dimension of where the <code>Robot</code> is operating 
	 * @return a <code>boolean</code> pertaining whether the path needs to be recalculated
	 */

	private boolean needToRecalculatePath(ArrayList<Location> obstacles, int xDim, int yDim) {
		double ratio  = obstacles.size() / (xDim * yDim);
		return (ratio >= MAX_RATIO_BEFORE_RECALCULATE_PATH);
	}
	/**
	 * A mutator for the <code>Robot's</code> methods
	 * @param path the path for the <code>Robot</code> to take
	 */
	private void setPath(ArrayList<Location> path) {
		this.path = path;
	}

	/**
	 * Generates a <code>String</code> representation of a <code>Robot</code>
	 * 
	 * @return a <code>String</code> of the ID, position and charge for a
	 *         <code>Robot</code>
	 * @see SimElement#toString()
	 */

	public String toString() {
		return super.toString() + " Charge: " + currentChargeLevel + " Speed " + speedLevel;
	}

	/**
	 * An accessor for the <code>Robot's</code> fields
	 * @return a {@link ArrayList} of {@link Locations} the path to take 
	 */
	public ArrayList<Location> getPath()
	{
		return path;
	}
	
	/**
	 * Finds whether the <code>Robot</code> needs to recalculate its path
	 * @param xDim the horizontal dimension of where the <code>Robot</code> is operating
	 * @param yDim the vertical dimension of where the <code>Robot</code> is operating 
	 * @return a <code>boolean</code> pertaining whether the path must be recalculated
	 */
	public boolean isAvailable(int xDim, int yDim) {
		int minValue = ((xDim * yDim) / 2 + 2) ;
		System.out.println(currentChargeLevel > minValue && path.isEmpty());
		return (currentChargeLevel > minValue && path.isEmpty());
	}
	
	/**
	 * Finds whether the <code>Robot</code> has run out of charge
	 * @return a <code>boolean</code> pertaining whether the <code>Robot</code> has run out charge or not
	 */
	public boolean isDead() {
		return currentChargeLevel == 0;
	}
	
	/**
	 *  An accessor for the <code>Robot's</code> fields
	 * @return the robot's initial charge level
	 */
	
	public int getInitialChargeLevel() {
		return initialChargeLevel;
	}

	/**
	 * Increases the charge of a <code>Robot</code>
	 */
	public void increaseCharge() {
		currentChargeLevel += speedLevel;
		if(currentChargeLevel > initialChargeLevel)
		{
			currentChargeLevel = initialChargeLevel;
		}
	}
	/**
	 * Decreases the charge of a <code>Robot</code>
	 */
	public void decreaseCharge() {
		this.currentChargeLevel --;
	}
}