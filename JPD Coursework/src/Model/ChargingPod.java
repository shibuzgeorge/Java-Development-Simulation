package Model;

/**
 * Represents charging pod which allows a specific robot to charge when at the
 * pod's location
 * 
 * @author Rajan Malhi
 * @version 1.0
 *
 */
public class ChargingPod extends SimElement {
	public ChargingPod(int idNum, Location location, Robot robot) {
		super(idNum, "C", location);
	}

	/**
	 * Generates a <code>String</code> representation of <code>ChargingPod</code>
	 * 
	 * @return a <code>String</code> of the ID,the location and charge of the
	 *         <code>ChargingPod</code>
	 */

	public String toString() {
		return super.toString();
	}

}