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
        return (ui.readInt()) == 1 ? new LeisureSwimmer(ui) : new CompetitiveSwimmer(ui);
    }



    /*
    * This method takes in createMember method and adds the member to the arraylist in the Database class
    * It also takes in chooseCoach method to add both Member and Coach as Key/Value pair in the hashMap inside Database
     */
    public void addMember(UI ui, Member newMember, Database database) {
        database.getMemberList().add(newMember);

        if (newMember instanceof CompetitiveSwimmer) {
            ui.print("Please enter how many swimming disciplines " + newMember.getName() + " is practising: ");
            int disciplineAmount = ui.readInt();
            for (int i = 0; i < disciplineAmount; i++) {
                ((CompetitiveSwimmer) newMember).getSwimmingDisciplineList().add(new SwimmingDiscipline(ui));
            }
            database.getSwimmersCoachAssociationList().
                    put(newMember, chooseCoach(ui, database));

            ui.printLn(newMember.getName() + " er blevet tilføjet som medlem med " + disciplineAmount +
                    " aktive svømme discipliner");
        }
    }



    /*
     * This method iterates through the Coach list, then based on input returns coach if name matches input
     * The method is used to choose a coach for the instantiation of a competition swimmer, so that both
     * - individuals can be put inside the hashmap as a Key/Value pair containing this association.
     */
    public Coach chooseCoach(UI ui, Database swimmerCoachDatabase) {
        for (Coach coach : swimmerCoachDatabase.getCoachList()) {
            ui.printLn("Træner: " + coach.getName());
        } // End of for loop

        ui.print("Hvilken Træner skal medlemmet have: ");
        while (true) {
            String coachName = ui.readLine();
            for (Coach coach : swimmerCoachDatabase.getCoachList()) {
                if (coach.getName().equalsIgnoreCase(coachName)) {
                    return coach;
                }
            } // End of for loop
            ui.printLn("Træner eksisterer ikke, prøv venligst igen");
        } // End of while loop
    } // End of method



    /*
    * This method prints every member name
     */
    public void printMembers(Database swimmerCoachDatabase) {
        for (Member member : swimmerCoachDatabase.getMemberList()) {
            System.out.println(member.getName());
        } // End of for loop
    } // End of Method

}