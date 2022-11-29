package actors;

import database.Database;
import utility.UI;

public class Coach extends Employee {


	// Constructors ---------------------------------
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


	// Coach Behaviors (Methods) --------------------------
	public void checkTrainingResults() {

	}

	public void checkCompetitionResults() {

	}


	public CompetitiveSwimmer lookupSwimmer(UI ui, Database swimmerCoachDatabase) {

		//Finds member name
		String swimmerName = findSwimmerByName(ui, swimmerCoachDatabase);

		System.out.println("Please enter ID on the member: ");
		int swimmerID = ui.readInt();
		for (Member member : swimmerCoachDatabase.getMemberList()) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName) && member.getUniqueID() == swimmerID) {
					return (CompetitiveSwimmer) member;
				}
			}
		}
		return null;
	}

	public CompetitiveSwimmer loadSwimmer(UI ui, Database swimmerCoachDatabase) {

		//Finds member name
		String swimmerName = findSwimmerByName(ui, swimmerCoachDatabase);

		System.out.println("Please enter ID on the member you wish to add result to:");
		int swimmerID = ui.readInt();
		for (Member member : swimmerCoachDatabase.getMemberList()) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName) && member.getUniqueID() == swimmerID) {
					System.out.printf("|ID: %-5d Name: %-10s Phone Number: %-10s Age: %-15s State: %-5b Discipline: ",
							member.getUniqueID(), member.getName(), member.getPhoneNumber(), member.getAge(),
							member.isIsMembershipActive());
							((CompetitiveSwimmer) member).printSwimDisciplineList();
					return (CompetitiveSwimmer) member;
				}
			}
		}
		return null;
	}

	public void addSwimResult(UI ui, Database swimmerCoachDatabase) {

		CompetitiveSwimmer swimmer = loadSwimmer(ui, swimmerCoachDatabase);

		for (int i = 0; i < swimmerCoachDatabase.getSwimmersCoachAssociationList().size(); i++) {
			if (swimmerCoachDatabase.getSwimmersCoachAssociationList().containsKey(swimmer)) {
				SwimmingDiscipline.SwimmingDisciplineTypes swimmingDiscipline = ui.setSwimmingDisciplineType();

				int hasSwimDiscipline = hasSwimmingDiscipline(swimmer, swimmingDiscipline);

				if (hasSwimDiscipline > -1) {
					swimmer.getSwimmingDisciplineList().get(hasSwimDiscipline).getSwimmingDisciplineResults()
							.add(new SwimmingResult(ui));
				} else {
					ui.printLn("Svømmeren er ikke konkurrerende i denne disciplin");
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

	public String findSwimmerByName(UI ui, Database swimmerCoachDatabase) {

		ui.printLn("Please enter name of swimmer you wish lookup: ");
		String swimmerName = ui.readLine();

		for (Member member : swimmerCoachDatabase.getMemberList()) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName)) {
					ui.printLn("ID: " + member.getUniqueID() +  " Name: " + member.getName());
				}
			}
		}
		return swimmerName;
	}

	// Checks if a competitor has the swimming discipline, which we are requesting, returns -1 i false
	private int hasSwimmingDiscipline(CompetitiveSwimmer swimmer, SwimmingDiscipline.SwimmingDisciplineTypes swimmingDiscipline) {

		for (int i = 0; i < swimmer.getSwimmingDisciplineList().size(); i++) {
			if (swimmer.getSwimmingDisciplineList().get(i).getSwimmingDiscipline().
					equals(swimmingDiscipline)) {
				return i;
			}
		}
		return -1;
	}

	public void findMembersOfCoach(Database swimmerCoachDatabase, Coach coach) {
		System.out.println("Træner: " + this.getName() + "har følgende medlemmere");

		for (Member key : swimmerCoachDatabase.getSwimmersCoachAssociationList().keySet()) {
			if (swimmerCoachDatabase.getSwimmersCoachAssociationList().get(key).equals(coach))
				System.out.println(key.getUniqueID() + ": " + key.getName());
		}
	}

	public void findCoachOfMember(Database swimmerCoachDatabase, Member member) {
		for (Coach values : swimmerCoachDatabase.getSwimmersCoachAssociationList().values()) {
			if (swimmerCoachDatabase.getSwimmersCoachAssociationList().get(member).equals(values))
				System.out.println(values.getName());
		}
	}

}