package model;

/**
 * Represents a company. It can only exists as an attribute of
 * a MiniRoom object.
 */
public class Company{
	/**
	 * nit of the company.
	 */
	private String nit;

	/**
	 * Official name of the company.
	 */
	private String name;

	/**
	 * Constructor of the class.
	 * @param nit String.
	 * @param name String.
	 */
	public Company(String nit, String name){
		this.nit = nit;
		this.name = name;
	}

	// Getters and Setters

	public String getNit() {
		return this.nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}