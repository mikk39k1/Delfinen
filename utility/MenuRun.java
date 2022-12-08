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
            SingleTonUI.getInstance().printLn("");
            printMenu();
            switch (SingleTonUI.getInstance().readInt()) {
                case 1 -> addMember(employee, database); /*add new member to all members lists */
                case 2 -> deleteMember(employee, database); /*deletes member from members lists*/
                case 3 -> printAllMembers(employee, database);/*print all members */
                case 4 -> printMembersInDebt(employee, database); /*Prints list of members who hasn't paid */
                case 5 -> changePayDue(employee, database);
                case 6 -> addSwimResult(employee, database);/*add swimResult*/
                case 7 -> printCompetitiveSwimmersResult(employee, database); /*Prints 1 swimmers results*/
                case 8 -> printTopFiveByDiscipline(employee, database);/*Prints top 5 in 1 discipline*/
                case 9 -> printSwimmersByCoach(employee, database);/*Prints all members for specific coach*/
                case 10 -> createCoach(employee, database, SingleTonUI.getInstance());/*Create a coach and add them to coachlist.*/
                case 11 -> deleteCoach(employee, database, SingleTonUI.getInstance());//Delete Coach
                case 12 -> printEco(employee, database);
                case 0 -> isSignedIn = logOut(); /*Logs you out of the system */
                default -> SingleTonUI.getInstance().printLn("Incorrect choice. Chose another option.\n");
            } // End of switch statement
        } // End of while loop
    } // End of method


    /*
     * Prints all the attributes of the menu
     */
    private void printMenu() {
        SingleTonUI.getInstance().printLn(menuHeader);
        SingleTonUI.getInstance().printLn(leadText);
        for (String menuOption : menuOptions) {
            SingleTonUI.getInstance().printLn(menuOption);
        } // End of for loop
    } // End of method


    /*
     * This method adds a member through the Chairman class
     * Only Employee Privilege level of ADMINISTRATOR can use this method (Chairman class)
     */
    private void addMember(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).addMember(SingleTonUI.getInstance(), ((Chairman) employee).createMember(SingleTonUI.getInstance()),
                    database);
            SingleTonFileHandler.getInstance().writeToFullMembersList(database.getMemberList());
            SingleTonFileHandler.getInstance().writeToSwimmerCoachAssociationFile(database);
            SingleTonFileHandler.getInstance().loggingAction(database.getMemberList().get(database.
                    getMemberList().size() - 1).getName() + " is now swimming with DELFINEN.");
        } else {
            SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Create a new member\".");
        } // End of if / else statement
    } // End of method


    /*
     * This method finds and deletes a member from the Database memberList
     * Only Employee Privilege level of ADMINISTRATOR can use this method (Chairman class)
     */
    private void deleteMember(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).deleteMember(SingleTonUI.getInstance(), database);
            SingleTonFileHandler.getInstance().writeToFullMembersList(database.getMemberList());
            SingleTonFileHandler.getInstance().loggingAction("Member deleted.");
        } else {
            SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Delete a member\".");
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
            SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
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
                SingleTonFileHandler.getInstance().loggingAction("Members in arrear viewed.");
            } else {
                ((Treasurer) employee).checkMemberArrears(swimmerCoachDatabase);    // Runs method as Treasurer
                SingleTonFileHandler.getInstance().loggingAction("Members in arrear viewed.");
            } // End of inner if / else statement
        } else {
            SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print members in arrear\".");
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
                adminOverride.setMemberArrears(database, SingleTonUI.getInstance()); // Runs temporary user intended method
                SingleTonFileHandler.getInstance().writeToFullMembersList(database.getMemberList()); // Writes changes to file
                SingleTonFileHandler.getInstance().loggingAction("A members payment status was changed.");
            } else {
                ((Treasurer) employee).checkMemberArrears(database);    // Runs method as Treasurer
            } // End of inner if / else statement
            SingleTonFileHandler.getInstance().writeToFullMembersList(database.getMemberList()); // Writes changes to file
            SingleTonFileHandler.getInstance().loggingAction("A members payment status was changed.");
        } else {
            SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Change a members payment status\".");
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
            SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Add a swimresult\".");
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
            SingleTonFileHandler.getInstance().loggingAction("A competitive swimmers results printed.");
        } else {
            SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print swimming results\".");
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
            SingleTonUI.getInstance().printLn("Crawl, Butterfly, Breaststroke, Freestyle, Backcrawl");
            SingleTonSuperSorterThreeThousand.getInstance().topFiveSmadderButRefactored(SingleTonUI.getInstance().setSwimmingDisciplineType(),
                    SingleTonUI.getInstance().setDistance(), database.getSwimmersCoachAssociationList());
            //SuperSorterThreeThousand.getInstance().topFiveMemberResults(UI.getInstance(), database.getSwimmersCoachAssociationList());
            SingleTonFileHandler.getInstance().loggingAction("Top 5 athletes was printed.");
        } else {
            SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print top 5 athletes\".");
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
                Coach adminOverride = ((Chairman) employee).chooseCoach(SingleTonUI.getInstance(), swimmerCoachDatabase, false);
                adminOverride.findMembersOfCoach(swimmerCoachDatabase, adminOverride); // Runs method as temporary user
                SingleTonFileHandler.getInstance().loggingAction("Swimmers with coach association viewed.");
            } else {
                ((Coach) employee).findMembersOfCoach(swimmerCoachDatabase, ((Coach) employee)); // Runs method as coach
                SingleTonFileHandler.getInstance().loggingAction("Swimmers with coach association viewed.");
            } // End of inner if / else statement
        } else {
            SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print swimmer associated with coach\".");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method allows chairman to create a coach with login credentials, so he can be a user of the system
     */
    private void createCoach(Employee employee, Database database, SingleTonUI singleTonUi) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).createCoach(database, singleTonUi);
            SingleTonFileHandler.getInstance().writeToCoachList(database.getCoachList());
            SingleTonFileHandler.getInstance().writeCoachUserAndPassToList(
                    database.getCoachList().get(database.getCoachList().size() - 1).getUsername(),
                    database.getCoachList().get(database.getCoachList().size() - 1).getPassword());

            SingleTonFileHandler.getInstance().loggingAction("A new coach added.");
        } else {
            singleTonUi.printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Create coach\".");
        } // End of if / else statement
    } // End of method

    private void deleteCoach(Employee employee, Database database, SingleTonUI singleTonUi) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
            singleTonUi.printLn("Write the name of the coach you would like to remove:");
            String findCoach = singleTonUi.readLine();
            singleTonUi.printLn("Write the username for the coach:");
            String coachUsername = singleTonUi.readLine();

            ((Chairman) employee).deleteCoach(findCoach, database, singleTonUi);
            SingleTonFileHandler.getInstance().deleteCoachLoginFromFile(coachUsername);
            SingleTonFileHandler.getInstance().writeToCoachList(database.getCoachList());
            SingleTonFileHandler.getInstance().writeToSwimmerCoachAssociationFile(database);
            SingleTonFileHandler.getInstance().loggingAction(findCoach + " is no longer a DELFINEN coach.");
        } else {
            singleTonUi.printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Delete coach\".");
        } // End of if / else statement
    } // End of method


    private void printEco(Employee employee, Database database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMY_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                  // Creates temporary user for admin
                adminOverride.printEconomyInfo(database); // Runs temporary user intended method
                SingleTonFileHandler.getInstance().loggingAction("Economy details viewed.");
            } else {
                ((Treasurer) employee).printEconomyInfo(database);
            } // End of inner if / else statement
            SingleTonFileHandler.getInstance().writeToFullMembersList(database.getMemberList()); // Writes changes to file
            SingleTonFileHandler.getInstance().loggingAction("Economy details viewed.");
        } else {
            SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print economy\".");
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
            switch (SingleTonUI.getInstance().readInt()) {
                case 1 -> {
                    //Method reads input from user: swimDiscipline and period of time to get results
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        adminOverride.checkCompetitorSwimResults(
                                adminOverride.lookupSwimmer(SingleTonUI.getInstance(), database)); //Runs temporary user method
                    } else {
                        ((Coach) employee).checkCompetitorSwimResults(
                                ((Coach) employee).lookupSwimmer(SingleTonUI.getInstance(), database)); // runs method as Coach
                    } // End of inner if / else statement
                    chooseSortMethod = false;
                    SingleTonFileHandler.getInstance().loggingAction("All results of a certain swimmer was viewed.");
                } // End of case 1
                case 2 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SingleTonSuperSorterThreeThousand.getInstance().setSortByDate(SingleTonUI.getInstance(), SingleTonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(SingleTonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingleTonUI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SingleTonSuperSorterThreeThousand.getInstance().setSortByDate(SingleTonUI.getInstance(), SingleTonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(SingleTonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingleTonUI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    SingleTonFileHandler.getInstance().loggingAction("Results on a certain date for a swimmer was viewed.");
                } // End of case 2
                case 3 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SingleTonSuperSorterThreeThousand.getInstance().setSortByDistance(SingleTonUI.getInstance(), SingleTonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(SingleTonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingleTonUI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SingleTonSuperSorterThreeThousand.getInstance().setSortByDistance(SingleTonUI.getInstance(), SingleTonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(SingleTonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingleTonUI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    SingleTonFileHandler.getInstance().loggingAction("Results on a certain distance for a swimmer was viewed.");
                } // End of case 3
                case 4 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SingleTonSuperSorterThreeThousand.getInstance().setSortByIsCompetitive(SingleTonUI.getInstance(), SingleTonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(SingleTonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingleTonUI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SingleTonSuperSorterThreeThousand.getInstance().setSortByIsCompetitive(SingleTonUI.getInstance(), SingleTonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(SingleTonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingleTonUI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    SingleTonFileHandler.getInstance().loggingAction("Results on a certain date for a swimmer was viewed.");
                } // End of case 4
                case 5 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SingleTonSuperSorterThreeThousand.getInstance().setSortByRank(SingleTonUI.getInstance(), SingleTonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(SingleTonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingleTonUI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SingleTonSuperSorterThreeThousand.getInstance().setSortByRank(SingleTonUI.getInstance(), SingleTonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(SingleTonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingleTonUI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    SingleTonFileHandler.getInstance().loggingAction("Results of either training or competitiveness, for a swimmer was viewed.");
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
            switch (SingleTonUI.getInstance().readInt()) {
                case 1 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SingleTonSuperSorterThreeThousand.getInstance().setSortByMemberName(database.getMemberList()));
                        SingleTonFileHandler.getInstance().loggingAction("All members was viewed, sorted by Name.");
                    } else {
                        SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
                        SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 1
                case 2 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SingleTonSuperSorterThreeThousand.getInstance().setSortByMemberAge(database.getMemberList()));
                        SingleTonFileHandler.getInstance().loggingAction("All members was viewed, sorted by Age.");
                    } else {
                        SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
                        SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 2
                case 3 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SingleTonSuperSorterThreeThousand.getInstance().setSortByMemberID(database.getMemberList()));
                        SingleTonFileHandler.getInstance().loggingAction("All members was viewed, sorted by ID.");
                    } else {
                        SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
                        SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 3
                case 4 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SingleTonSuperSorterThreeThousand.getInstance().setSortByMemberPhoneNumber(database.getMemberList()));
                        SingleTonFileHandler.getInstance().loggingAction("All members was viewed, sorted by Phone number.");
                    } else {
                        SingleTonUI.getInstance().printLn("You don't have the privilege to use this function");
                        SingleTonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 4
                case 0 -> {
                    SingleTonUI.getInstance().printLn("Returning to Head Menu");
                    chooseSortMethod = false;
                } // End of case 0
                default -> SingleTonUI.getInstance().printLn("Invalid input, please try again"); // ENd of default case
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
            int readInput = SingleTonUI.getInstance().readInt();
            switch (readInput) {
                case 1, 2, 3 -> {
                    if (employee instanceof Chairman) {
                        try {
                            SingleTonUI.getInstance().printLn("As chairman, please enter the coach name, you wish to see respective members of");
                            Coach adminOverride = ((Chairman) employee).chooseCoach(SingleTonUI.getInstance(), database, false); // Creates temporary user for admin
                            SingleTonSuperSorterThreeThousand.getInstance().setSortByTeam(readInput, adminOverride, database.getSwimmersCoachAssociationList());
                            CompetitiveSwimmer swimmer = adminOverride.loadSwimmer(SingleTonUI.getInstance(), database);
                            swimmer.getSwimmingDisciplineList().forEach(swimmingDiscipline -> SingleTonUI.getInstance().print(" | " + swimmingDiscipline.getSwimmingDisciplineType()));
                            SingleTonUI.getInstance().printLn(" | ");
                            SwimmingDiscipline.SwimmingDisciplineTypes disciplineType = SingleTonUI.getInstance().setSwimmingDisciplineType();
                            adminOverride.addSwimResult(SingleTonUI.getInstance(), swimmer, disciplineType); // Runs temporary user method
                            SingleTonFileHandler.getInstance().appendResult(database.getSwimmersCoachAssociationList(), swimmer,
                                    disciplineType);
                            SingleTonFileHandler.getInstance().loggingAction("A swim result was added.");
                        } catch (NullPointerException e) {
                            SingleTonUI.getInstance().printLn("Swimmer does not exist");
                        }

                    } else {
                        try {
                            SingleTonSuperSorterThreeThousand.getInstance().setSortByTeam(readInput, ((Coach) employee), database.getSwimmersCoachAssociationList());
                            CompetitiveSwimmer swimmer = ((Coach) employee).loadSwimmer(SingleTonUI.getInstance(), database);
                            swimmer.getSwimmingDisciplineList().forEach(swimmingDiscipline -> SingleTonUI.getInstance().print(" | " + swimmingDiscipline.getSwimmingDisciplineType()));
                            SingleTonUI.getInstance().printLn(" | ");
                            SwimmingDiscipline.SwimmingDisciplineTypes disciplineType = SingleTonUI.getInstance().setSwimmingDisciplineType();
                            ((Coach) employee).addSwimResult(SingleTonUI.getInstance(), swimmer, disciplineType); // Runs method as Coach
                            SingleTonFileHandler.getInstance().appendResult(database.getSwimmersCoachAssociationList(), swimmer,
                                    disciplineType);
                            SingleTonFileHandler.getInstance().loggingAction("A swim result was added.");
                        } catch (NullPointerException e) {
                            SingleTonUI.getInstance().printLn("Swimmer does not exist");

                        } // End of try / catch statement
                    } // End of inner if / else statement

                    chooseTeam = false;
                }
                case 0 -> {
                    SingleTonUI.getInstance().printLn("Returning to Head Menu");
                    chooseTeam = false;
                }
                default -> SingleTonUI.getInstance().printLn("Not a valid option");
            } // End of switch statement
        } // End of while loop
    } // End of method


    /*
     * This method will log out the user and terminate the program
     */
    private boolean logOut() {
        SingleTonUI.getInstance().printLn("Until next time!");
        SingleTonFileHandler.getInstance().loggingAction("Program terminated.");
        System.exit(0);
        return false;
    } // End of Method

} // End of Class