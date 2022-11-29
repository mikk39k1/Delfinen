package utility;

import actors.*;
import database.Database;

public class MenuRun {
    UI ui = new UI();
    FileHandler fileHandler = new FileHandler();
    private final String[] menuOptions;
    private final String menuHeader;
    private final String leadtext;

    // Constructor  -----------------------------------------
    public MenuRun(String menuHeader, String leadtext, String[] menuOptions, Employee employee, Database swimmerCoachDatabase) {
        this.menuHeader = menuHeader;
        this.menuOptions = menuOptions;
        this.leadtext = leadtext;
        menuLooping(employee, swimmerCoachDatabase);
    }


    // MenuRun Behaviors (Methods) ----------------------------

    public void menuLooping(Employee employee, Database swimmerCoachDatabase) {
        boolean isSignedIn = true;
        while (isSignedIn) {
            printMenu();
            int userChoice = ui.readInt();
            switch (userChoice) {
                case 1 -> {addMember(employee, swimmerCoachDatabase); /*add new member to all members lists */}
                case 2 -> {printAllMembers(employee, swimmerCoachDatabase);/*print all members */}
                case 3 -> {printMembersInDebt(employee, swimmerCoachDatabase); /*Prints list of members who hasn't paid */}
                case 4 -> {changePayDue(employee, swimmerCoachDatabase);}
                case 5 -> {addSwimResult(employee, swimmerCoachDatabase);/*add swimResult*/}
                case 6 -> {printCompetitiveSwimmersResult(employee, swimmerCoachDatabase); /*Prints 1 swimmers results*/}
                case 7 -> {printTopFiveByDiscipline(employee, swimmerCoachDatabase);/*Prints top 5 in 1 discipline*/}
                case 8 -> {printSwimmersByCoach(employee, swimmerCoachDatabase);/*Prints all members for specific coach*/}
                case 9 -> {isSignedIn = logOut(); /*Logs you out of the system */}
                default -> ui.printLn("Vælg en eksisterende mulighed.\n");
            } // End of switch statement
        } // End of while loop
    } // End of method


    /*
     * Prints all attributes of the menu
     */
    private void printMenu() {
        System.out.println(menuHeader);
        System.out.println(leadtext);
        for (String menuOption : menuOptions) {
            System.out.println(menuOption);
        } // End of for loop
    } // End of method


    /*
     * This method adds a member through the Chairman class
     * Only Employee Privilege leve of ADMINISTRATOR can use this method (Chairman class)
     */
    private void addMember(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).addMember(ui, ((Chairman) employee).createMember(ui),
                    swimmerCoachDatabase);
            fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList());
        } else {
            ui.printLn("Du har ikke login rettigheder til denne funktion");
        } // End of if / else statement
    } // End of method


    /*
     * This method prints all members from the database through the Chairman class
     * Only Employee Privilege leve of ADMINISTRATOR can use this method (Chairman class)
     */
    private void printAllMembers(Employee employee, Database memberList) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).printMembers(memberList);
        } else {
            ui.printLn("Du har ikke login rettigheder til denne funktion");
        } // End of if / else statement
    } // End of method


    /*
     * This method prints all members with arrears through the Treasurer class
     * Only Employee Privilege leve of ADMINISTRATOR and ECONOMYMANAGEMENT can use this method (Chairman/Treasurer class)
     */
    private void printMembersInDebt(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMYMANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                      // Creates temporary user for admin
                adminOverride.checkMemberArrears(swimmerCoachDatabase);         // Runs temporary user intended method
            } else {
                ((Treasurer) employee).checkMemberArrears(swimmerCoachDatabase);    // Runs method as Treasurer
            } // End of inner if / else statement
        } else {
            ui.printLn("Du har ikke login rettigheder til denne funktion");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method allows for changes within member status, regarding paid status through the Treasurer class
     * Only Employee Privilege leve of ADMINISTRATOR and ECONOMYMANAGEMENT can use this method (Chairman/Treasurer class)
     */
    private void changePayDue(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMYMANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                  // Creates temporary user for admin
                adminOverride.setMemberArrears(swimmerCoachDatabase, ui); // Runs temporary user intended method
                fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList()); // Writes changes to file
            } else {
                ((Treasurer) employee).checkMemberArrears(swimmerCoachDatabase);    // Runs method as Treasurer
            } // End of inner if / else statement
            fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList()); // Writes changes to file
        } else {
            ui.printLn("Du har ikke login rettigheder til denne funktion");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method adds swimming result to a member through the Coach class
     * Only Employee Privilege leve of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void addSwimResult(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Coach adminOverride = new Coach();                         // Creates temporary user for admin
                adminOverride.addSwimResult(ui, swimmerCoachDatabase); // Runs temporary user method
                fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList());   // Writes to file

            } else {
                ((Coach) employee).addSwimResult(ui, swimmerCoachDatabase); // Runs method as Coach
                fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList()); // Writes to file
            } // End of inner if / else statement

        } else {
            ui.printLn("Du har ikke login rettigheder til denne funktion");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method prints out a specific Swimmers results
     * Only Employee Privilege leve of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void printCompetitiveSwimmersResult(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            //Method reads input from user: swimDiscipline and period of time to get results
            if (employee instanceof Chairman) {
                Coach adminOverride = new Coach();                      // Creates temporary user for admin
                adminOverride.checkCompetitorSwimResults(
                        adminOverride.lookupSwimmer(ui, swimmerCoachDatabase)); //Runs temporary user method
            } else {
                ((Coach) employee).checkCompetitorSwimResults(
                        ((Coach) employee).lookupSwimmer(ui, swimmerCoachDatabase)); // runs method as Coach
            } // End of inner if / else statement
        } else {
            ui.printLn("Du har ikke login rettigheder til denne funktion");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method prints and sorts the top 5 swimmer performances based on inputs from user.
     * The method reads input from user: swimDiscipline and period of time to get results
     * Only Employee Privilege leve of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void printTopFiveByDiscipline(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Coach adminOverride = new Coach();              // Creates a temporary user for admin
                adminOverride.checkCompetitorSwimResults(
                        adminOverride.lookupSwimmer(ui, swimmerCoachDatabase)); //Runs temporary user method
            } else {
                ((Coach) employee).checkCompetitorSwimResults(
                        ((Coach) employee).lookupSwimmer(ui, swimmerCoachDatabase)); // Runs method as Coach
            } // End of inner if / else statement
        } else {
            ui.printLn("Du har ikke login rettigheder til denne funktion");
        } // End of outer if / else statement
    } //End of method



    /*
    * This method prints all the members associated for a specific coach
    * Only Employee Privilege leve of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void printSwimmersByCoach(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                // This method makes admin take role of an existing coach, to print his members out
                Coach adminOverride = ((Chairman) employee).chooseCoach(ui, swimmerCoachDatabase);
                adminOverride.findMembersOfCoach(swimmerCoachDatabase, adminOverride); // Runs method as temporary user
            } else {
                ((Coach) employee).findMembersOfCoach(swimmerCoachDatabase, ((Coach) employee)); // Runs method as coach
            } // End of inner if / else statement
        } else {
            ui.printLn("Du har ikke login rettigheder til denne funktion");
        } // End of outer if / else statement
    } // End of method


    /*
    * This method will log out the user and terminate the program
     */
    private boolean logOut() {
        ui.printLn("Logger ud");
        System.exit(0);
        return false;

    }
}
