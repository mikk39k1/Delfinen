package actors;

import database.SingletonDatabase;
import utility.SingletonFileHandler;
import utility.SingletonUI;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

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
    public Member createMember(SingletonUI singleTonUi) {
        singleTonUi.print("Type 1 - for exercising or 2 - for competing swimmer: ");
        return (singleTonUi.readInt()) == 1 ? new LeisureSwimmer(singleTonUi) : new CompetitiveSwimmer(singleTonUi); // Adds member based on int input
    } // End of method




    // This CODE IS FOR GENERATING MULTIPLE USERS FOR TESTING PURPOSES
    /*
    public void create100CompetitiveMembers() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            SwimmingDiscipline.SwimmingDisciplineTypes[] arr =
                    {SwimmingDiscipline.SwimmingDisciplineTypes.BACKCRAWL,
                            SwimmingDiscipline.SwimmingDisciplineTypes.BREASTSTROKE,
                            SwimmingDiscipline.SwimmingDisciplineTypes.BUTTERFLY,
                            SwimmingDiscipline.SwimmingDisciplineTypes.CRAWL,
                            SwimmingDiscipline.SwimmingDisciplineTypes.FREESTYLE
                    };

            String[] arrNames = {
                    "Bob",
                    "James",
                    "Marry",
                    "Henrik",
                    "Mike",
                    "Brim",
                    "Zoro",
                    "Jenifer",
                    "Liam",
                    "Pippy",
                    "Oliver",
                    "Patrick",
                    "Gordon",
                    "Jason",
                    "Addison",
                    "Allison",
                    "Jenny"};

            boolean[] arrBool = {true, false};

            String randomNames = arrNames[random.nextInt(arrNames.length)];
            int randomYear = random.nextInt(122) + 1900;
            int randomMonth = random.nextInt(12) + 1;
            int randomDay = random.nextInt(28) + 1;
            SwimmingDiscipline.SwimmingDisciplineTypes randomDiscipline = arr[random.nextInt(5)];
            int randomPhoneNumber = random.nextInt(99999999) + 11111111;
            boolean randomBool = arrBool[random.nextInt(arrBool.length)];


            CompetitiveSwimmer competitiveSwimmer = new CompetitiveSwimmer(
                    randomNames,
                    String.valueOf(randomPhoneNumber),
                    LocalDate.of(randomYear, randomMonth, randomDay),
                    randomBool,
                    randomBool);

            Coach randomCoach = SingletonDatabase.getSingletonDatabase().getCoachList().get(random.nextInt(
                    SingletonDatabase.getSingletonDatabase().getCoachList().size()));

            if(randomCoach.getMemberAmountForCoach(SingletonDatabase.getSingletonDatabase(), randomCoach) < 20) {

                competitiveSwimmer.getSwimmingDisciplineList().add(new SwimmingDiscipline(String.valueOf(randomDiscipline)));
                SingletonDatabase.getSingletonDatabase().getMemberList().add((competitiveSwimmer));
                SingletonDatabase.getSingletonDatabase().getSwimmersCoachAssociationList().put(competitiveSwimmer, randomCoach);
            }
        }
    }


     */



    /*
    * This method creates a new Coach employee to file and coachList
    * and sets the username and Password in Password file
    */

    public void createCoach(SingletonDatabase coachList, SingletonUI singleTonUi) {
        singleTonUi.print("Please enter name of Coach: ");
        String coachName = singleTonUi.readLine();
        singleTonUi.print("Please enter a phone number: ");
        String phonenumber = singleTonUi.readLine();
        singleTonUi.print("Please enter a username: ");
        String username = singleTonUi.readLine();
        singleTonUi.print("Please enter a password: ");
        String password = singleTonUi.readLine();

        coachList.getCoachList().add(new Coach(coachName, phonenumber,username,password));
    } // End of method


    /*
    * This method removes a coach from the file and coachList.
     */
    public void deleteCoach(String findCoach, SingletonDatabase singleTonDatabase, SingletonUI singleTonUi) {

        singleTonDatabase.getCoachList().removeIf(coach -> coach.getName().equalsIgnoreCase(findCoach));
        singleTonDatabase.getSwimmersCoachAssociationList().forEach((key, value) -> {
            if (value.loadCoachOfMember(singleTonDatabase.getSwimmersCoachAssociationList(),key).equalsIgnoreCase(findCoach)) {

                singleTonUi.printLn("\n Choose a NEW coach for the following members:");
                singleTonUi.printLn("ID: " + key.getUniqueID() + " " + key.getName() + "\n ");
                singleTonDatabase.getSwimmersCoachAssociationList().put(key, chooseCoach(singleTonUi, singleTonDatabase, false));
            }
        });

        singleTonUi.printLn("You have removed " + findCoach + " from the coach list.");
        // End of if statement
        // End of for loop
    }



        /*
    * This method takes in createMember method and adds the member to the arraylist in the Database class
    * It also takes in chooseCoach method to add both Member and Coach as Key/Value pair in the hashMap inside Database
     */
    public void addMember(SingletonUI singleTonUi, Member newMember, SingletonDatabase singleTonDatabase) {
        if (newMember instanceof CompetitiveSwimmer && !singleTonDatabase.getCoachList().isEmpty()) {
            singleTonUi.print("Please enter how many swimming disciplines " + newMember.getName() + " is practising: ");
            int disciplineAmount = singleTonUi.readInt();           // Stores temporary the amount of Discipline Types swimmer should have
            singleTonUi.printLn("Enter Swimming discipline: Crawl, Butterfly, Breaststroke, Backcrawl or Freestyle: ");
            for (int i = 0; i < disciplineAmount; i++) {
                ((CompetitiveSwimmer) newMember).getSwimmingDisciplineList().add(new SwimmingDiscipline(singleTonUi)); // Adds Swimming Discipline
            } // End of for loop
            singleTonDatabase.getSwimmersCoachAssociationList().
                    put(newMember, chooseCoach(singleTonUi, singleTonDatabase, true));  // Adds new member and coach to Database HashMap

            singleTonUi.printLn(newMember.getName() + " has been added as a member with " + disciplineAmount +
                    " swimming " + (disciplineAmount>1?"disciplines":"discipline"));

        } // End of if statement
        if ((newMember instanceof CompetitiveSwimmer && !singleTonDatabase.getCoachList().isEmpty() || newMember instanceof LeisureSwimmer)) {
            singleTonDatabase.getMemberList().add(newMember); // Adds new member to Database memberList
        }

    } // End of method



    /*
    * This method finds and deletes a member from the Database memberList
     */
    public void deleteMember(SingletonUI singleTonUi, SingletonDatabase memberList) {
        boolean memberNameExist = false;    // Attribute will help determine for further continuation of this method
        singleTonUi.print("Please enter name of member: ");
        String memberName = singleTonUi.readLine();  // Stores a name value of a member, intended to remove as a String
        for (Member member : memberList.getMemberList()) {
            if (member.getName().equals(memberName)) {
                System.out.printf("%-20d %-10s %-12s %-20s ",   // Prints members in case of doublets in names
                        member.getUniqueID(), member.getName(), member.getDateOfBirth(), member.isIsMembershipActive());
                memberNameExist = true;   // Attribute will now be argument for continuation of this method
                singleTonUi.printLn("");
            } // End of first if statement
        } // End of first for loop
        if (memberNameExist) {
            singleTonUi.print("\nPlease enter ID of the member to remove: ");
            int memberID = singleTonUi.readInt();    // Stores the ID value of member, intended to remove
            memberList.getMemberList().removeIf(member -> member.getUniqueID() == memberID); // Removes member if ID matches a member entity
            singleTonUi.printLn("\nMember deleted");
        } else {
            singleTonUi.printLn("No member found with that name");
        } // End of if / else statement
    } // End of method


    /*
     * This method iterates through the Coach list, then based on input returns coach if name matches input
     * The method is used to choose a coach for the instantiation of a competition swimmer, so that both
     * - individuals can be put inside the hashmap as a Key/Value pair containing this association.
     */
    public Coach chooseCoach(SingletonUI singleTonUi, SingletonDatabase coachList, Boolean isMemberAddToCoach) {
        for (Coach coach : coachList.getCoachList()) {
            singleTonUi.printLn("Coach: " + coach.getName());       // Prints all available Coaches from Database coachList
        } // End of for loop

        while (true) {
            singleTonUi.print("\nPlease enter the name of a coach: ");
            String coachName = singleTonUi.readLine(); // Stores temporary the name of the Coach intended to be used
            for (Coach coach : coachList.getCoachList()) {
                if (coach.getName().equalsIgnoreCase(coachName)) {
                    if (isMemberAddToCoach && coach.getMemberAmountForCoach(coachList, coach) < 20) {
                        return coach;
                    } else if (isMemberAddToCoach && coach.getMemberAmountForCoach(coachList, coach) > 20) {
                        singleTonUi.printLn("The coach's team is full, pick another coach.");
                    } else if (!isMemberAddToCoach) {
                        return coach;
                    }
                }// End of if statement
            } // End of for loop
            if (coachList.getCoachList().stream().noneMatch(coach ->
                    coach.getName().equalsIgnoreCase(coachName))) {
                singleTonUi.printLn("Coach does not exist try again");
            } // End of if statement
        } // End of while loop
    } // End of method



    /*
    * This method prints every member name
     */
    public void printMembers(List<Member> members) {
        for (Member member : members) {
            System.out.printf("ID: %-8d Name: %-30s Date of Birth: %-15s Tel: %-15s Membership Status: %-5b%n",
                    member.getUniqueID(),
                    member.getName(),
                    member.getDateOfBirth(),
                    member.getPhoneNumber(),
                    member.isIsMembershipActive());       // Prints all ID, names, DoB, membership status  of every member in Database memberList
        } // End of for loop
    } // End of Method

}