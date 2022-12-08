package utility;

import actors.*;
import database.Database;

/*
 * This class represent the menu and the interaction of user and system. Whenever an input is received for the menu
 *  the corresponding reaction is followed by menuOptions titles. Each option is verifying the user privilege, to ensure
 *  no user is acting with malice or doing thinks not in his/hers jurisdiction.
 */
public class MenuRun {
    private final String[] menuOptions;
    private final String menuHeader;
    private final String leadText;

    // Constructor  -----------------------------------------
    public MenuRun(String menuHeader, String leadtext, String[] menuOptions) {
        this.menuHeader = menuHeader;
        this.menuOptions = menuOptions;
        this.leadText = leadtext;
    }


    /*
     * This method is looping each option a user can interact with within the menu
     */
    protected void menuLooping(Employee employee, Database database) {
        boolean isSignedIn = true;
        while (isSignedIn) {
            UI.getInstance().printLn("");
            printMenu();
            switch (UI.getInstance().readInt()) {
                case 1 -> addMember(employee, database); /*add new member to all members lists */
                case 2 -> deleteMember(employee, database); /*deletes member from members lists*/
                case 3 -> printAllMembers(employee, database);/*print all members */
                case 4 -> printMembersInDebt(employee, database); /*Prints list of members who hasn't paid */
                case 5 -> changePayDue(employee, database);
                case 6 -> addSwimResult(employee, database);/*add swimResult*/
                case 7 -> printCompetitiveSwimmersResult(employee, database); /*Prints 1 swimmers results*/
                case 8 -> printTopFiveByDiscipline(employee, database);/*Prints top 5 in 1 discipline*/
                case 9 -> printSwimmersByCoach(employee, database);/*Prints all members for specific coach*/
                case 10 -> createCoach(employee, database, UI.getInstance());/*Create a coach and add them to coachlist.*/
                case 11 -> deleteCoach(employee, database, UI.getInstance());//Delete Coach
                case 12 -> printEco(employee, database);
                case 0 -> isSignedIn = logOut(); /*Logs you out of the system */
                default -> UI.getInstance().printLn("Incorrect choice. Chose another option.\n");
            } // End of switch statement
        } // End of while loop
    } // End of method


    /*
     * Prints all the attributes of the menu
     */
    private void printMenu() {
        UI.getInstance().printLn(menuHeader);
        UI.getInstance().printLn(leadText);
        for (String menuOption : menuOptions) {
            UI.getInstance().printLn(menuOption);
        } // End of for loop
    } // End of method


