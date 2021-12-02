package model;

/**
 * It is a Company with predefined nit and name. In addition, it has an extra attribute
 * to store the id of an associated research project.
 */
public class Icesi extends Company{
	/**
	 * Id of a research project of the university.
	 */
	private String projectId;

	/**
	 * Constructor of the class. The nit and name are fixed, but projectId varies.
	 * @param projectId String.
	 */
	public Icesi(String projectId){
		super("890.316.745-5", "Icesi University");
		this.projectId = projectId;
	}

	// Getters and Setters

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
}