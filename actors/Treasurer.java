package actors;

public class Treasurer extends Employee {


	public Treasurer(RoleType role, PrivilegeType privilege) {
		setRole(role);
		setPrivilege(privilege);
		setPhoneNumber("+45 01 23 58 13");
		setUsername("Acc001");
		setPassword("OnePlusTwoIs3");

		// Need method to add Coach to database on FILE so database can load array with active Coaches
		loadCredentials();

	}

	private void checkMemberArrears() {

	}

	/**
	 * Changes the arrears status of a member from debt to paid
	 */

	private void setMemberArrears() {
		// TODO - implement Treasure.setMemberArrears
		throw new UnsupportedOperationException();
	}

}