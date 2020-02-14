package vehicles.main;

public class Vehicle {

	// Fields of a Vehicle object
	protected int Id; // Primary Key
	protected int Year;
	protected String Make;
	protected String Model;

	// Default Constructor
	public Vehicle() {

	}

	// Unique Key Constructor
	public Vehicle(int Id) {
		this.Id = Id;
	}
	
	// Update fields constructor
	public Vehicle(int Year, String Make, String Model) {
		this.Year = Year;
		this.Make = Make;
		this.Model = Model;
	}

	// Constructor that initializes all fields
	public Vehicle(int Id, int Year, String Make, String Model) {
		this.Id = Id;
		this.Year = Year;
		this.Make = Make;
		this.Model = Model;
	}

	// getters of all fields
	public int getID() {
		return this.Id;
	}

	public int getYear() {
		return this.Year;
	}

	public String getMake() {
		return this.Make;
	}

	public String getModel() {
		return this.Model;
	}

	
	
	// setters of all fields
	public void setID(int Id) {
		this.Id = Id;
	}

	public void setYear(int Year) {
		this.Year = Year;
	}

	public void setMake(String Make) {
		this.Make = Make;
	}

	public void setModel(String Model) {
		this.Model = Model;
	}

}
