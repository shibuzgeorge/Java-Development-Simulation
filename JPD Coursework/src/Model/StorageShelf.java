package Model;

/**
 * Represents a Storage shelf with id and location <code>Location</code>
 * 
 * @author Shibu George
 * @version 1.0
 *
 */
public class StorageShelf extends SimElement {
	private boolean visited;

	/**
	 * Creates a new <code>StorageShelf</code>
	 * 
	 * @param id
	 *            the unique ID for the the element
	 * @param location
	 *            the (x,y) position for the element Calls the super class and sends
	 *            the parameter to the <code>SimElement</code>
	 */

	public StorageShelf(int idNum, Location location) {
		super(idNum, "ss", location);
		visited = false;
	}

	/**
	 * Generates a <code>String</code> representation of a <code>Location</code>
	 * 
	 * @return a <code>String</code> of the x and y coordinates for the
	 *         <code>Location</code>
	 */
	public String toString() {
		return super.toString();
	}

	/**
	 * An accessor for the shelf's fields
	 * 
	 * @return a <code>boolean</code> pertaining whether the shelf has been visited
	 *         if part of an order
	 */

	public boolean isVisited() {
		return visited;
	}

	/**
	 * A mutator for the shelf's fields
	 * 
	 * @param visited
	 *            the <code>boolean</code> value to set
	 */

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}