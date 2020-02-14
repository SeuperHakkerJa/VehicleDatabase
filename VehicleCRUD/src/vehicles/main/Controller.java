package vehicles.main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Controller Level
 * 
 * Handling request from users, calling methods in DAO Layer, and dispatch
 * result back to page.
 * 
 * @author Xiaochen Li (Jacob)
 *
 */
public class Controller extends HttpServlet {

	// default setting
	private static final long serialVersionUID = 1L;
	private VehicleDAO vehicleDAO;

	/**
	 * Initialize DAO object, all parameters of setting are editable at web.xml
	 * under WebContent/WEB-INF/ , where new users can change user name, password
	 * for SQL local database.
	 */
	public void init() {

		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

		vehicleDAO = new VehicleDAO(jdbcURL, jdbcUsername, jdbcPassword);

	}

	/**
	 * Called by server to allow servlet to handle post request
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * Called by server to allow servlet to handle get request
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertVehicle(request, response);
				break;
			case "/delete":
				deleteVehicle(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				updateVehicle(request, response);
				break;
			case "/find":
				showFilterForm(request, response);
				break;
			case "/filter":
				filterVehicle(request, response);
				break;
			default:

				listVehicle(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * Default presenting page, listing all vehicles from database to the List site.
	 * 
	 * @throws SQLException,IOException,ServletException
	 */
	private void listVehicle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		// call DAO method to access all vehicles in DB
		List<Vehicle> listVehicle = vehicleDAO.getAllVehicles();
		request.setAttribute("listVehicle", listVehicle);

		// dispatch results to VehicleList
		RequestDispatcher dispatcher = request.getRequestDispatcher("VehicleList.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("VehicleForm.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		int Id = Integer.parseInt(request.getParameter("id"));
		Vehicle existingVehicle = vehicleDAO.getVehicle(Id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("VehicleForm.jsp");
		request.setAttribute("vehicle", existingVehicle);
		dispatcher.forward(request, response);

	}

	/**
	 * Receiving request from the new form, use DAO to add new entity in local DB
	 * 
	 * @throws SQLException,IOException
	 */
	private void insertVehicle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int Id = Integer.parseInt(request.getParameter("Id"));
		int Year = Integer.parseInt(request.getParameter("Year"));
		String Make = request.getParameter("Make");
		String Model = request.getParameter("Model");

		// check criteria
		if (Year > 2050 || Year < 1950) {
			throw new SQLException("Sorry, " + Year + " is not in the proper range of year [1950, 2050]");
		}

		if (Make == "") {
			throw new SQLException("Sorry, Make can not be empty");
		}

		if (Model == "") {
			throw new SQLException("Sorry, Model can not be empty");
		}

		Vehicle newVehicle = new Vehicle(Id, Year, Make, Model);
		vehicleDAO.insertVehicle(newVehicle);
		response.sendRedirect("list");
	}

	/**
	 * Retrieving the car through unique ID from Database, and send all its
	 * information to the form for users to make changes.
	 * 
	 * @throws IOException,SQLException
	 */
	private void updateVehicle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		int Id = Integer.parseInt(request.getParameter("Id2"));

		int Year = Integer.parseInt(request.getParameter("Year"));
		String Make = request.getParameter("Make");
		String Model = request.getParameter("Model");

		// check criteria
		if (Year > 2050 || Year < 1950) {
			throw new SQLException("Sorry, " + Year + " is not in the proper range of year [1950, 2050]");
		}

		if (Make == "") {
			throw new SQLException("Sorry, Make can not be empty");
		}

		if (Model == "") {
			throw new SQLException("Sorry, Model can not be empty");
		}

		Vehicle vehicle = new Vehicle(Id, Year, Make, Model);

		vehicleDAO.updateVehicle(vehicle);

		response.sendRedirect("list");

	}

	/**
	 * Retrieve info from primary key. Delete the entry in local DB and then refresh
	 * the page.
	 * 
	 * @throws SQLException, IOException
	 */
	private void deleteVehicle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));

		Vehicle vehicle = new Vehicle(id);
		vehicleDAO.deleteVehicle(vehicle);
		response.sendRedirect("list");

	}
	
	
	private void showFilterForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("filterForm.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void filterVehicle(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		
		int Id = 0;
		String str_id = request.getParameter("Id");
		if (str_id != null) Id = Integer.parseInt(str_id);
		
		int Year = 0;
		String str_year = request.getParameter("Year");
		if (str_year != null )Year = Integer.parseInt(str_year);
		
		String Make = "";
		String temp_make = request.getParameter("Make");
		if (temp_make != null ) Make = temp_make;
		
		String Model = "";
		String temp_model = request.getParameter("Model");
		if (temp_model != null) Model = temp_model;
		
		Vehicle v = new Vehicle(Id, Year, Make, Model);

		List<Vehicle> filterVehicle = vehicleDAO.filter(v);
		request.setAttribute("filteredVehicle", filterVehicle);
		
		

		// dispatch results to VehicleList
		RequestDispatcher dispatcher = request.getRequestDispatcher("filterList.jsp");
		dispatcher.forward(request, response);
	}
	
	
	
}
