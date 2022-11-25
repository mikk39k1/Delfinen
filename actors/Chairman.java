package actors;

public class Chairman extends Employee {


	// Constructor ---------------------------------------
	public Chairman(RoleType role, PrivilegeType privilege) {
		setName("Administrator");
		setRole(role);
		setPrivilege(privilege);
		setUsername("rootAdmin");
		setPassword("Enigma123");

		// Need method to add Coach to database on FILE so database can load array with active Coaches
		loadCredentials();

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


	// Interface ------------------- unique username/password loader
	@Override
	public void loadCredentials() {

	}
}