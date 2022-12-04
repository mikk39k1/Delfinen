package actors;

import database.Database;
import utility.FileHandler;
import utility.UI;
import java.util.List;

/*
* This class represents both the admin and chairman class. Being the highest valuable entity of all the operators within
*  our system, the chairman is the only class able to create/delete swimmers of type (Leisure or Competitive)
* In our design the chairman can also create/delete coaches, with their own login/password, which is being stored on a file
 */
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
        ui.print("Type 1 - for exercising or 2 - for competing swimmer: ");
        return (ui.readInt()) == 1 ? new LeisureSwimmer(ui) : new CompetitiveSwimmer(ui); // Adds member based on int input
    } // End of method



    /*
    * This method creates a new Coach employee to file and coachList
    * and sets the username and Password in Password file
    */
    public void createCoach(Database coachList, UI ui, FileHandler filehandler) {
        ui.print("Please enter name of Coach: ");
        String coachName = ui.readLine();
        ui.print("Please enter a phone number: ");
        String phonenumber = ui.readLine();
        ui.print("Please enter a username: ");
        String username = ui.readLine();
        ui.print("Please enter a password: ");
        String password = ui.readLine();

        coachList.getCoachList().add(new Coach(coachName, phonenumber,username,password));
        filehandler.writeCoachUserAndPassToList(username,password);
    } // End of method


    /*
    * This method removes a coach from the file and coachList.
     */
    public void deleteCoach(Database database, UI ui, FileHandler filehandler){
        ui.printLn("Write the name of the coach you would like to remove:");
        String findCoach = ui.readLine();
        ui.printLn("Write the username for the coach:");
        String coachUsername = ui.readLine();
        for (int i = 0; i < database.getCoachList().size(); i++){
            if (database.getCoachList().get(i).getName().equalsIgnoreCase(findCoach)){
                for (Member member: database.getSwimmersCoachAssociationList().keySet()){
                    if (database.getSwimmersCoachAssociationList().get(member).loadCoachOfMember(database,member).equals(findCoach)){

                        ui.printLn("\n Choose a NEW coach for the following members:");
                        System.out.println(member.getUniqueID() + member.getName() + "\n ");
                        database.getCoachList().remove(i);
                        database.getSwimmersCoachAssociationList().put(member,chooseCoach(ui,database, false));
                    }
                }

                filehandler.deleteCoachLoginFromFile(coachUsername);


                ui.printLn("You have removed " + findCoach + " from the coach list.");
            } // End of if statement
        } // End of for loop


    } //End of method

    /*
    *
    *
    *
    *
    *
    * */


    /*
    * This method takes in createMember method and adds the member to the arraylist in the Database class
    * It also takes in chooseCoach method to add both Member and Coach as Key/Value pair in the hashMap inside Database
     */
    public void addMember(UI ui, Member newMember, Database database, FileHandler fileHandler) {
        database.getMemberList().add(newMember);            // Adds new member to Database memberList

        if (newMember instanceof CompetitiveSwimmer) {
            ui.print("Please enter how many swimming disciplines " + newMember.getName() + " is practising: ");
            int disciplineAmount = ui.readInt();           // Stores temporary the amount of Discipline Types swimmer should have
            ui.printLn("Enter Swimming discipline: Crawl, Butterfly, Breaststroke, Backcrawl or Freestyle: ");
            for (int i = 0; i < disciplineAmount; i++) {
                ((CompetitiveSwimmer) newMember).getSwimmingDisciplineList().add(new SwimmingDiscipline(ui)); // Adds Swimming Discipline
            } // End of for loop
            database.getSwimmersCoachAssociationList().
                    put(newMember, chooseCoach(ui, database, true));  // Adds new member and coach to Database HashMap

            ui.printLn(newMember.getName() + " has been added as a member with " + disciplineAmount +
                    " swimming " + (disciplineAmount>1?"disciplines":"discipline"));
            fileHandler.writeToSwimmerCoachAssociationFile(database);
        } // End of if statement
    } // End of method



    /*
    * This method finds and deletes a member from the Database memberList
     */
    public void deleteMember(UI ui, Database memberList) {
        boolean memberNameExist = false;    // Attribute will help determine for further continuation of this method
        ui.print("Please enter name of member: ");
        String memberName = ui.readLine();  // Stores a name value of a member, intended to remove as a String
        for (Member member : memberList.getMemberList()) {
            if (member.getName().equals(memberName)) {
                System.out.printf("%-20d %-10s %-12s %-20s ",   // Prints members in case of doublets in names
                        member.getUniqueID(), member.getName(), member.getDateOfBirth(), member.isIsMembershipActive());
                memberNameExist = true;   // Attribute will now be argument for continuation of this method
                ui.printLn("");
            } // End of first if statement
        } // End of first for loop
        if (memberNameExist) {
            ui.print("\nPlease enter ID of the member to remove: ");
            int memberID = ui.readInt();    // Stores the ID value of member, intended to remove
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
    public Coach chooseCoach(UI ui, Database swimmerCoachDatabase, Boolean isMemberAddToCoach) {
        for (Coach coach : swimmerCoachDatabase.getCoachList()) {
            ui.printLn("Coach: " + coach.getName());       // Prints all available Coaches from Database coachList
        } // End of for loop

        while (true) {
            ui.print("Please enter the name of a coach: ");
            String coachName = ui.readLine(); // Stores temporary the name of the Coach intended to be used

            for (Coach coach : swimmerCoachDatabase.getCoachList()) {
                if (coach.getName().equalsIgnoreCase(coachName)) {
                    return coach;       // If temporary name exists within employed Coaches, returns that actual coach
                } // End of if statement
            } // End of for loop
            ui.printLn("Coach doesn't exist. Try again");
        } // End of while loop
    } // End of method



    /*
    * This method prints every member name
     */
    public void printMembers(List<Member> members) {
        for (Member member : members) {
            System.out.printf("ID: %-8d Name: %-30s Date of Birth: %-15s Tel: %-15s State: %-5b%n",
                    member.getUniqueID(),
                    member.getName(),
                    member.getDateOfBirth(),
                    member.getPhoneNumber(),
                    member.isIsMembershipActive());       // Prints all ID, names, DoB, membership status  of every member in Database memberList
        } // End of for loop
    } // End of Method

}