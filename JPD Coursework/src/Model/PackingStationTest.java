package Model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Performs unit tests on the {@link PackingStation} class.
 * 
 * @author Azhar Zaman
 * @version 1.0
 */

public class PackingStationTest {

	private Location locationOne;
	private Location locationTwo;

	private PackingStation stationOne;
	private PackingStation stationTwo;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		locationOne = new Location(4, 5); // New location at 4,5
		locationTwo = new Location(5, 6); // New location at 5,6

		stationOne = new PackingStation(1, locationOne); // New Station: ID: 1, Location (4,5)
		stationTwo = new PackingStation(2, locationTwo); // New Station: ID: 2, Location (5,6)

	}

	@Test
	public void toStringTest() {

		assertEquals("ID: ps1 Location: x = 4, y = 5", stationOne.toString()); // Check if ID of stationOne is at
																				// location (4,5)
		assertEquals("ID: ps2 Location: x = 5, y = 6", stationTwo.toString()); // Check if ID of stationTwo is at
																				// location (5,6)

	}
}