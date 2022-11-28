package actors;

import database.SwimmerCoachDatabase;
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

	public Coach(String name, String phoneNumber, String username) {
		setName(name);
		setRole(RoleType.COACHING);
		setPrivilege(PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT);
		setPhoneNumber(phoneNumber);
		setUsername(username);

		// Need method to add Coach to database on FILE so database can load array with active Coaches
	}

	public Coach() {

	}


	// Behaviors (Methods) --------------------------
	public void checkTrainingResults() {

	}

	public void checkCompetitionResults() {

	}


	public CompetitiveSwimmer lookupSwimmer(UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {

		//Finds member name
		String swimmerName = findSwimmerByName(ui, swimmerCoachDatabase);

		System.out.println("Please enter ID on the member: ");
		int swimmerID = ui.readInt();
		for (Member member : swimmerCoachDatabase.getMemberList().swimmers) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName) && member.getUniqueID() == swimmerID) {
					return (CompetitiveSwimmer) member;
				}
			}
		}
		return null;

	}

	public CompetitiveSwimmer loadSwimmer(UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {

		//Finds member name
		String swimmerName = findSwimmerByName(ui, swimmerCoachDatabase);

		System.out.println("Please enter ID on the member you wish to add result to:");
		int swimmerID = ui.readInt();
		for (Member member : swimmerCoachDatabase.getMemberList().swimmers) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName) && member.getUniqueID() == swimmerID) {
					System.out.printf("ID: %-5d Name: %-10s Phone Number: %-10s Age: %-15s State: %-5b Discipline: ",
							member.getUniqueID(), member.getName(), member.getPhoneNumber(), member.getAge(),
							member.isIsMembershipActive());
							((CompetitiveSwimmer) member).printSwimDisciplineList();
					return (CompetitiveSwimmer) member;
				}
			}
		}
		return null;
	}



	public void addSwimResult(UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {

		CompetitiveSwimmer swimmer = loadSwimmer(ui, swimmerCoachDatabase);


		for (int i = 0; i < swimmerCoachDatabase.getSwimmersCoachAssociationList().size(); i++) {
			if (swimmerCoachDatabase.getSwimmersCoachAssociationList().containsKey(swimmer)) {
				SwimmingDiscipline.SwimmingDisciplineTypes swimmingDiscipline = ui.setSwimmingDisciplineType();
				//second loop is iterate through swimmers list of discipline and get the right one to get needed
				//swimresult list
				for (int j = 0; j < swimmer.getSwimmingDisciplineList().size(); j++) {
					if (swimmer.getSwimmingDisciplineList().get(j).getSwimmingDiscipline().
							equals(swimmingDiscipline)) {
						swimmer.getSwimmingDisciplineList().get(j).getSwimmingDisciplineResults()
								.add(new SwimmingResult(ui));
						i = swimmerCoachDatabase.getSwimmersCoachAssociationList().size();//just to get out of both loops
					} else if (j == swimmer.getSwimmingDisciplineList().size() - 1){
						ui.printLn("Svømmeren er ikke konkurrerende i denne disciplin");
						i = swimmerCoachDatabase.getSwimmersCoachAssociationList().size();//just to get out of both loops
					}
				}
			}
		}
	}

	public void checkCompetitorSwimResults(CompetitiveSwimmer competitiveSwimmer) {

		for (int i = 0; i < competitiveSwimmer.getSwimmingDisciplineList().size(); i++) {
			System.out.printf("ID: %-5d Name: %-10s Phone Number: %-10s Age: %-15s State: %-5b Discipline: %-10s\n",
					competitiveSwimmer.getUniqueID(),competitiveSwimmer.getName(),
					competitiveSwimmer.getPhoneNumber(), competitiveSwimmer.getAge(), competitiveSwimmer.isIsMembershipActive(),
					competitiveSwimmer.getSwimmingDisciplineList().get(i).getSwimmingDiscipline());
			System.out.println(competitiveSwimmer.getSwimmingDisciplineList().get(i).getSwimmingDisciplineResults());
		}
	}

	public String findSwimmerByName(UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {

		ui.printLn("Please enter name of swimmer you wish lookup: ");
		String swimmerName = ui.readLine();

		for (Member member : swimmerCoachDatabase.getMemberList().swimmers) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName)) {
					ui.printLn("ID: " + member.getUniqueID() +  " Name: " + member.getName());
				}
			}
		}
		return swimmerName;
	}
}