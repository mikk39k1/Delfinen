package actors;

public class Treasurer extends Employee {


	public Treasurer(RoleType role, PrivilegeType privilege) {
		setRole(role);
		setPrivilege(privilege);
		setPhoneNumber("+45 01 23 58 13");
		setUsername("Acc001");

		// Need method to add Coach to database on FILE so database can load array with active Coaches

	}

	private void checkMemberArrears() {

	}

	/**
	 * Changes the arrears status of a member from debt to paid
	 */

	private void setMemberArrears() {

	}

	// Interface ------------------- unique username/password loader

}