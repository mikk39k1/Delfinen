package actors;

import utility.UI;

public class Coach extends Employee {

	// Constructor ---------------------------------
	public Coach(UI in) {
		in.print("Please enter name of Coach: ");
		setName(in.readLine());
		setRole(RoleType.COACHING);
		setPrivilege(PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT);
		in.print("Please enter a phone number: ");
		setPhoneNumber(in.readLine());
		in.print("Please enter a username: ");
		setUsername(in.readLine());
		in.print("Please enter a password: ");
		setPassword(in.readLine());

		// Need method to add Coach to database on FILE so database can load array with active Coaches
		loadCredentials();
	}

	public Coach(String name,String phoneNumber, String username, String password) {
		setName(name);
		setRole(RoleType.COACHING);
		setPrivilege(PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT);
		setPhoneNumber(phoneNumber);
		setUsername(username);
		setPassword(password);

		// Need method to add Coach to database on FILE so database can load array with active Coaches
	}


	// Behaviors (Methods) --------------------------
	private void checkTrainingResults() {

	}

	private void checkCompetitionResults() {

	}

	private void addSwimResult() {

	}

}