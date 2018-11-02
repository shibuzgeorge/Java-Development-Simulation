package Model;
/**
 * The class collects new orders from the InputReader class which it then passes
 * onto the <code>Robot</code> class. It also stores the order which consists of
 * items which the <code>Robot</code> class collects. This class extemds from
 * the <code>SimElement</code> class.
 * <p>
 * There may be errors due to certain classes not being implemented yet.
 * 
 * @author Azhar Zaman
 * @version 1.0
 * @since 2018-03-25
 */
public class PackingStation extends SimElement {

	// If a robot returns with an order then this variable will be set to true
	private boolean processing;

	private String[] shelvesToVisit;

	/**
	 * Initialises the Packing Station. A unique ID and Location are registered here
	 * from the <code>SimElement</code> class.
	 *
	 * @param idNum
	 *            The unique ID which identifies each Station in the simulation
	 * @param location
	 *            The coordinates where the Station is placed on the grid
	 */
	public PackingStation(int idNum, Location location) {

		super(idNum, "ps", location);
		processing = false; // The default value for this variable will be false

	}

	/**
	 * This method is used by the <code>Simulation</code> class to mark the
	 * <code>PackingStation</code> as unavailable, as it is now processing an order.
	 */
	public void startProcessing() {

		processing = true;

	}

	/**
	 * This is used to ensure the Packing Station is not asked to request another
	 * order by the <code>Simulation</code> class, as it will be processing an
	 * existing order. The boolean function will return true if the packing station
	 * is busy otherwise it will return false.
	 * 
	 * @return A boolean which, if true, means the Station is processing an order
	 */
	public boolean getProcessingInfomation() {

		return processing;

	}

	/**
	 * MIGHT BE REMOVED...
	 * <p>
	 * This returns the shelves that the robot will need to visit to complete the
	 * order. The shelf IDs are returned to the <code>Simulation</code> class which
	 * passes them onto the <code>Robot</code> class.
	 * 
	 * @return An array containing Shelf IDs
	 */
	public String[] getShelfIDs() {

		return shelvesToVisit;

	}

	/**
	 * Converts the ID and Location of the packing station to a String which can be
	 * viewed and processed by other classes.
	 * 
	 * @return A String for the Station's ID and Location
	 */
	public String toString() {

		return super.toString();

	}

}