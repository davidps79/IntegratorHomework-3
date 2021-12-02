package model;

/**
 * Represents a computer used as a server.
 * It can only exist inside a Rack object. 
 */
public class Server{
	/**
	 * Cache memory.
	 */
	private double cacheMemory;

	/**
	 * Processor number.
	 */
	private int processorNumber;

	/**
	 * RAM memory.
	 */
	private int ramMemory;

	/**
	 * Disk number.
	 */	
	private int diskNumber;

	/**
	 * Disk capacity.
	 */
	private double diskCapacity;

	/**
	 * Constructor of the class.
	 * @param cacheMemory double.
	 * @param processorNumber int.
	 * @param ramMemory int.
	 * @param diskNumber int.
	 * @param diskCapacity double.
	 */
	public Server(double cacheMemory, int processorNumber, int ramMemory, int diskNumber, double diskCapacity){
		this.cacheMemory = cacheMemory;
		this.processorNumber = processorNumber;
		this.ramMemory = ramMemory;
		this.diskNumber = diskNumber;
		this.diskCapacity = diskCapacity;
	}

	// Getters and Setters
	
	public double getCacheMemory() {
		return this.cacheMemory;
	}

	public void setCacheMemory(double cacheMemory) {
		this.cacheMemory = cacheMemory;
	}

	public int getProcessorNumber() {
		return this.processorNumber;
	}

	public void setProcessorNumber(int processorNumber) {
		this.processorNumber = processorNumber;
	}

	public int getRamMemory() {
		return this.ramMemory;
	}

	public void setRamMemory(int ramMemory) {
		this.ramMemory = ramMemory;
	}

	public int getDiskNumber() {
		return this.diskNumber;
	}

	public void setDiskNumber(int diskNumber) {
		this.diskNumber = diskNumber;
	}

	public double getDiskCapacity() {
		return this.diskCapacity;
	}

	public void setDiskCapacity(double diskCapacity) {
		this.diskCapacity = diskCapacity;
	}
}