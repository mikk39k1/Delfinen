package actors;

import utility.Controller;
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
	public void checkTrainingResults() {

	}

	public void checkCompetitionResults() {

	}

	public void addSwimResult(CompetitiveSwimmer competitiveSwimmer, UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {
		System.out.println(swimmerCoachDatabase.getSwimmersCoachAssociationList());

		ui.printLn("Please enter name of swimmer you wish to add result to: ");
		String swimmerName = ui.readLine();
		for (int i = 0; i < swimmerCoachDatabase.getSwimmersCoachAssociationList().size(); i++) {
			if (swimmerName.equalsIgnoreCase(swimmerCoachDatabase.getSwimmersCoachAssociationList()
					.get().getName()));
		}


		competitiveSwimmer.getSwimmingDisciplineList().get(0).getSwimmingDisciplineResults().add(new SwimmingResult(ui));
	}

	// Interface ------------------- unique username/password loader
}