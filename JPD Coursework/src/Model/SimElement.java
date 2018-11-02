package Model;

/**
 * Represents an element of the simulation All elements have a unique ID and a
 * <code>Location</code>
 * 
 * @author Arun Bahra
 * @version 1.0
 *
 */
public abstract class SimElement {
	protected Location location; // the location for the element
	protected int idNum; // the unique ID number for an element
	protected String idName;//

	/**
	 * Creates a new <code>SimElement</code>
	 * 
	 * @param id
	 *            the unique ID number for the the element
	 * @param idName
	 *            the identifier for the type of element e.g. "R" for Robot
	 * @param location
	 *            the (x,y) position for the element
	 */
	public SimElement(int idNum, String idName, Location location) {
		this.idNum = idNum;
		this.location = location;
		this.idName = idName;
	}

	/**
	 * Returns <code>String</code> of the element's unique ID and number
	 * 
	 * @return The element's unique ID and number
	 */
	public String getFullId() {
		return idName + idNum;
	}

	/**
	 * Returns <code>int</code> of the element's unique number
	 * 
	 * @return the element's unique number
	 */

	public int getIdNum() {
		return idNum;
	}

	/**
	 * Returns <code>Location</code> of the element's location
	 * 
	 * @return the element's location
	 * @see Location
	 */

	public Location getLocation() {
		return location;
	}

	/**
	 * Sets the location of a <code>SimElement</code>
	 * 
	 * @param newLoc
	 *            the location to set
	 */

	public void setLocation(Location newLoc) {
		this.location = newLoc;
	}

	/**
	 * Generates a <code>String</code> representation of a <code>SimElement</code>
	 * 
	 * @return a <code>String</code> of the ID and position for a
	 *         <code>SimElement</code>
	 * @see Location#toString()
	 */
	public String toString() {
		return "ID: " + getFullId() + " Location: " + location;
	}
}