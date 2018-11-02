package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Performs unit tests on the {@link StorageShelf} class.
 *
 * @author Shibu George
 * @version 1.0
 */

public class StorageShelfTest {

	private Location location1;
	private Location location2;
	private StorageShelf shelf1;
	private StorageShelf shelf2;

	@Before
	public void setUp() throws Exception {

		location1 = new Location(1, 1); // New location at 1,1
		location2 = new Location(3, 4); // New location at 3,4
		shelf1 = new StorageShelf(1, location1); // New StorageShelf: ID: 1, Location (1,1)
		shelf2 = new StorageShelf(2, location2); // New StorageShelf: ID: 2, Location (3,4)
	}

	@Test
	public void testToString() {
		assertEquals("ID: ss1 Location: x = 1, y = 1", shelf1.toString()); // Check if ID 1 is at location (1,1)
		assertEquals("ID: ss2 Location: x = 3, y = 4", shelf2.toString()); // Check if ID 2 is at location (3,4)
	}

	@Test
	public void testVisited() {
		assertFalse(shelf1.isVisited());
	}

}