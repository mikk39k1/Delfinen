package utility;

import actors.*;
import database.SingletonDatabase;

import java.util.ArrayList;

/*
* This class is the representing startup of the system. Whenever the program starts, predefined users, members, passwords
*   and results are loaded through whole Database containing Members, associationsLists, Employees etc
 */
public class SystemBoot {

    // Utility / Controller ------------------
    private Employee currentUser;

    private final ArrayList<Employee> enigmaUsers = new ArrayList<>();

    /*
     * Main method booting it all up
     */
    public static void main(String[] args) {
        new SystemBoot().startSystem();
    }

    /*
     * This method starts the whole program doing different important steps as followed:
     * - 1 Loading all member data from fullMemberList file
     * - 2 Loading all coach employees from coachList file
     * - 3 Sets the corresponding static member ID, so Member ID keeps incrementing, even after deleted users
     * - 4 Loading all Member/Coach associations from file to SwimmerCoachAssociationList
     * - 5 Loading all results by members from file to Members Result
     * - 6 Logs action
     * - 7 Prints welcome ASCII-art
     * - 8 Loads the employed users
     * - 9 Invokes login method
     * - 10 Starts the menu selection
     */
    private void startSystem() {
        SingletonDatabase.getSingletonDatabase().setMemberList(SingletonFileHandler.getInstance()
                .readMemberList(SingletonDatabase.getSingletonDatabase().getMemberList()));
        SingletonDatabase.getSingletonDatabase().setCoachList(SingletonFileHandler.getInstance()
                .readCoachList(SingletonDatabase.getSingletonDatabase().getCoachList()));
        Member.setID(SingletonFileHandler.getInstance().readID());
        SingletonDatabase.getSingletonDatabase().setSwimmersCoachAssociationList(SingletonFileHandler.getInstance()
                .readSwimmerCoachAssociationList(SingletonDatabase.getSingletonDatabase()));
        SingletonFileHandler.getInstance().readResults(SingletonDatabase.getSingletonDatabase()
                .getSwimmersCoachAssociationList());

        SingletonFileHandler.getInstance().loggingAction("Program started.");
        SingletonFileHandler.getInstance().printWelcomeSharks();
        loadStaff();
        //loading();
        loginSystem();



        MenuRun startSystem = new MenuRun(">>> ENIGMA SOLUTION <<<", "\u001B[1mChose an option:\u001B[0m", new String[]{
                "1. Add a new member.",
                "2. Delete a member",
                "3. Print list of existing members.",
                "4. Print member(s) in arrear.",
                "5. Change a members payment status",
                "6. Add a new swimming result.",
                "7. Print swimming results",
                "8. Print top 5 athletes in a given discipline.",
                "9. Print all members associated with this user",
                "10. Add a new coach",
                "11. Delete a coach",
                "12. Check this years Club-Economy",
                "0. Log out."
        });      // 6
        startSystem.menuLooping(currentUser, SingletonDatabase.getSingletonDatabase());
    } // End of method


    /*
     * This method follows the principle of "The Least Privilege" and ensure users cant do more than allowed by setting Role and Privilege level
     */
    private void setRoleAndPrivilege(String username, SingletonDatabase database) {
        // Switch statement set role and privilege based on correct username
        enigmaUsers.forEach(
                employee -> {
                    if (employee.getUsername().equals(username)) {
                        currentUser = employee;
                        SingletonFileHandler.getInstance().loggingAction(currentUser.getName() + " logged in.");
                    } else {
                        database.getCoachList().forEach(
                                coach -> {
                                    if (coach.getUsername().equals(username)) {
                                        currentUser = coach;
                                        SingletonFileHandler.getInstance().loggingAction(currentUser.getName() + " logged in.");
                                    } // End of inner if statement
                                } // End of lambda predicate expression
                        ); // End of inner forEach ArrayList build-in method
                    } // End of if / else statement
                } // End of lambda predicate expression
        ); // End of out forEach ArrayList build-in method
    } // End of method



    /*
    * This method is doing a login sequence, if isLoggedIn returns an authentic username, a temporary user with privilege and role is given based on user login
     */
    private void loginSystem() {
        String user;
        do {
            user = isLoggedIn();                // Temporary stores a username if prompted existing username
            if (!user.equals("0")) {
                setRoleAndPrivilege(user, SingletonDatabase.getSingletonDatabase());      // Sets authorization level of role / privileges
            } // End of if statement
        } while (user.equals("0")); // End of do-while loop
    } // end of method




    // loginStuff ----------------------------------
    /*
      This method checks through file and username / password method if inputs are authentic, to allow login
     */
    private String isLoggedIn() {
        String username = SingletonFileHandler.getInstance().readUsername(getUsername());
        if (!username.equals("0")) {
            for (int i = 1; i < 4; i++) {

                if (isPasswordCorrect(getPassword())) {

                    SingletonUI.getInstance().printLn("You're signed in");
                    return username;
                } else if (i != 3) {
                    SingletonUI.getInstance().printLn("you have " + (3 - i) + ((3 - i > 1) ? " tries left\n" : " try left\n"));
                } // End of inner else - if statement
            } // End of for loop
        } // End of outer if statement
        return "0";
    } // End of method


    /*
    * This method requests username, and returns input
     */
    private String getUsername() {
        SingletonUI.getInstance().print("Please enter your username: ");
        return SingletonUI.getInstance().readLine();
    } // End of method


    /*
    * This method requests password, and returns input
     */
    private String getPassword() {
        SingletonUI.getInstance().print("Please enter your password: ");
        return SingletonUI.getInstance().readLine();
    } // End of method

    /*
    * This method checks if password is correct, and returns a boolean based on success / failure
     */
    private boolean isPasswordCorrect(String password) {
        return !SingletonFileHandler.getInstance().readPassword(password).equals("0");

    } // End of method


    /*
     * This method instantiates the program with minimal users. Chairman and Treasurer
     */
    protected void loadStaff() {
        enigmaUsers.add(new Chairman(Employee.RoleType.ADMIN, Employee.PrivilegeType.ADMINISTRATOR));
        enigmaUsers.add(new Treasurer(Employee.RoleType.ACCOUNTANT, Employee.PrivilegeType.ECONOMY_MANAGEMENT));
    } // End of method


    /*
     * This method is illustrating a loading buffer for a more intuitive experience
     */
    private void loading() {
        try {
            SingletonUI.getInstance().printLn("\n                                     Loading ");
            String anim = "|/-\\";
            for (int x = 0; x <= 100; x++) {
                String data = "\r                                     " + anim.charAt(x % anim.length()) + " " + x;
                SingletonUI.getInstance().print(data + " of 100");
                Thread.sleep(70);

            }
            SingletonUI.getInstance().printLn("\nENIGMA SOLUTIONS" + (char)153 + " 2022. All rights reserved " + (char)174 +"\n");
        } catch (Exception e){
            SingletonUI.getInstance().printLn(e.getMessage());
        }
    }
} // End of class
