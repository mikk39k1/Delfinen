package actors;

public class Chairman extends Employee {


	// Constructor ---------------------------------------
	public Chairman(RoleType role, PrivilegeType privilege) {
		setName("Administrator");
		setRole(role);
		setPrivilege(privilege);
		setUsername("rootAdmin");

		// Need method to add Coach to database on FILE so database can load array with active Coaches
	}


	// Behaviors (Methods) ---------------------------------
	private Member createMember() {
		// TODO - implement Chairman.createMember
		throw new UnsupportedOperationException();
	}


	public void addMember(Member newMember) {
		// TODO - implement Chairman.addMember
		throw new UnsupportedOperationException();
	}

}