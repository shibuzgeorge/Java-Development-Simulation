package Model;

/**
 * Represents a position for a simulation element to be placed at
 * 
 * @author Arun Bahra
 * @version 1.0
 */
public class Location {
	// The x (horizontal) coordinate of the element's location
	private int x;
	// The y (vertical) coordinate of the element's location
	private int y;

	/**
	 * Creates a <code>Location</code> and sets coordinates
	 * 
	 * @param x
	 *            The x coordinate of the <code>Location</code> to create
	 * @param y
	 *            The y coordinate of the <code>Location</code> to create
	 */

	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns whether an <code>Object</code> is equal to the <code>Location</code>
	 * it is being compared to
	 * 
	 * @param o
	 *            the <code>Object</code> to compare to the <code>Location</code>
	 * @return <code>boolean</code> found by comparing the objects
	 */
	public boolean equals(Object o) {
		if (o instanceof Location) {
			Location Loc = (Location) o;

			if (Loc.getX() == this.x && Loc.getY() == this.y) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns an <code>integer</code> of the location's horizontal position
	 * 
	 * @return the location's horizontal position
	 */

	public int getX() {
		return x;
	}

	/**
	 * Returns an <code>integer</code> of the location's horizontal position
	 * 
	 * @return the location's horizontal position
	 */

	public int getY() {
		return y;
	}

	/**
	 * Generates a <code>String</code> representation of a <code>Location</code>
	 * 
	 * @return a <code>String</code> of the x and y coordinates for the
	 *         <code>Location</code>
	 */
	public String toString() {
		return "x = " + x + ", y = " + y;
	}
}