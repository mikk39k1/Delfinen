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

		// Need method to add Coach to database on FILE so database can load array with active Coaches
	}

	public Coach(String name,String phoneNumber, String username) {
		setName(name);
		setRole(RoleType.COACHING);
		setPrivilege(PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT);
		setPhoneNumber(phoneNumber);
		setUsername(username);

		// Need method to add Coach to database on FILE so database can load array with active Coaches
	}


	// Behaviors (Methods) --------------------------
	private void checkTrainingResults() {

	}

	private void checkCompetitionResults() {

	}

	private void addSwimResult() {

	}

	// Interface ------------------- unique username/password loader
}