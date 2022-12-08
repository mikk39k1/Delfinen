package actors;

/*
* This class represent all what an employee must have. Our design is made up with "The Least Privilege" concept.
* Whenever an employee is instantiated, a privilege and role is given, thus ensuring we better can encapsulate each members
* access to the system.
 */
public abstract class Employee extends Person {

	// Attributes ---------------------------------
	public enum RoleType {
		ADMIN,
		ACCOUNTANT,
		COACHING
	}
	public enum PrivilegeType {
		ADMINISTRATOR,
		ECONOMY_MANAGEMENT,
		COMPETITIVE_SWIMMER_MANAGEMENT}

	private RoleType role;
	private PrivilegeType privilege;
	private String username;
	private String password;


	// Getters ------------------------------------

	public PrivilegeType getPrivilege() {
		return privilege;
	}

	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return password;
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
}