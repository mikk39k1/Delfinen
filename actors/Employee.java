package actors;

public abstract class Employee extends Person {

	// Attributes ---------------------------------
	public enum RoleType {
		ADMIN,
		ACCOUNTANT,
		COACHING}
	public enum PrivilegeType {
		ADMINISTRATOR,
		ECONOMY_MANAGEMENT,
		COMPETITIVE_SWIMMER_MANAGEMENT};

	private RoleType role;
	private PrivilegeType privilege;
	private String username;


	// Getters ------------------------------------
	public RoleType getRole() {
		return role;
	}

	public PrivilegeType getPrivilege() {
		return privilege;
	}

	public String getUsername() {
		return this.username;
	}


	// Setters -------------------------------------
	public void setRole(RoleType role) {
		this.role = role;
	}
	public void setPrivilege(PrivilegeType privilege) {
		this.privilege = privilege;
	}
	public void setUsername(String username) {
		this.username = username;
	}


	// Behaviors (Methods) -------------------------
	/**
	 * Loads the username and password from etc/passwd file
	 */
}