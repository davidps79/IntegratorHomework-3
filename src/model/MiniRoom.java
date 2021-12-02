package model;

/**
 * Represents a mini room with a rack of the data center.
 */
public class MiniRoom{
	/**
	 * Unique number which identificates each mini room.
	 */
	private String id;

	/**
	 * Indicates whether it is adjacent to a window.
	 */
	private boolean nextToWindow;

	/**
	 * Price of renting it.
	 */
	private double rentalPrice;

	/**
	 * Rack with servers. Null if it is not rented.
	 */
	private Rack rack;

	/**
	 * Company which rented it. Null if it is not rented.
	 */
	private Company company;

	/**
	 * Rental date. Null if it is not rented.
	 */
	private Date rentalDate;

	/**
	 * Rented or available.
	 */
	private State state;

	/**
	 * On or off.
	 */
	private Power power;

	/**
	 * Array with corridor and column location.
	 */
	private int[] location;

	/**
	 * Constructor of the class. All mini rooms are available and turned off by default. Therefore, 
	 * company, rentalDate and rack are no required (null).
	 * @param id String.
	 * @param nextToWindow boolean.
	 * @param rentalPrice double.
	 */
	public MiniRoom(String id, boolean nextToWindow, double rentalPrice, int corridor, int column){
		this.id = id;
		this.nextToWindow = nextToWindow;
		this.rentalPrice = rentalPrice;
		this.state = State.AVAILABLE;
		this.power = Power.OFF;
		this.rack = null;
		this.company = null;
		this.rentalDate = null;
		this.location = new int[2];
		this.location[0] = corridor;
		this.location[1] = column;
	}

	/**
	 * ToString method. It includes the corridor and column position, the
	 * indicator of window and the price.
	 */
	public String toString(){
		String s = "";
		s += "Corridor: " + location[0];
		s += "\nColumn: " + location[1];
		s += "\nIs next to window: " + (nextToWindow?"Yes":"No");
		s += "\nRental price: " + rentalPrice;
		return s;
	}

	/**
	 * Modifies the values because it became rented. Sets a company and a rentalDate.
	 * Sets state to rented and the power to On. Initializes rack with the entered
	 * servers.
	 * @param company Company.
	 * @param rentalDate Date.
	 * @param preServers Server[].
	 */
	public void rent(Company company, Date rentalDate, Server[] preServers){
		this.company = company;
		this.rentalDate = rentalDate;
		state = State.RENTED;
		power = Power.ON;
		rack = new Rack(preServers);
	}

	/**
	 * Modifies the values because it became available again. Sets company, rack
	 * and rentalDate to null. Sets state to available and the power to Off.
	 */
	public void cancelRental(){
		state = State.AVAILABLE;
		power = Power.OFF;
		this.rack = null;
		this.company = null;
		this.rentalDate = null;
		this.rack = null;
	}

	/**
	 * Returns a String with the detailed processing capacity of the
	 * entire mini room. It takes into account all the servers of its
	 * rack.
	 * @return s String with total ram memory and disk capacity.
	 */
	public String getProcessingCapacity(){
		String s = "";
		s += "Processing capacity of mini room:";
		s += "\nTotal RAM memory: " + rack.getTotalRamMemory();
		s += "\nTotal disk capacity: " + rack.getTotalDiskCapacity();
		return s;
	}

	// Getters and Setters

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isNextToWindow() {
		return this.nextToWindow;
	}

	public void setNextToWindow(boolean nextToWindow) {
		this.nextToWindow = nextToWindow;
	}

	public double getRentalPrice() {
		return this.rentalPrice;
	}

	public void setRentalPrice(double rentalPrice) {
		this.rentalPrice = rentalPrice;
	}

	public Rack getRack() {
		return this.rack;
	}

	public void setRack(Rack rack) {
		this.rack = rack;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getRentalDate() {
		return this.rentalDate;
	}

	public void setRentalDate(Date rentalDate) {
		this.rentalDate = rentalDate;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Power getPower() {
		return this.power;
	}

	public void setPower(Power power) {
		this.power = power;
	}

	public int[] getLocation() {
		return this.location;
	}

	public void setLocation(int[] location) {
		this.location = location;
	}
}