package actors;

import database.Database;
import utility.UI;

public class Chairman extends Employee {

    // Constructor ---------------------------------------
    public Chairman(RoleType role, PrivilegeType privilege) {
        setName("Administrator");
        setRole(role);
        setPrivilege(privilege);
        setUsername("rootAdmin");
    }


    // Chairman Behaviors (Methods) ---------------------------------

    /*
    * This method creates a member, and returns the member as a value
     */
    public Member createMember(UI ui) {
        ui.print("Tast venligst 1 - for motionist eller 2 - for Konkurrence Svømmer: ");
        return (ui.readInt()) == 1 ? new LeisureSwimmer(ui) : new CompetitiveSwimmer(ui); // Adds member based on int input
    } // End of method



    /*
    * This method takes in createMember method and adds the member to the arraylist in the Database class
    * It also takes in chooseCoach method to add both Member and Coach as Key/Value pair in the hashMap inside Database
     */
    public void addMember(UI ui, Member newMember, Database database) {
        database.getMemberList().add(newMember);            // Adds new member to Database memberList

        if (newMember instanceof CompetitiveSwimmer) {
            ui.print("Please enter how many swimming disciplines " + newMember.getName() + " is practising: ");
            int disciplineAmount = ui.readInt();
            for (int i = 0; i < disciplineAmount; i++) {
                ((CompetitiveSwimmer) newMember).getSwimmingDisciplineList().add(new SwimmingDiscipline(ui)); // Adds Swimming Discipline
            } // End of for loop
            database.getSwimmersCoachAssociationList().
                    put(newMember, chooseCoach(ui, database));  // Adds new member and coach to Database HashMap

            ui.printLn(newMember.getName() + " er blevet tilføjet som medlem med " + disciplineAmount +
                    " aktive svømme discipliner");
        } // End of if statement
    } // End of method


    /*
    * This method finds and deletes a member from the Database memberList
     */
    public void deleteMember(UI ui, Database memberList) {
        boolean memberNameExist = false;
        ui.print("Please enter name of member: ");
        String memberName = ui.readLine();
        for (Member member : memberList.getMemberList()) {
            if (member.getName().equals(memberName)) {
                System.out.printf("%-20d %-10s %-12s %-20s ",
                        member.getUniqueID(), member.getName(), member.getAge(), member.isIsMembershipActive());
                memberNameExist = true;
                ui.printLn("");
            } // End of first if statement
        } // End of first for loop
        if (memberNameExist) {
            ui.print("\nPlease enter ID of the member to remove: ");
            int memberID = ui.readInt();
            memberList.getMemberList().removeIf(member -> member.getUniqueID() == memberID); // Removes member if ID matches a member entity
            ui.printLn("\nMember deleted");
        } else {
            ui.printLn("No member found with that name");
        } // End of if / else statement
    } // End of method


    /*
     * This method iterates through the Coach list, then based on input returns coach if name matches input
     * The method is used to choose a coach for the instantiation of a competition swimmer, so that both
     * - individuals can be put inside the hashmap as a Key/Value pair containing this association.
     */
    public Coach chooseCoach(UI ui, Database swimmerCoachDatabase) {
        for (Coach coach : swimmerCoachDatabase.getCoachList()) {
            ui.printLn("Træner: " + coach.getName());       // Prints all available Coaches from Database coachList
        } // End of for loop

        ui.print("Hvilken Træner skal medlemmet have: ");
        while (true) {
            String coachName = ui.readLine();
            for (Coach coach : swimmerCoachDatabase.getCoachList()) {
                if (coach.getName().equalsIgnoreCase(coachName)) {
                    return coach;
                } // End of if statement
            } // End of for loop
            ui.printLn("Træner eksisterer ikke, prøv venligst igen");
        } // End of while loop
    } // End of method



    /*
    * This method prints every member name
     */
    public void printMembers(UI ui, Database swimmerCoachDatabase) {
        for (Member member : swimmerCoachDatabase.getMemberList()) {
            ui.printLn(member.getName());               // Prints all names of every member in Database memberList
        } // End of for loop
    } // End of Method

}