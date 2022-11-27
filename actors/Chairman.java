package actors;

import database.SwimmerCoachDatabase;
import utility.UI;

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
	public Member createMember(UI ui) {
		ui.print("Tast venligst 1 - for motionist eller 2 - for Konkurrence Svømmer: ");
		return (ui.readInt()) > 1 ? new LeisureSwimmer(ui) : new CompetitiveSwimmer(ui);

	}


	public void addMember(UI ui, Member newMember, SwimmerCoachDatabase swimmerCoachDatabase) {
		swimmerCoachDatabase.getMemberList().swimmers.add(newMember);
		swimmerCoachDatabase.getSwimmersCoachAssociationList().put(newMember,chooseCoach(ui, swimmerCoachDatabase));
	}

	// This method iterates through the Coach list after
	public Coach chooseCoach(UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {
		ui.printLn(swimmerCoachDatabase.getCoachList().getCoaches().toString());
		while (true) {
			String coachName = ui.readLine();
			for (Coach coach : swimmerCoachDatabase.getCoachList().getCoaches()) {
				if (coach.getName().equalsIgnoreCase(coachName)) {
					return coach;
				}
			}
			ui.printLn("Træner eksisterer ikke, prøv venligst igen");
		}
	}

}