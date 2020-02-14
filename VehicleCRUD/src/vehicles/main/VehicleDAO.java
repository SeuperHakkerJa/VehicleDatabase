package vehicles.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Accessing Layer, directly accessing/editing data from local DB.
 * 
 * @author xiaochen
 *
 */
public class VehicleDAO {

	// JDBC variables
	private String jdbcURL;
	private String jdbcUsername;
	private String jdbcPassword;
	private Connection jdbcConnection;

	// SQL format
	public final String SQL_SELECT = "SELECT * FROM vehicle";
	public final String SQL_SELECT_WHERE = "SELECT * FROM vehicle WHERE Id = ?";
	public final String SQL_INSERT = "INSERT INTO vehicle (Id, Year, Make, Model) VALUES (?, ?, ?, ?)";
	public final String SQL_UPDATE = "UPDATE vehicle SET Year = ?, Make = ? , Model = ? WHERE Id = ?";
	public final String SQL_DELETE = "DELETE FROM vehicle WHERE Id = ?";

	/**
	 * Constructors for new instance of Vehicle DAO. An object provide rights to
	 * access data from local database (MySQL ).
	 * 
	 * @param jdbcURL
	 * @param jdbcUsername - customize this variable in web.xml
	 * @param jdbcPassword - customize this variable in web.xml
	 */
	public VehicleDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) {
		this.jdbcURL = jdbcURL;
		this.jdbcUsername = jdbcUsername;
		this.jdbcPassword = jdbcPassword;
	}

	/**
	 * Safer connect, which allows one time connect and handles error during
	 * connection
	 * 
	 * @throws SQLException
	 */
	protected void connect() throws SQLException {

		if (jdbcConnection == null || jdbcConnection.isClosed()) {
			try {

				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new SQLException(e);
			}
			jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}
	}

	/**
	 * Safer disconnect, which prevents disconnecting non-exist connection.
	 * 
	 * @throws SQLException
	 */
	protected void disconnect() throws SQLException {

		if (jdbcConnection != null && !jdbcConnection.isClosed()) {
			jdbcConnection.close();
		}
	}

	/* ------------------------------ CRUD --------------------------------- */

	/**
	 * Insert record into database, vehicle passed in act as medium passing needed
	 * information.
	 * 
	 * @throws SQLException
	 */
	public boolean insertVehicle(Vehicle vehicle) throws SQLException {

		String command = SQL_INSERT;

		connect();

		PreparedStatement pstmt = jdbcConnection.prepareStatement(command);
		pstmt.setInt(1, vehicle.getID());
		pstmt.setInt(2, vehicle.getYear());
		pstmt.setString(3, vehicle.getMake());
		pstmt.setString(4, vehicle.getModel());

		boolean inserted = pstmt.executeUpdate() > 0;
		pstmt.close();
		disconnect();

		return inserted;

	}

	/**
	 * Select all tuples from database.
	 * 
	 * @return The list containing all Vehicles.
	 * @throws SQLException
	 */
	public List<Vehicle> getAllVehicles() throws SQLException {

		List<Vehicle> vehicles = new ArrayList<>();
		String command = SQL_SELECT;

		// connection starts
		connect();

		// execute SELECT statement
		Statement stmt = jdbcConnection.createStatement();
		ResultSet rs = stmt.executeQuery(command);

		// iterate to retrieve all values
		while (rs.next()) {
			int Id = rs.getInt("Id");
			int Year = rs.getInt("Year");
			String Make = rs.getString("Make");
			String Model = rs.getString("Model");

			Vehicle vehicle = new Vehicle(Id, Year, Make, Model);
			vehicles.add(vehicle);

		}

		// free memory
		rs.close();
		stmt.close();

		disconnect();

		return vehicles;
	}

	/**
	 * Retrieving single vehicle, used for getting all information when
	 * users want to edit a specific vehicle.
	 * 
	 * @throws SQLException
	 */
	public Vehicle getVehicle(int Id) throws SQLException {

		Vehicle vehicle = null;
		String command = SQL_SELECT_WHERE;

		connect();

		PreparedStatement pstmt = jdbcConnection.prepareStatement(command);
		pstmt.setInt(1, Id);

		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			int Year = rs.getInt("Year");
			String Make = rs.getString("Make");
			String Model = rs.getString("Model");

			vehicle = new Vehicle(Id, Year, Make, Model);

		}

		rs.close();
		pstmt.close();

		// return fetched result
		return vehicle;

	}

	/**
	 * 
	 * Update the content of passed in vehicle, identified via primary key.
	 * 
	 * @throws SQLException
	 */
	public boolean updateVehicle(Vehicle vehicle) throws SQLException {

		String command = SQL_UPDATE;

		connect();

		PreparedStatement pstmt = jdbcConnection.prepareCall(command);
		pstmt.setInt(1, vehicle.getYear());
		pstmt.setString(2, vehicle.getMake());
		pstmt.setString(3, vehicle.getModel());
		pstmt.setInt(4, vehicle.getID());

		boolean updated = pstmt.executeUpdate() > 0;
		pstmt.close();
		disconnect();
		return updated;

	}

	/**
	 * Delete the vehicle passed in, identified by the Id.
	 * @throws SQLException
	 */
	public boolean deleteVehicle(Vehicle vehicle) throws SQLException {

		String command = SQL_DELETE;

		connect();

		PreparedStatement pstmt = jdbcConnection.prepareCall(command);
		pstmt.setInt(1, vehicle.getID());
		boolean deleted = pstmt.executeUpdate() > 0;

		pstmt.close();
		disconnect();

		return deleted;

	}
	
	public List<Vehicle> filter(Vehicle vehicle) throws SQLException {
		
		List<Vehicle> list = new ArrayList<>();
		
		String command = "SELECT * FROM vehicle WHERE"
				+ " (Id = ? OR ? = 0 ) AND (Year = ? OR ? = 0) AND (Make = ? or ? = '')"
				+ " AND (Model = ? OR ? = '') ";
		
		connect();
		PreparedStatement pstmt = jdbcConnection.prepareCall(command);
		pstmt.setInt(1,vehicle.getID());
		pstmt.setInt(2,vehicle.getID());
		
		pstmt.setInt(3, vehicle.getYear());
		pstmt.setInt(4, vehicle.getYear());
		
		pstmt.setString(5, vehicle.getMake());
		pstmt.setString(6, vehicle.getMake());
		
		pstmt.setString(7, vehicle.getModel());
		pstmt.setString(8, vehicle.getModel());
		
		
		ResultSet rs = pstmt.executeQuery();
		
		while (rs.next()) {
			int Id = rs.getInt("Id");
			int Year = rs.getInt("Year");
			String Make = rs.getString("Make");
			String Model = rs.getString("Model");

			Vehicle v = new Vehicle(Id, Year, Make, Model);
			list.add(v);
			
		}
		rs.close();
		pstmt.close();
		disconnect();
		
		return list;
	}	

}
