package vehicles.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import vehicles.main.*;


class testVehicle {

	@Test
	void testVehicleAccess() {
		Vehicle v = new Vehicle(0011, 2012, "Toyota", "Random");

		assertEquals(v.getID(), 0011);
		assertEquals(v.getYear(), 2012);
		assertEquals(v.getMake(), "Toyota");
		assertEquals(v.getModel(), "Random");

		v.setID(1100);
		v.setYear(2020);
		v.setMake("Other");
		v.setModel("foo");

		assertEquals(v.getID(), 1100);
		assertEquals(v.getYear(), 2020);
		assertEquals(v.getMake(), "Other");
		assertEquals(v.getModel(), "foo");

	}

}
