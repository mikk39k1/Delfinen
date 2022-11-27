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
	}


	// Behaviors (Methods) ---------------------------------
	public Member createMember(UI ui) {
		ui.print("Tast venligst 1 - for motionist eller 2 - for Konkurrence Svømmer: ");
		return (ui.readInt()) > 1 ? new LeisureSwimmer(ui) : new CompetitiveSwimmer(ui);
	}


	public void addMember(UI ui, Member newMember, SwimmerCoachDatabase swimmerCoachDatabase) {
		// ISSUE AT THE MOMENT ---------------- WE NEED TO MAKE SURE ONLY COMPETITIVE MEMBERS ARE ADDED TO SWIMMERCOACHASSOCIATION LIST.
		swimmerCoachDatabase.getMemberList().swimmers.add(newMember);
		swimmerCoachDatabase.getSwimmersCoachAssociationList().put(newMember,chooseCoach(ui, swimmerCoachDatabase));
		ui.printLn(newMember.getName() + " er blevet tilføjet som medlem");
	}

	// This method iterates through the Coach list after
	public Coach chooseCoach(UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {
		for (Coach coach : swimmerCoachDatabase.getCoachList().getCoaches()) {
			ui.printLn("Træner: " + coach.getName());
		}

		ui.print("Hvilken Træner skal medlemmet have: ");
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

	public void printMembers(SwimmerCoachDatabase swimmerCoachDatabase) {
		for (Member member : swimmerCoachDatabase.getMemberList().swimmers) {
			System.out.println(member.getName());
		}
	}

}