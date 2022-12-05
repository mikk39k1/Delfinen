package utility;

import actors.*;
import database.Database;

import java.util.ArrayList;

/*
* This class is the representing startup of the system. Whenever the program starts, predefined users, members, passwords
*   and results are loaded through whole Database containing Members, associationsLists, Employees etc
 */
public class SystemBoot {

    // Utility / Controller ------------------
    private final UI ui = new UI();
    private final FileHandler fileHandler = new FileHandler();
    private Employee currentUser;
    private final ArrayList<Employee> enigmaUsers = new ArrayList<>();
    private final Database database = new Database();

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
     * - 4 Loads the employed users
     * - 5 Prints welcome screen
     * - 6 Invokes login method
     * - 7 Starts the menu selection
     */
    private void startSystem() {
        database.setMemberList(fileHandler.loadMemberList(database.getMemberList()));// 1
        database.setCoachList(fileHandler.loadCoachList(database.getCoachList()));// 2
        Member.setID(fileHandler.loadID());// 3
        database.setSwimmersCoachAssociationList(fileHandler.loadSwimmerCoachAssociationList(database));
        fileHandler.loadResultMethod(database.getSwimmersCoachAssociationList());

        System.out.println("\n");
        fileHandler.loggingAction("Program started.");
        fileHandler.printWelcomeSharks();   // 5
        loadStaff();
        //loading();
        loginSystem();                      // 6

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
        startSystem.menuLooping(currentUser, database);
    } // End of method


    /*
    * This method follows the principle of "The Least Privilege" and ensure users cant do more than allowed by setting Role and Privilege level
     */
    private void setRoleAndPrivilege(String username) {
        // Switch statement set role and privilege based on correct username
        for (Employee user : enigmaUsers) {
            if (user.getUsername().equals(username)) {
                currentUser = user;
                fileHandler.loggingAction(currentUser.getName() + " logged in.");
            } else {
                for (Coach coach : database.getCoachList()) {
                    if (coach.getUsername().equals(username)) {
                        currentUser = coach;
                        fileHandler.loggingAction(currentUser.getName() + " logged in.");
                    } // End inner if statement
                } // End inner for loop
            } // End if / else statement
        } // End of for loop
    } // End of method



    /*
    * This method is doing a login sequence, if isLoggedIn returns an authentic username, a temporary user with privilege and role is given based on user login
     */
    private void loginSystem() {
        String user;
        do {
            user = isLoggedIn();                // Temporary stores a username if prompted existing username
            if (!user.equals("0")) {
                setRoleAndPrivilege(user);      // Sets authorization level of role / privileges
            } // End of if statement
        } while (user.equals("0")); // End of do-while loop
    } // end of method




    // loginStuff ----------------------------------
    /*
      This method checks through file and username / password method if inputs are authentic, to allow login
     */
    private String isLoggedIn() {
        String username = fileHandler.checkUsername(getUsername());
        if (!username.equals("0")) {
            for (int i = 1; i < 4; i++) {

                if (isPasswordCorrect(getPassword())) {

                    System.out.println("You're signed in");
                    return username;
                } else if (i != 3) {
                    System.out.println("you have " + (3 - i) + ((3 - i > 1) ? " tries left\n" : " try left\n"));
                } // End of inner else - if statement
            } // End of for loop
        } // End of outer if statement
        return "0";
    } // End of method


    /*
    * This method requests username, and returns input
     */
    private String getUsername() {
        System.out.print("Please enter your username: ");
        return ui.readLine();
    } // End of method


    /*
    * This method requests password, and returns input
     */
    private String getPassword() {
        System.out.print("Please enter your password: ");
        return ui.readLine();
    } // End of method

    /*
    * This method checks if password is correct, and returns a boolean based on success / failure
     */
    private boolean isPasswordCorrect(String password) {
        return !fileHandler.checkPassword(password).equals("0");

    } // End of method

    protected void loadStaff() {
        enigmaUsers.add(new Chairman(Employee.RoleType.ADMIN, Employee.PrivilegeType.ADMINISTRATOR));
        enigmaUsers.add(new Treasurer(Employee.RoleType.ACCOUNTANT, Employee.PrivilegeType.ECONOMY_MANAGEMENT));
    } // End of method



    private void loading() {
        try {
            System.out.println("\n                                     Loading ");
            String anim = "|/-\\";
            for (int x = 0; x <= 100; x++) {
                String data = "\r                                     " + anim.charAt(x % anim.length()) + " " + x;
                System.out.print(data + " of 100");
                Thread.sleep(70);

            }
            System.out.println("\nENIGMA SOLUTIONS" + (char)153 + " 2022. All rights reserved " + (char)174 +"\n");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
} // End of class
