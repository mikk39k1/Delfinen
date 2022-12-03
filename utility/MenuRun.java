package utility;

import actors.*;
import database.Database;

/*
* This class represent the menu and the interaction of user and system. Whenever an input is received for the menu
*  the corresponding reaction is followed by menuOptions titles. Each option is verifying the user privilege, to ensure
*  no user is acting with malice or doing thinks not in his/hers jurisdiction.
 */
public class MenuRun {
    UI ui = new UI();
    FileHandler fileHandler = new FileHandler();
    SuperSorterThreeThousand sorter = new SuperSorterThreeThousand();
    private final String[] menuOptions;
    private final String menuHeader;
    private final String leadtext;

    // Constructor  -----------------------------------------
    public MenuRun(String menuHeader, String leadtext, String[] menuOptions) {
        this.menuHeader = menuHeader;
        this.menuOptions = menuOptions;
        this.leadtext = leadtext;
    }

    // MenuRun Behaviors (Methods) ----------------------------


    /*
    * This method is looping each option a user can interact with within the menu
     */
    public void menuLooping(Employee employee, Database swimmerCoachDatabase) {
        boolean isSignedIn = true;
        while (isSignedIn) {
            ui.printLn("");
            printMenu();
            switch (ui.readInt()) {
                case 1 -> {addMember(employee, swimmerCoachDatabase); /*add new member to all members lists */}
                case 2 -> {deleteMember(employee,swimmerCoachDatabase); /*deletes member from members lists*/}
                case 3 -> {printAllMembers(employee, swimmerCoachDatabase);/*print all members */}
                case 4 -> {printMembersInDebt(employee, swimmerCoachDatabase); /*Prints list of members who hasn't paid */}
                case 5 -> {changePayDue(employee, swimmerCoachDatabase);}
                case 6 -> {addSwimResult(employee, swimmerCoachDatabase);/*add swimResult*/}
                case 7 -> {printCompetitiveSwimmersResult(employee, swimmerCoachDatabase); /*Prints 1 swimmers results*/}
                case 8 -> {printTopFiveByDiscipline(employee, swimmerCoachDatabase);/*Prints top 5 in 1 discipline*/}
                case 9 -> {printSwimmersByCoach(employee, swimmerCoachDatabase);/*Prints all members for specific coach*/}
                case 10 ->{createCoach(employee,swimmerCoachDatabase,ui);}/*Create a coach and add them to coachlist.*/
                case 11 ->{deleteCoach(employee,swimmerCoachDatabase,ui);}//Delete Coach
                case 12 ->{printEco(employee,swimmerCoachDatabase);}
                case 0 -> {isSignedIn = logOut(); /*Logs you out of the system */}
                default -> ui.printLn("Incorrect choice. Chose another option.\n");
            } // End of switch statement
        } // End of while loop
    } // End of method



    /*
     * Prints all the attributes of the menu
     */
    private void printMenu() {
        ui.printLn(menuHeader);
        ui.printLn(leadtext);
        for (String menuOption : menuOptions) {
            ui.printLn(menuOption);
        } // End of for loop
    } // End of method


