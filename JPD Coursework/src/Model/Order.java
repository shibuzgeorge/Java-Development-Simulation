package Model;

import java.util.ArrayList;
/**
 * Represents all the orders generated for the simulation.
 * 
 * 
 * @author Shibu George
 *
 */
public class Order {
	private int processingTime;
	private boolean delivered;
	private boolean orderComplete;
	private ArrayList<StorageShelf> storageShelfs;

	/**
	 * The Order class will take parameter processingTime and ArrayList of Locations
	 * at Storage Shelfs This will be updated to the GUI in the order section. This
	 * will be used in the Simulator GUI to check which orders are completed and
	 * which needs to be assigned to a Packing Station
	 * 
	 * @param processingTime the time to process an order after delivery
	 * @param shelvesToVisit a {@link ArrayList} of all {@link Locations} that need to be visited to complete the order
	 */
	public Order(int processingTime, ArrayList<StorageShelf> shelvesToVisit) {
		this.processingTime = processingTime;
		this.storageShelfs = shelvesToVisit;
		this.delivered = false;
		this.setOrderComplete(false);
	}
	
	/**
	 * An alternative constructor for an <codde>Order</code> object
	 * @param processingTime2
	 */
	public Order(int processingTime2) {
		this.processingTime = processingTime2;
		this.storageShelfs = new ArrayList<StorageShelf>();
	}


	/**
	 * 
	 * Returns the processing time to the Simulation class
	 * @return int variable which each station needs complete the order (in ticks)
	 */
	public int getProcessingTime() {
		return processingTime;
	}
	/**
	 * This method returns an ArrayList of the StorageShelf of each Storage Shelf
	 * @return ArrayList of StorageShelf of the StorageShelf
	 */
	public ArrayList<StorageShelf> getStorageShelfs() {
		
		return storageShelfs;
		
	}
	
	

	/**
	 * 
	 * Returns a boolean value if the order has been delivered
	 * @return boolean value if delivered or not
	 */
	public boolean isDelivered() {
		return delivered;
	}

	/**
	 * 
	 * Method which takes a parameter boolean delivered and sets it to field
	 * delivered
	 * @param delivered
	 */
	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	/**
	 * Reduces the processingTime by one every time it is called
	 */
	public void process() {
		
		processingTime--;
	}

	/**
	 * Converts everything to a String and prints out the getProcessingTime and
	 * getStorageShelfs method
	 */
	public String toString() {

		String toStrings = "Processing Time: " + getProcessingTime() + " At Shelves: " + getStorageShelfs();
		return toStrings;

	}

	/** 
	 * Boolean method which checks the order is completed
	 * @return boolean order complete
	 */

	public boolean orderIsComplete() {
		return processingTime == 0;
	}

	/**
	 * Sets the orders which are completed with the parameter and assigns to the
	 * field orderComplete
	 * @param orderComplete
	 */
	public void setOrderComplete(boolean orderComplete) {
		this.orderComplete = orderComplete;
	}


	public void addShelfToVisit(StorageShelf sh) {
		this.storageShelfs.add(sh);
		
	}

}
