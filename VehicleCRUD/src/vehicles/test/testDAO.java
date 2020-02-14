package vehicles.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.*;

import vehicles.main.*;

import org.junit.jupiter.api.Test;

/**
 * PLEASE READ THIS BEFORE RUNNING THIS TEST.
 * 
 * This JUnit Test File is to unit testing the proper behavior of the DAO Layer.
 * to test the functionality, I will also need to test its connectivity with
 * actual production database, this might cause the lost of production data.
 * PLEASE TEST BEFORE INSERETING ACTUAL DATA.
 * 
 * @author xiaochen
 *
 */
class testDAO {

	// Please put your user name / password below
	String url = "jdbc:mysql://localhost:3306/VehicleCRUD";
	String userName = "root";
	String pswd = "JacobL9!";

	/**
	 * Testing functionality of getting single vehicle.
	 * 
	 * @throws SQLException
	 */
	@Test
	void testGetVehicles() throws SQLException {
		VehicleDAO dao = new VehicleDAO(url, userName, pswd);
		Vehicle vehicle = new Vehicle(100, 2014, "foo", "bar");

		if (dao.getVehicle(100) != null)
			dao.deleteVehicle(vehicle);

		dao.insertVehicle(vehicle);
		Vehicle result = dao.getVehicle(100);

		assertEquals(result.getID(), vehicle.getID());
		assertEquals(result.getYear(), vehicle.getYear());
		assertEquals(result.getMake(), vehicle.getMake());
		assertEquals(result.getModel(), vehicle.getModel());

		dao.deleteVehicle(vehicle);

	}

	/**
	 * 
	 * Testing functionality of getAllVehicles
	 * 
	 * @throws SQLException
	 */
	@Test
	void testGetAllVehicles() throws SQLException {
		VehicleDAO dao = new VehicleDAO(url, userName, pswd);
		List<Vehicle> list = new ArrayList<>();

		Vehicle vehicle = new Vehicle(100, 2014, "foo", "bar");

		if (dao.getVehicle(100) != null)
			dao.deleteVehicle(vehicle);

		list = dao.getAllVehicles();
		int initSize = list.size();

		dao.insertVehicle(vehicle);

		list = dao.getAllVehicles();
		int afterSize = list.size();

		assertEquals(initSize + 1, afterSize);

		dao.deleteVehicle(vehicle);

	}

	@Test
	void testUpdateVehicle() throws SQLException {

		VehicleDAO dao = new VehicleDAO(url, userName, pswd);
		Vehicle vehicle = new Vehicle(100, 2014, "foo", "bar");

		if (dao.getVehicle(100) != null)
			dao.deleteVehicle(vehicle);

		dao.insertVehicle(vehicle);

		Vehicle updated = new Vehicle(100, 2015, "foobar", "UCSD");

		dao.updateVehicle(updated);

		Vehicle result = dao.getVehicle(100);

		assertEquals(result.getID(), updated.getID());
		assertEquals(result.getYear(), updated.getYear());
		assertEquals(result.getMake(), updated.getMake());
		assertEquals(result.getModel(), updated.getModel());

		dao.deleteVehicle(vehicle);

	}

}