    /*
     * This method adds a member through the Chairman class
     * Only Employee Privilege level of ADMINISTRATOR can use this method (Chairman class)
     */
    private void addMember(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).addMember(ui, ((Chairman) employee).createMember(ui),
                    swimmerCoachDatabase, fileHandler);
            fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList());
            fileHandler.loggingAction(swimmerCoachDatabase.getMemberList().get(swimmerCoachDatabase.
                    getMemberList().size()-1).getName() + " is now swimming with DELFINEN.");
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Create a new member\".");
        } // End of if / else statement
    } // End of method


    /*
    * This method finds and deletes a member from the Database memberList
    * Only Employee Privilege level of ADMINISTRATOR can use this method (Chairman class)
     */
    private void deleteMember(Employee employee, Database memberList) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman)employee).deleteMember(ui,memberList);
            fileHandler.writeToFullMembersList(memberList.getMemberList());
            fileHandler.loggingAction("Member deleted.");
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Delete a member\".");
        } // End of if / else statement
    } // End of method



    /*
     * This method prints all members from the database through the Chairman class
     * Only Employee Privilege level of ADMINISTRATOR can use this method (Chairman class)
     */
    private void printAllMembers(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            innerMenuPrintAllMembers(employee, database);
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
        } // End of if / else statement
    } // End of method



    /*
     * This method prints all members with arrears through the Treasurer class
     * Only Employee Privilege level of ADMINISTRATOR and ECONOMY_MANAGEMENT can use this method (Chairman/Treasurer class)
     */
    private void printMembersInDebt(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMY_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                      // Creates temporary user for admin
                adminOverride.checkMemberArrears(swimmerCoachDatabase);         // Runs temporary user intended method
                fileHandler.loggingAction("Members in arrear viewed.");
            } else {
                ((Treasurer) employee).checkMemberArrears(swimmerCoachDatabase);    // Runs method as Treasurer
                fileHandler.loggingAction("Members in arrear viewed.");
            } // End of inner if / else statement
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Print members in arrear\".");
        } // End of outer if / else statement
    } // End of method



    /*
     * This method allows for changes within member status, regarding paid status through the Treasurer class
     * Only Employee Privilege level of ADMINISTRATOR and ECONOMY_MANAGEMENT can use this method (Chairman/Treasurer class)
     */
    private void changePayDue(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMY_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                  // Creates temporary user for admin
                adminOverride.setMemberArrears(swimmerCoachDatabase, ui); // Runs temporary user intended method
                fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList()); // Writes changes to file
                fileHandler.loggingAction("A members payment status was changed.");
            } else {
                ((Treasurer) employee).checkMemberArrears(swimmerCoachDatabase);    // Runs method as Treasurer
            } // End of inner if / else statement
            fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList()); // Writes changes to file
            fileHandler.loggingAction("A members payment status was changed.");
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Change a members payment status\".");
        } // End of outer if / else statement
    } // End of method



    /*
     * This method adds swimming result to a member through the Coach class
     * Only Employee Privilege level of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void addSwimResult(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Coach adminOverride = new Coach();                         // Creates temporary user for admin
                adminOverride.addSwimResult(employee,ui, swimmerCoachDatabase,fileHandler); // Runs temporary user method
                fileHandler.loggingAction("A swim result was added.");
            } else {
                ((Coach) employee).addSwimResult(employee,ui, swimmerCoachDatabase,fileHandler); // Runs method as Coach
                fileHandler.loggingAction("A swim result was added.");
            } // End of inner if / else statement

        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Add a swimresult\".");
        } // End of outer if / else statement
    } // End of method




    /*
     * This method prints out a specific Swimmers results
     * Only Employee Privilege level of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void printCompetitiveSwimmersResult(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            innerMenuSwimmerResults(employee, database);
            fileHandler.loggingAction("A competitive swimmers results printed.");
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Print swimming results\".");
        } // End of outer if / else statement
    } // End of method



    /*
     * This method prints and sorts the top 5 swimmer performances based on inputs from user.
     * The method reads input from user: swimDiscipline and period of time to get results
     * Only Employee Privilege level of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void printTopFiveByDiscipline(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Coach adminOverride = new Coach();              // Creates a temporary user for admin
                adminOverride.checkTopFiveCompetitionSwimResults(swimmerCoachDatabase,ui.setSwimmingDisciplineType(), ui); //Runs temporary user method
                fileHandler.loggingAction("Top 5 athletes was printed.");
            } else {
                ((Coach) employee).checkTopFiveCompetitionSwimResults(swimmerCoachDatabase,ui.setSwimmingDisciplineType(), ui); // Runs method as Coach
                fileHandler.loggingAction("Top 5 athletes was printed.");
            } // End of inner if / else statement
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Print top 5 athletes\".");
        } // End of outer if / else statement
    } //End of method




    /*
    * This method prints all the members associated for a specific coach
    * Only Employee Privilege level of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void printSwimmersByCoach(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                // This method makes admin take role of an existing coach, to print his members out
                Coach adminOverride = ((Chairman) employee).chooseCoach(ui, swimmerCoachDatabase);
                adminOverride.findMembersOfCoach(swimmerCoachDatabase, adminOverride,ui); // Runs method as temporary user
                fileHandler.loggingAction("Swimmers with coach association viewed.");
            } else {
                ((Coach) employee).findMembersOfCoach(swimmerCoachDatabase, ((Coach) employee),ui); // Runs method as coach
                fileHandler.loggingAction("Swimmers with coach association viewed.");
            } // End of inner if / else statement
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Print swimmer associated with coach\".");
        } // End of outer if / else statement
    } // End of method



    /*
    * This method allows chairman to create a coach with login credentials, so he can be a user of the system
     */
    private void createCoach(Employee employee, Database database, UI ui){
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).createCoach(database,ui, fileHandler);
            fileHandler.writeToCoachlist(database.getCoachList());
            fileHandler.loggingAction("A new coach added.");
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Create coach\".");
        } // End of if / else statement
    } // End of method

    private void deleteCoach(Employee employee, Database database, UI ui){
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).deleteCoach(database,ui,fileHandler);
            fileHandler.writeToCoachlist(database.getCoachList());
            fileHandler.writeToSwimmerCoachAssociationFile(database);
            fileHandler.loggingAction("A coach got deleted.");
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Delete coach\".");
        } // End of if / else statement
    } // End of method

    private void printEco(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMY_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                  // Creates temporary user for admin
                adminOverride.printEconomyInfo(swimmerCoachDatabase); // Runs temporary user intended method
                fileHandler.loggingAction("Economy details viewed.");
            } else {
                ((Treasurer) employee).printEconomyInfo(swimmerCoachDatabase);
            } // End of inner if / else statement
            fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList()); // Writes changes to file
            fileHandler.loggingAction("Economy details viewed.");
        } else {
            ui.printLn("You don't have the privilege to use this function");
            fileHandler.loggingAction("Unauthorised user tried to access \"Print economy\".");
        } // End of outer if / else statement
    } // End of method



    public void innerMenuSwimmerResults(Employee employee, Database database) {
        boolean chooseSortMethod = true;
        MenuRun innerMenu = new MenuRun("SORTING OPTIONS", "\u001B[1mChose an option:\u001B[0m", new String[] {
                "1. Show all Results for Swimmer",
                "2. Show by Date",
                "3. Sort by Distance",
                "4. Sort by Competitiveness",
                "5. Sort by Rank"
        });

        while(chooseSortMethod) {
            innerMenu.printMenu();
            switch (ui.readInt()) {
                case 1 -> {
                    //Method reads input from user: swimDiscipline and period of time to get results
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        adminOverride.checkCompetitorSwimResults(
                                adminOverride.lookupSwimmer(ui, database)); //Runs temporary user method
                    } else {
                        ((Coach) employee).checkCompetitorSwimResults(
                                ((Coach) employee).lookupSwimmer(ui, database)); // runs method as Coach
                    } // End of inner if / else statement
                    chooseSortMethod = false;
                }
                case 2 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        sorter.setSortByDate(ui,sorter.
                                oneSwimmersResultList(adminOverride.
                                        lookupSwimmer(ui, database),database,ui.setSwimmingDisciplineType()));
                    } else {
                        sorter.setSortByDate(ui,sorter.
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(ui,database),database,ui.setSwimmingDisciplineType()));
                    }
                    chooseSortMethod = false;

                }
                case 3 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        sorter.setSortByDistance(ui,sorter.
                                oneSwimmersResultList(adminOverride.
                                        lookupSwimmer(ui, database),database,ui.setSwimmingDisciplineType()));
                    } else {
                        sorter.setSortByDistance(ui,sorter.
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(ui,database),database,ui.setSwimmingDisciplineType()));
                    }
                    chooseSortMethod = false;
                }
                case 4 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        sorter.setSortByIsCompetitive(ui,sorter.
                                oneSwimmersResultList(adminOverride.
                                        lookupSwimmer(ui, database),database,ui.setSwimmingDisciplineType()));
                    } else {
                        sorter.setSortByIsCompetitive(ui,sorter.
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(ui,database),database,ui.setSwimmingDisciplineType()));
                    }
                    chooseSortMethod = false;
                } // End
                case 5 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        sorter.setSortByRank(ui,sorter.
                                oneSwimmersResultList(adminOverride.
                                        lookupSwimmer(ui, database),database,ui.setSwimmingDisciplineType()));
                    } else {
                        sorter.setSortByRank(ui,sorter.
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(ui,database),database,ui.setSwimmingDisciplineType()));
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 4
            } // End of switch case
        } // End of while loop
    } // End of method



    public void innerMenuPrintAllMembers(Employee employee, Database database) {
        boolean chooseSortMethod = true;
        MenuRun innerMenu = new MenuRun("SORTING OPTIONS", "\u001B[1mChose an option:\u001B[0m", new String[] {
                "1. Sort by Name",
                "2. Sort by Age",
                "3. Sort by ID",
                "4. Sort by Phone Number",
                "0. Back to Head Menu"
        });

        while (chooseSortMethod) {
            innerMenu.printMenu();
            switch (ui.readInt()) {
                case 1 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(sorter.setSortByMemberName(database));
                        fileHandler.loggingAction("All members was viewed, sorted by Name.");
                    } else {
                        ui.printLn("You don't have the privilege to use this function");
                        fileHandler.loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 1 statment
                case 2 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(sorter.setSortByMemberAge(database));
                        fileHandler.loggingAction("All members was viewed, sorted by Age.");
                    } else {
                        ui.printLn("You don't have the privilege to use this function");
                        fileHandler.loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 2 statement
                case 3 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(sorter.setSortByMemberID(database));
                        fileHandler.loggingAction("All members was viewed, sorted by ID.");
                    } else {
                        ui.printLn("You don't have the privilege to use this function");
                        fileHandler.loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 3 statement
                case 4 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(sorter.setSortByMemberPhoneNumber(database));
                        fileHandler.loggingAction("All members was viewed, sorted by Phone number.");
                    } else {
                        ui.printLn("You don't have the privilege to use this function");
                        fileHandler.loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 4 statement
                case 0 -> {
                    ui.printLn("Returning to Head Menu");
                    chooseSortMethod = false;
                }
                default -> {


                } // ENd of default case
            } // End of switch case
        } // End of while loop
    } // End of method




    /*
    * This method will log out the user and terminate the program
     */
    private boolean logOut() {
        ui.printLn("Until next time!");
        fileHandler.loggingAction("Program terminated.");
        System.exit(0);
        return false;
    } // End of Method

} // End of Class