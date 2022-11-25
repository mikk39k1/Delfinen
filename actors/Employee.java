package actors;

public abstract class Employee extends Person implements CredentialDataHandler {

	// Attributes ---------------------------------
	public enum RoleType {
		ADMIN,
		ACCOUNTANT,
		COACHING}
	public enum PrivilegeType {
		ADMINISTRATOR,
		ECONOMYMANAGEMENT,
		COMPETITIVE_SWIMMER_MANAGEMENT};

	private RoleType role;
	private PrivilegeType privilege;
	private String username;
	private String password;


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

	public String getPassword() {
		return this.password;
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
	public void setPassword(String password) {
		this.password = password;
	}


	// Behaviors (Methods) -------------------------
	/**
	 * Loads the username and password from etc/passwd file
	 */

}