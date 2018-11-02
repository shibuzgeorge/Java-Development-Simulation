package Model;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Performs tests on the {@link ChargingPod} class
 * 
 * @author Rajan Malhi
 * @version 1.0
 */

public class ChargingPodTest {

	private Location location1;
	private Location location2;
	private ChargingPod pod1;
	private ChargingPod pod2;
	private Robot robot1;
	private Robot robot2;

	@Before
	public void setUp() throws Exception {

		location1 = new Location(1, 1); // New location at 1,1
		location2 = new Location(3, 4); // New location at 3,4
		robot1 = new Robot(1, location1, 20, 3); // Create a robot with 20 units of power
		robot2 = new Robot(2, location2, 50, 2); // Create a robot with 50 units of power
		pod1 = new ChargingPod(1, location1, robot1); // New StorageShelf: ID: 1, Location (1,1)
		pod2 = new ChargingPod(2, location2, robot2); // New StorageShelf: ID: 2, Location (3,4)

	}

	@Test
	public void testToString() {

		assertEquals("ID: C1 Location: x = 1, y = 1", pod1.toString()); // Check if ID 1 is at location (1,1)
		assertEquals("ID: C2 Location: x = 3, y = 4", pod2.toString()); // Check if ID 2 is at location (3,4)
	}

}