    /*
     * This method adds a member through the Chairman class
     * Only Employee Privilege level of ADMINISTRATOR can use this method (Chairman class)
     */
    private void addMember(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).addMember(UI.getInstance(), ((Chairman) employee).createMember(UI.getInstance()),
                    database);
            FileHandler.getInstance().writeToFullMembersList(database.getMemberList());
            FileHandler.getInstance().writeToSwimmerCoachAssociationFile(database);
            FileHandler.getInstance().loggingAction(database.getMemberList().get(database.
                    getMemberList().size() - 1).getName() + " is now swimming with DELFINEN.");
        } else {
            UI.getInstance().printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Create a new member\".");
        } // End of if / else statement
    } // End of method


    /*
     * This method finds and deletes a member from the Database memberList
     * Only Employee Privilege level of ADMINISTRATOR can use this method (Chairman class)
     */
    private void deleteMember(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).deleteMember(UI.getInstance(), database);
            FileHandler.getInstance().writeToFullMembersList(database.getMemberList());
            FileHandler.getInstance().loggingAction("Member deleted.");
        } else {
            UI.getInstance().printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Delete a member\".");
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
            UI.getInstance().printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
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
                FileHandler.getInstance().loggingAction("Members in arrear viewed.");
            } else {
                ((Treasurer) employee).checkMemberArrears(swimmerCoachDatabase);    // Runs method as Treasurer
                FileHandler.getInstance().loggingAction("Members in arrear viewed.");
            } // End of inner if / else statement
        } else {
            UI.getInstance().printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print members in arrear\".");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method allows for changes within member status, regarding paid status through the Treasurer class
     * Only Employee Privilege level of ADMINISTRATOR and ECONOMY_MANAGEMENT can use this method (Chairman/Treasurer class)
     */
    private void changePayDue(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMY_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                  // Creates temporary user for admin
                adminOverride.setMemberArrears(database, UI.getInstance()); // Runs temporary user intended method
                FileHandler.getInstance().writeToFullMembersList(database.getMemberList()); // Writes changes to file
                FileHandler.getInstance().loggingAction("A members payment status was changed.");
            } else {
                ((Treasurer) employee).checkMemberArrears(database);    // Runs method as Treasurer
            } // End of inner if / else statement
            FileHandler.getInstance().writeToFullMembersList(database.getMemberList()); // Writes changes to file
            FileHandler.getInstance().loggingAction("A members payment status was changed.");
        } else {
            UI.getInstance().printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Change a members payment status\".");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method adds swimming result to a member through the Coach class
     * Only Employee Privilege level of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void addSwimResult(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            innerMenuAddSwimResults(employee, database);

        } else {
            UI.getInstance().printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Add a swimresult\".");
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
            FileHandler.getInstance().loggingAction("A competitive swimmers results printed.");
        } else {
            UI.getInstance().printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print swimming results\".");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method prints and sorts the top 5 swimmer performances based on inputs from user.
     * The method reads input from user: swimDiscipline and period of time to get results
     * Only Employee Privilege level of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void printTopFiveByDiscipline(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
            UI.getInstance().printLn("Crawl, butterfly, breaststroke, ");
            SuperSorterThreeThousand.getInstance().topFiveSmadderButRefactored(UI.getInstance().setSwimmingDisciplineType(),
                    UI.getInstance().setDistance(), database.getSwimmersCoachAssociationList());
            //SuperSorterThreeThousand.getInstance().topFiveMemberResults(UI.getInstance(), database.getSwimmersCoachAssociationList());
            FileHandler.getInstance().loggingAction("Top 5 athletes was printed.");
        } else {
            UI.getInstance().printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print top 5 athletes\".");
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
                Coach adminOverride = ((Chairman) employee).chooseCoach(UI.getInstance(), swimmerCoachDatabase, false);
                adminOverride.findMembersOfCoach(swimmerCoachDatabase, adminOverride); // Runs method as temporary user
                FileHandler.getInstance().loggingAction("Swimmers with coach association viewed.");
            } else {
                ((Coach) employee).findMembersOfCoach(swimmerCoachDatabase, ((Coach) employee)); // Runs method as coach
                FileHandler.getInstance().loggingAction("Swimmers with coach association viewed.");
            } // End of inner if / else statement
        } else {
            UI.getInstance().printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print swimmer associated with coach\".");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method allows chairman to create a coach with login credentials, so he can be a user of the system
     */
    private void createCoach(Employee employee, Database database, UI ui) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).createCoach(database, ui, FileHandler.getInstance());
            FileHandler.getInstance().writeToCoachList(database.getCoachList());
            FileHandler.getInstance().writeCoachUserAndPassToList(
                    database.getCoachList().get(database.getCoachList().size()-1).getUsername(),
                    database.getCoachList().get(database.getCoachList().size()-1).getPassword());

            FileHandler.getInstance().loggingAction("A new coach added.");
        } else {
            ui.printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Create coach\".");
        } // End of if / else statement
    } // End of method

    private void deleteCoach(Employee employee, Database database, UI ui) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
            ui.printLn("Write the name of the coach you would like to remove:");
            String findCoach = ui.readLine();
            ui.printLn("Write the username for the coach:");
            String coachUsername = ui.readLine();

            ((Chairman) employee).deleteCoach(findCoach, database, ui);
            FileHandler.getInstance().deleteCoachLoginFromFile(coachUsername);
            FileHandler.getInstance().writeToCoachList(database.getCoachList());
            FileHandler.getInstance().writeToSwimmerCoachAssociationFile(database);
            FileHandler.getInstance().loggingAction(findCoach + " is no longer a DELFINEN coach.");
        } else {
            ui.printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Delete coach\".");
        } // End of if / else statement
    } // End of method


    private void printEco(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMY_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                  // Creates temporary user for admin
                adminOverride.printEconomyInfo(database); // Runs temporary user intended method
                FileHandler.getInstance().loggingAction("Economy details viewed.");
            } else {
                ((Treasurer) employee).printEconomyInfo(database);
            } // End of inner if / else statement
            FileHandler.getInstance().writeToFullMembersList(database.getMemberList()); // Writes changes to file
            FileHandler.getInstance().loggingAction("Economy details viewed.");
        } else {
            UI.getInstance().printLn("You don't have the privilege to use this function");
            FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print economy\".");
        } // End of outer if / else statement
    } // End of method


    public void innerMenuSwimmerResults(Employee employee, Database database) {
        boolean chooseSortMethod = true;
        MenuRun innerMenu = new MenuRun("SORTING OPTIONS", "\u001B[1mChose an option:\u001B[0m", new String[]{
                "1. Show all Results for Swimmer",
                "2. Show by Date",
                "3. Sort by Distance",
                "4. Sort by Competitiveness",
                "5. Sort by Rank"
        });

        while (chooseSortMethod) {
            innerMenu.printMenu();
            switch (UI.getInstance().readInt()) {
                case 1 -> {
                    //Method reads input from user: swimDiscipline and period of time to get results
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        adminOverride.checkCompetitorSwimResults(
                                adminOverride.lookupSwimmer(UI.getInstance(), database)); //Runs temporary user method
                    } else {
                        ((Coach) employee).checkCompetitorSwimResults(
                                ((Coach) employee).lookupSwimmer(UI.getInstance(), database)); // runs method as Coach
                    } // End of inner if / else statement
                    chooseSortMethod = false;
                    FileHandler.getInstance().loggingAction("All results of a certain swimmer was viewed.");
                } // End of case 1
                case 2 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SuperSorterThreeThousand.getInstance().setSortByDate(UI.getInstance(), SuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(UI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        UI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SuperSorterThreeThousand.getInstance().setSortByDate(UI.getInstance(), SuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(UI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        UI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    FileHandler.getInstance().loggingAction("Results on a certain date for a swimmer was viewed.");
                } // End of case 2
                case 3 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SuperSorterThreeThousand.getInstance().setSortByDistance(UI.getInstance(), SuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(UI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        UI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SuperSorterThreeThousand.getInstance().setSortByDistance(UI.getInstance(), SuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(UI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        UI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    FileHandler.getInstance().loggingAction("Results on a certain distance for a swimmer was viewed.");
                } // End of case 3
                case 4 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SuperSorterThreeThousand.getInstance().setSortByIsCompetitive(UI.getInstance(), SuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(UI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        UI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SuperSorterThreeThousand.getInstance().setSortByIsCompetitive(UI.getInstance(), SuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(UI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        UI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    FileHandler.getInstance().loggingAction("Results on a certain date for a swimmer was viewed.");
                } // End of case 4
                case 5 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SuperSorterThreeThousand.getInstance().setSortByRank(UI.getInstance(), SuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(UI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        UI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SuperSorterThreeThousand.getInstance().setSortByRank(UI.getInstance(), SuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(UI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        UI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    FileHandler.getInstance().loggingAction("Results of either training or competitiveness, for a swimmer was viewed.");
                } // End of case 5
            } // End of switch case
        } // End of while loop
    } // End of method


    public void innerMenuPrintAllMembers(Employee employee, Database database) {
        boolean chooseSortMethod = true;
        MenuRun innerMenu = new MenuRun("SORTING OPTIONS", "\u001B[1mChose an option:\u001B[0m", new String[]{
                "1. Sort by Name",
                "2. Sort by Age",
                "3. Sort by ID",
                "4. Sort by Phone Number",
                "0. Back to Head Menu"
        });

        while (chooseSortMethod) {
            innerMenu.printMenu();
            switch (UI.getInstance().readInt()) {
                case 1 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SuperSorterThreeThousand.getInstance().setSortByMemberName(database.getMemberList()));
                        FileHandler.getInstance().loggingAction("All members was viewed, sorted by Name.");
                    } else {
                        UI.getInstance().printLn("You don't have the privilege to use this function");
                        FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 1
                case 2 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SuperSorterThreeThousand.getInstance().setSortByMemberAge(database.getMemberList()));
                        FileHandler.getInstance().loggingAction("All members was viewed, sorted by Age.");
                    } else {
                        UI.getInstance().printLn("You don't have the privilege to use this function");
                        FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 2
                case 3 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SuperSorterThreeThousand.getInstance().setSortByMemberID(database.getMemberList()));
                        FileHandler.getInstance().loggingAction("All members was viewed, sorted by ID.");
                    } else {
                        UI.getInstance().printLn("You don't have the privilege to use this function");
                        FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 3
                case 4 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SuperSorterThreeThousand.getInstance().setSortByMemberPhoneNumber(database.getMemberList()));
                        FileHandler.getInstance().loggingAction("All members was viewed, sorted by Phone number.");
                    } else {
                        UI.getInstance().printLn("You don't have the privilege to use this function");
                        FileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 4
                case 0 -> {
                    UI.getInstance().printLn("Returning to Head Menu");
                    chooseSortMethod = false;
                } // End of case 0
                default -> UI.getInstance().printLn("Invalid input, please try again"); // ENd of default case
            } // End of switch case
        } // End of while loop
    } // End of method


    public void innerMenuAddSwimResults(Employee employee, Database database) {
        MenuRun innerMenu = new MenuRun("\u001B[1mADD SWIMRESULTS\u001B[0m", "Which team is swimmer on?", new String[]{
                "Choose either: ",
                "1. Junior Team",
                "2. Adult Team",
                "3. See all your members",
                "0. Return to Head Menu"
        });


        boolean chooseTeam = true;
        while (chooseTeam) {
            innerMenu.printMenu();
            int readInput = UI.getInstance().readInt();
            switch (readInput) {
                case 1, 2, 3 -> {
                    if (employee instanceof Chairman) {
                        try {
                            UI.getInstance().printLn("As chairman, please enter the coach name, you wish to see respective members of");
                            Coach adminOverride = ((Chairman) employee).chooseCoach(UI.getInstance(), database, false); // Creates temporary user for admin
                            SuperSorterThreeThousand.getInstance().setSortByTeam(readInput, adminOverride, database.getSwimmersCoachAssociationList());
                            CompetitiveSwimmer swimmer = adminOverride.loadSwimmer(UI.getInstance(),database);
                            swimmer.getSwimmingDisciplineList().forEach(swimmingDiscipline -> UI.getInstance().print(" | " + swimmingDiscipline.getSwimmingDisciplineType()));
                            UI.getInstance().printLn(" | ");
                            SwimmingDiscipline.SwimmingDisciplineTypes disciplineType = UI.getInstance().setSwimmingDisciplineType();
                            adminOverride.addSwimResult(UI.getInstance(), swimmer, disciplineType ); // Runs temporary user method
                            FileHandler.getInstance().appendResult(database.getSwimmersCoachAssociationList(), swimmer,
                                    disciplineType);
                            FileHandler.getInstance().loggingAction("A swim result was added.");
                        } catch (NullPointerException e) {
                            UI.getInstance().printLn("Swimmer does not exist");
                        }

                    } else {
                        try {
                            SuperSorterThreeThousand.getInstance().setSortByTeam(readInput, ((Coach) employee), database.getSwimmersCoachAssociationList());
                            CompetitiveSwimmer swimmer = ((Coach) employee).loadSwimmer(UI.getInstance(), database);
                            swimmer.getSwimmingDisciplineList().forEach(swimmingDiscipline -> UI.getInstance().print(" | " + swimmingDiscipline.getSwimmingDisciplineType()));
                            UI.getInstance().printLn(" | ");
                            SwimmingDiscipline.SwimmingDisciplineTypes disciplineType = UI.getInstance().setSwimmingDisciplineType();
                            ((Coach) employee).addSwimResult(UI.getInstance(), swimmer, disciplineType); // Runs method as Coach
                            FileHandler.getInstance().appendResult(database.getSwimmersCoachAssociationList(), swimmer,
                                    disciplineType);
                            FileHandler.getInstance().loggingAction("A swim result was added.");
                        } catch (NullPointerException e) {
                            UI.getInstance().printLn("Swimmer does not exist");

                        } // End of try / catch statement
                    } // End of inner if / else statement

                    chooseTeam = false;
                }
                case 0 -> {
                    UI.getInstance().printLn("Returning to Head Menu");
                    chooseTeam = false;
                }
                default -> UI.getInstance().printLn("Not a valid option");
            } // End of switch statement
        } // End of while loop
    } // End of method


    /*
     * This method will log out the user and terminate the program
     */
    private boolean logOut() {
        UI.getInstance().printLn("Until next time!");
        FileHandler.getInstance().loggingAction("Program terminated.");
        System.exit(0);
        return false;
    } // End of Method

} // End of Class