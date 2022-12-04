package actors;

import database.Database;
import utility.FileHandler;
import utility.SuperSorterThreeThousand;
import utility.UI;

import java.util.Map;

/*
* This class represent a coach. The coach is supposed to be able to pick out competition swimmers based on their performances
* In our design the coach can also add swimming results for both for competitive as training sessions.
* This class is also subject to the principle of "The Least privilege", securing that an encapsulating principle won't allow
*  the coach to meddle with methods and changes not within a coach's responsibility
 */
public class Coach extends Employee {

	// Constructors ---------------------------------

	// Constructor to create a Coach with Password.
	public Coach(String name, String phonenumber, String username, String password) {
		setName(name);
		setRole(RoleType.COACHING);
		setPrivilege(PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT);
		setPhoneNumber(phonenumber);
		setUsername(username);
		setPassword(password);
	}

	public Coach() {

	}

	// Coach Behaviors (Methods) --------------------------
	public void checkTrainingResults() {

	}

	public void checkCompetitionResults() {

	}


	/*
	* This method looks up a swimmer from Database memberList
	 */
	public CompetitiveSwimmer lookupSwimmer(UI ui, Database swimmerCoachDatabase) {

		//Finds member name
		String swimmerName = findSwimmerByName(ui, swimmerCoachDatabase); // Stores temporary swimmer name in a String

		System.out.print("Please enter ID on the member: ");
		int swimmerID = ui.readInt();
		for (Member member : swimmerCoachDatabase.getMemberList()) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName) && member.getUniqueID() == swimmerID) {
					System.out.printf("%nID: %-8d Name: %-30s Date of Birth: %-15s Tel: %-15s State: %-5b Discipline: ",
							member.getUniqueID(), member.getName(), member.getDateOfBirth(), member.getPhoneNumber(),
							member.isIsMembershipActive());
					((CompetitiveSwimmer) member).printSwimDisciplineList();
					return (CompetitiveSwimmer) member;
				} // End of inner if statement
			} // End of outer if statement
		} // End of for loop
		return null;
	} // End of method


	/*
	* This method
	 */
	public CompetitiveSwimmer loadSwimmer(UI ui, Database swimmerCoachDatabase) {
		System.out.println("Please enter ID on the member you wish to add result to: ");
		int swimmerID = ui.readInt();
		for (Member member : swimmerCoachDatabase.getMemberList()) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName) && member.getUniqueID() == swimmerID) {
					System.out.printf("%nID: %-8d Name: %-30s Date of Birth: %-15s Tel: %-15s State: %-5b Discipline: ",
							member.getUniqueID(), member.getName(), member.getDateOfBirth(), member.getPhoneNumber(),
							member.isIsMembershipActive());
					return (CompetitiveSwimmer) member;
				} // End of inner if statement
			} // End of outer if statement
		} // End of for loop
		return null;
	} // End of method


	/*
	* This method adds swimming results of a competitive swimmer, to the corresponding searched swimming discipline type
	 */
	public void addSwimResult(Employee employee, UI ui, Database swimmerCoachDatabase, FileHandler filehandler) {

		CompetitiveSwimmer swimmer = loadSwimmer(ui, swimmerCoachDatabase);

		for (Map.Entry<Member, Coach> set : swimmerCoachDatabase.getSwimmersCoachAssociationList().entrySet()) {
			if (set.getKey().equals(swimmer)) {
				ui.print(" | ");
				for (SwimmingDiscipline swimType : ((CompetitiveSwimmer)set.getKey()).getSwimmingDisciplineList()) {
					ui.print(swimType + " | ");
				}
				ui.printLn("");
				SwimmingDiscipline.SwimmingDisciplineTypes swimmingDiscipline = ui.setSwimmingDisciplineType();
				int hasSwimDiscipline = hasSwimmingDiscipline(swimmer, swimmingDiscipline);
				if (hasSwimDiscipline > -1) {
					swimmer.getSwimmingDisciplineList().get(hasSwimDiscipline).getSwimmingDisciplineResults()
							.add(new SwimmingResult(ui));
					filehandler.appendResult(employee,swimmerCoachDatabase,swimmer,hasSwimDiscipline);
					System.out.println("The swim result was added! ");
				} else {
					ui.printLn("The swimmer does not participate in this kind of competition");
				} // End of inner if / else statement
			} // End of if statement
		} // End of for loop
	} // End of method



	/*
	* This method prints a specific competitive swimmers results for each swimming discipline
	 */
	public void checkCompetitorSwimResults(CompetitiveSwimmer competitiveSwimmer) {

		for (int i = 0; i < competitiveSwimmer.getSwimmingDisciplineList().size(); i++) {
			System.out.printf("%nID: %-8d Name: %-30s Date of Birth: %-15s Tel: %-15s State: %-5b Discipline: %-10s%n",
					competitiveSwimmer.getUniqueID(),competitiveSwimmer.getName(),
					competitiveSwimmer.getDateOfBirth(),competitiveSwimmer.getPhoneNumber(), competitiveSwimmer.isIsMembershipActive(),
					competitiveSwimmer.getSwimmingDisciplineList().get(i).getSwimmingDiscipline());
			for (SwimmingResult result : competitiveSwimmer.getSwimmingDisciplineList().get(i).getSwimmingDisciplineResults()) {
				System.out.print(competitiveSwimmer.getSwimmingDisciplineList().get(i).getSwimmingDiscipline()+": ");
				result.printResults();
			} // End of inner for loop
		} // End of outer for loop
	} // End of method


	public void checkTopFiveCompetitionSwimResults(Database database, SwimmingDiscipline.SwimmingDisciplineTypes swimType, UI ui) {
		SuperSorterThreeThousand sorterThreeThousand = new SuperSorterThreeThousand();
		sorterThreeThousand.setSortByDistance(ui,sorterThreeThousand.swimmingResultList(database,swimType));
	}



	/*
	* This method verifies name of a competitive swimmer, by checking if it exists within the Database memberList
	 */
	public String findSwimmerByName(UI ui, Database swimmerCoachDatabase) {

		ui.print("Please enter name of swimmer you wish lookup: ");
		String swimmerName = ui.readLine();		// Stores temporary swimmerName we are searching for

		for (Member member : swimmerCoachDatabase.getMemberList()) {
			if (member instanceof CompetitiveSwimmer) {
				if (member.getName().equalsIgnoreCase(swimmerName)) {
					ui.printLn("ID: " + member.getUniqueID() +  " Name: " + member.getName());
				} // End of inner if statement
			} // End of outer if statement
		} // End of for loop
		return swimmerName;
	} // End of method




	/*
	* Checks if a competitor has the swimming discipline, which we are requesting, returns -1 if false
	 */
	private int hasSwimmingDiscipline(CompetitiveSwimmer swimmer, SwimmingDiscipline.SwimmingDisciplineTypes swimmingDiscipline) {

		for (int i = 0; i < swimmer.getSwimmingDisciplineList().size(); i++) {
			if (swimmer.getSwimmingDisciplineList().get(i).getSwimmingDiscipline().
					equals(swimmingDiscipline)) {
				return i;
			} // End of if statement
		} // End of for loop
		return -1;
	} // End of method



	/*
	* This method finds and prints all members belonging to the coach logged in.
	 */
	public void findMembersOfCoach(Database swimmerCoachDatabase, Coach coach) {
		System.out.println("Coach " + this.getName() + ", has the following swimmers:");
		final int x = 0;
		for (Member key : swimmerCoachDatabase.getSwimmersCoachAssociationList().keySet()) {
			if (swimmerCoachDatabase.getSwimmersCoachAssociationList().get(key).equals(coach)) {
				System.out.println(key.getUniqueID() + ": " + key.getName());
			} // End of if statement
		} // End of for loop
	} // End of method

	public int getMemberAmountForCoach(Database swimmerCoachDatabase, Coach coach) {
        int memberAmount = 0;
		for (Member key : swimmerCoachDatabase.getSwimmersCoachAssociationList().keySet()) {
			if (swimmerCoachDatabase.getSwimmersCoachAssociationList().get(key).equals(coach)) {
                memberAmount++;
			} // End of if statement
		} // End of for loop
    return memberAmount;
	} // End of method



	/*
	* This method finds and prints the coach of a specific member
	 */
	public void findCoachOfMember(Database swimmerCoachDatabase, Member member) {
		for (Coach values : swimmerCoachDatabase.getSwimmersCoachAssociationList().values()) {
			if (swimmerCoachDatabase.getSwimmersCoachAssociationList().get(member).equals(values)) {
				System.out.println(values.getName());
			} // End of if statement
		} // End of for loop
	} // End of method



	public String loadCoachOfMember(Database swimmerCoachDatabase, Member member) {
		for (Coach values : swimmerCoachDatabase.getSwimmersCoachAssociationList().values()) {
			if (swimmerCoachDatabase.getSwimmersCoachAssociationList().get(member).equals(values)) {
				return values.getName();
			} // End of if statement
		} // End of for loop
		return "NoCoach";
	}

} // End of class