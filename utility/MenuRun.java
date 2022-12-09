package utility;

import actors.*;
import database.SingletonDatabase;

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
    protected void menuLooping(Employee employee, SingletonDatabase singleTonDatabase) {
        boolean isSignedIn = true;
        while (isSignedIn) {
            SingletonUI.getInstance().printLn("");
            printMenu();
            switch (SingletonUI.getInstance().readInt()) {
                case 1 -> addMember(employee, singleTonDatabase); /* Add new member to all members lists */
                case 2 -> deleteMember(employee, singleTonDatabase); /* Deletes member from members lists */
                case 3 -> printAllMembers(employee, singleTonDatabase);/*Print all members */
                case 4 -> printMembersInDebt(employee, singleTonDatabase); /* Prints list of members who hasn't paid */
                case 5 -> changePayDue(employee, singleTonDatabase); /* Change payment status for specific members */
                case 6 -> addSwimResult(employee, singleTonDatabase);/* Add swimResult*/
                case 7 -> printCompetitiveSwimmersResult(employee, singleTonDatabase); /* Prints 1 swimmers results*/
                case 8 -> printTopFiveByDiscipline(employee, singleTonDatabase);/* Prints top 5 in 1 discipline*/
                case 9 -> printSwimmersByCoach(employee, singleTonDatabase);/* Prints all members for specific coach */
                case 10 -> createCoach(employee, singleTonDatabase, SingletonUI.getInstance());/* Create a coach and add them to coach list. */
                case 11 -> deleteCoach(employee, singleTonDatabase, SingletonUI.getInstance());/* Delete Coach */
                case 12 -> printEco(employee, singleTonDatabase); /* Print Club Economy */
                case 0 -> isSignedIn = logOut(); /* Logs you out of the system */
                default -> SingletonUI.getInstance().printLn("Incorrect choice. Chose another option.\n");
            } // End of switch statement
        } // End of while loop
    } // End of method


    /*
     * Prints all the attributes of the menu
     */
    private void printMenu() {
        SingletonUI.getInstance().printLn(menuHeader);
        SingletonUI.getInstance().printLn(leadText);
        for (String menuOption : menuOptions) {
            SingletonUI.getInstance().printLn(menuOption);
        } // End of for loop
    } // End of method


    /*
     * This method adds a member through the Chairman class
     * Only Employee Privilege level of ADMINISTRATOR can use this method (Chairman class)
     */
    private void addMember(Employee employee, SingletonDatabase database) {
        try {
            if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                ((Chairman) employee).addMember(SingletonUI.getInstance(), ((Chairman) employee).createMember(SingletonUI.getInstance()),
                        database);
                SingletonFileHandler.getInstance().writeToFullMembersList(database.getMemberList());
                SingletonFileHandler.getInstance().writeToSwimmerCoachAssociationFile(database);
                SingletonFileHandler.getInstance().loggingAction(database.getMemberList().get(database.
                        getMemberList().size() - 1).getName() + " is now swimming with DELFINEN.");
            } else {
                SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
                SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Create a new member\".");
            } // End of if / else statement
        } catch (IndexOutOfBoundsException e) {
            SingletonUI.getInstance().printLn("No member was added, since no Coach was available - Please create a new Coach");
        }
    } // End of method


    /*
     * This method finds and deletes a member from the Database memberList
     * Only Employee Privilege level of ADMINISTRATOR can use this method (Chairman class)
     */
    private void deleteMember(Employee employee, SingletonDatabase database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).deleteMember(SingletonUI.getInstance(), database);
            SingletonFileHandler.getInstance().writeToFullMembersList(database.getMemberList());
            SingletonFileHandler.getInstance().loggingAction("Member deleted.");
        } else {
            SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Delete a member\".");
        } // End of if / else statement
    } // End of method


    /*
     * This method prints all members from the database through the Chairman class
     * Only Employee Privilege level of ADMINISTRATOR can use this method (Chairman class)
     */
    private void printAllMembers(Employee employee, SingletonDatabase database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            innerMenuPrintAllMembers(employee, database);
        } else {
            SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
        } // End of if / else statement
    } // End of method


    /*
     * This method prints all members with arrears through the Treasurer class
     * Only Employee Privilege level of ADMINISTRATOR and ECONOMY_MANAGEMENT can use this method (Chairman/Treasurer class)
     */
    private void printMembersInDebt(Employee employee, SingletonDatabase database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMY_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                      // Creates temporary user for admin
                adminOverride.checkMemberArrears(database);         // Runs temporary user intended method
                SingletonFileHandler.getInstance().loggingAction("Members in arrear viewed.");
            } else {
                ((Treasurer) employee).checkMemberArrears(database);    // Runs method as Treasurer
                SingletonFileHandler.getInstance().loggingAction("Members in arrear viewed.");
            } // End of inner if / else statement
        } else {
            SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print members in arrear\".");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method allows for changes within member status, regarding paid status through the Treasurer class
     * Only Employee Privilege level of ADMINISTRATOR and ECONOMY_MANAGEMENT can use this method (Chairman/Treasurer class)
     */
    private void changePayDue(Employee employee, SingletonDatabase database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMY_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                  // Creates temporary user for admin
                adminOverride.setMemberArrears(SingletonDatabase.getSingletonDatabase(), SingletonUI.getInstance()); // Runs temporary user intended method
                SingletonFileHandler.getInstance().writeToFullMembersList(database.getMemberList()); // Writes changes to file
                SingletonFileHandler.getInstance().loggingAction("A members payment status was changed.");
            } else {
                ((Treasurer) employee).setMemberArrears(SingletonDatabase.getSingletonDatabase(), SingletonUI.getInstance());    // Runs method as Treasurer
            } // End of inner if / else statement
            SingletonFileHandler.getInstance().writeToFullMembersList(database.getMemberList()); // Writes changes to file
            SingletonFileHandler.getInstance().loggingAction("A members payment status was changed.");
        } else {
            SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Change a members payment status\".");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method adds swimming result to a member through the Coach class
     * Only Employee Privilege level of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void addSwimResult(Employee employee, SingletonDatabase database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            innerMenuAddSwimResults(employee, database);

        } else {
            SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Add a swimresult\".");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method prints out a specific Swimmers results
     * Only Employee Privilege level of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void printCompetitiveSwimmersResult(Employee employee, SingletonDatabase database) {
        if((database.getMemberList().stream().anyMatch(member ->
                ((CompetitiveSwimmer)member).getSwimmingDisciplineList().stream().anyMatch(
                        swimmingDiscipline -> !swimmingDiscipline.getSwimmingDisciplineResults().isEmpty())))) {

            if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                    employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                innerMenuSwimmerResults(employee, database);
                SingletonFileHandler.getInstance().loggingAction("A competitive swimmers results printed.");
            } else {
                SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
                SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print swimming results\".");
            } // End of outer if / else statement
        } else {
            SingletonUI.getInstance().printLn("You have no swimmers with results available, please add results first");
        }
    } // End of method


    /*
     * This method prints and sorts the top 5 swimmer performances based on inputs from user.
     * The method reads input from user: swimDiscipline and period of time to get results
     * Only Employee Privilege level of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void printTopFiveByDiscipline(Employee employee, SingletonDatabase database) {
        if (database.getMemberList().stream().anyMatch(member ->
                ((CompetitiveSwimmer)member).getSwimmingDisciplineList().stream().anyMatch(
                swimmingDiscipline -> !swimmingDiscipline.getSwimmingDisciplineResults().isEmpty()))) {

            if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                    employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
                SingletonUI.getInstance().printLn("Crawl, Butterfly, Breaststroke, Freestyle, Backcrawl");
                SingletonSuperSorterThreeThousand.getInstance().topFiveSmadderKode(SingletonUI.getInstance().setSwimmingDisciplineType(),
                        SingletonUI.getInstance().setDistance(), database.getSwimmersCoachAssociationList());
                //SuperSorterThreeThousand.getInstance().topFiveMemberResults(UI.getInstance(), database.getSwimmersCoachAssociationList());
                SingletonFileHandler.getInstance().loggingAction("Top 5 athletes was printed.");
            } else {
                SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
                SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print top 5 athletes\".");
            } // End of outer if / else statement
        } else {
            SingletonUI.getInstance().printLn("You have no swimmers with results available, please add results first");
        }
    } //End of method


    /*
     * This method prints all the members associated for a specific coach
     * Only Employee Privilege level of ADMINISTRATOR and COMPETITIVE_SWIMMER_MANAGEMENT can use this method (Chairman/Coach class)
     */
    private void printSwimmersByCoach(Employee employee, SingletonDatabase database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                // This method makes admin take role of an existing coach, to print his members out
                Coach adminOverride = ((Chairman) employee).chooseCoach(SingletonUI.getInstance(), database, false);
                adminOverride.findMembersOfCoach(database, adminOverride); // Runs method as temporary user
                SingletonFileHandler.getInstance().loggingAction("Swimmers with coach association viewed.");
            } else {
                ((Coach) employee).findMembersOfCoach(database, ((Coach) employee)); // Runs method as coach
                SingletonFileHandler.getInstance().loggingAction("Swimmers with coach association viewed.");
            } // End of inner if / else statement
        } else {
            SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print swimmer associated with coach\".");
        } // End of outer if / else statement
    } // End of method


    /*
     * This method allows chairman to create a coach with login credentials, so he can be a user of the system
     */
    private void createCoach(Employee employee, SingletonDatabase database, SingletonUI ui) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            ((Chairman) employee).createCoach(database, ui);
            SingletonFileHandler.getInstance().writeToCoachList(database.getCoachList());
            SingletonFileHandler.getInstance().writeCoachUserAndPassToList(
                    database.getCoachList().get(database.getCoachList().size() - 1).getUsername(),
                    database.getCoachList().get(database.getCoachList().size() - 1).getPassword());

            SingletonFileHandler.getInstance().loggingAction("A new coach added.");
        } else {
            ui.printLn("You don't have the privilege to use this function");
            SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Create coach\".");
        } // End of if / else statement
    } // End of method

    private void deleteCoach(Employee employee, SingletonDatabase database, SingletonUI ui) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
            ui.printLn("Write the name of the coach you would like to remove:");
            String findCoach = ui.readLine();
            if (database.getCoachList().stream()
                    .anyMatch(coach ->( coach.getName().equalsIgnoreCase(findCoach))
                            && database.getCoachList().size() > 1
                            && database.getCoachList().stream()
                            .anyMatch(coach1 -> coach.getMemberAmountForCoach(database,coach) <= 20))) {

                ui.printLn("Write the username for the coach:");
                String coachUsername = ui.readLine();

                ((Chairman) employee).deleteCoach(findCoach, database, ui);
                SingletonFileHandler.getInstance().deleteCoachLoginFromFile(coachUsername);
                SingletonFileHandler.getInstance().writeToCoachList(database.getCoachList());
                SingletonFileHandler.getInstance().writeToSwimmerCoachAssociationFile(database);
                SingletonFileHandler.getInstance().loggingAction(findCoach + " is no longer a DELFINEN coach.");
            } else {
                SingletonUI.getInstance().printLn("Action not possible! ");
            }
        } else {
            ui.printLn("You don't have the privilege to use this function");
            SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Delete coach\".");
        } // End of if / else statement
    } // End of method


    private void printEco(Employee employee, SingletonDatabase database) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMY_MANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

            if (employee instanceof Chairman) {
                Treasurer adminOverride = new Treasurer();                  // Creates temporary user for admin
                adminOverride.printEconomyInfo(database); // Runs temporary user intended method
                SingletonFileHandler.getInstance().loggingAction("Economy details viewed.");
            } else {
                ((Treasurer) employee).printEconomyInfo(database);
            } // End of inner if / else statement
            SingletonFileHandler.getInstance().writeToFullMembersList(database.getMemberList()); // Writes changes to file
            SingletonFileHandler.getInstance().loggingAction("Economy details viewed.");
        } else {
            SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
            SingletonFileHandler.getInstance().loggingAction("Unauthorised user tried to access \"Print economy\".");
        } // End of outer if / else statement
    } // End of method


    public void innerMenuSwimmerResults(Employee employee, SingleTonDatabase singleTonDatabase) {
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
            switch (SingletonUI.getInstance().readInt()) {
                case 1 -> {
                    //Method reads input from user: swimDiscipline and period of time to get results
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        adminOverride.checkCompetitorSwimResults(
                                adminOverride.lookupSwimmer(SingletonUI.getInstance(), database)); //Runs temporary user method
                    } else {
                        ((Coach) employee).checkCompetitorSwimResults(
                                ((Coach) employee).lookupSwimmer(SingletonUI.getInstance(), database)); // runs method as Coach
                    } // End of inner if / else statement
                    chooseSortMethod = false;
                    SingletonFileHandler.getInstance().loggingAction("All results of a certain swimmer was viewed.");
                } // End of case 1
                case 2 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SingletonSuperSorterThreeThousand.getInstance().setSortByDate(SingletonUI.getInstance(), SingletonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(SingletonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingletonUI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SingletonSuperSorterThreeThousand.getInstance().setSortByDate(SingletonUI.getInstance(), SingletonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(SingletonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingletonUI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    SingletonFileHandler.getInstance().loggingAction("Results on a certain date for a swimmer was viewed.");
                } // End of case 2
                case 3 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SingletonSuperSorterThreeThousand.getInstance().setSortByDistance(SingletonUI.getInstance(), SingletonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(SingletonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingletonUI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SingletonSuperSorterThreeThousand.getInstance().setSortByDistance(SingletonUI.getInstance(), SingletonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(SingletonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingletonUI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    SingletonFileHandler.getInstance().loggingAction("Results on a certain distance for a swimmer was viewed.");
                } // End of case 3
                case 4 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SingletonSuperSorterThreeThousand.getInstance().setSortByIsCompetitive(SingletonUI.getInstance(), SingletonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(SingletonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingletonUI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SingletonSuperSorterThreeThousand.getInstance().setSortByIsCompetitive(SingletonUI.getInstance(), SingletonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(SingletonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingletonUI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    SingletonFileHandler.getInstance().loggingAction("Results on a certain date for a swimmer was viewed.");
                } // End of case 4
                case 5 -> {
                    if (employee instanceof Chairman) {
                        Coach adminOverride = new Coach();                      // Creates temporary user for admin
                        System.out.println(SingletonSuperSorterThreeThousand.getInstance().setSortByRank(SingletonUI.getInstance(), SingletonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(adminOverride.
                                                lookupSwimmer(SingletonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingletonUI.getInstance().setSwimmingDisciplineType())));
                    } else {
                        System.out.println(SingletonSuperSorterThreeThousand.getInstance().setSortByRank(SingletonUI.getInstance(), SingletonSuperSorterThreeThousand.getInstance().
                                oneSwimmersResultList(((Coach) employee).loadSwimmer(SingletonUI.getInstance(), database),
                                        database.getSwimmersCoachAssociationList(),
                                        SingletonUI.getInstance().setSwimmingDisciplineType())));
                    } // End of if / else statement
                    chooseSortMethod = false;
                    SingletonFileHandler.getInstance().loggingAction("Results of either training or competitiveness, for a swimmer was viewed.");
                } // End of case 5
            } // End of switch case
        } // End of while loop
    } // End of method


    private void innerMenuPrintAllMembers(Employee employee, SingletonDatabase database) {
        boolean chooseSortMethod = !database.getMemberList().isEmpty();
        MenuRun innerMenu = new MenuRun("SORTING OPTIONS", "\u001B[1mChose an option:\u001B[0m", new String[]{
                "1. Sort by Name",
                "2. Sort by Age",
                "3. Sort by ID",
                "4. Sort by Phone Number",
                "0. Back to Head Menu"
        });

        while (chooseSortMethod) {
            innerMenu.printMenu();
            switch (SingletonUI.getInstance().readInt()) {
                case 1 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SingletonSuperSorterThreeThousand.getInstance()
                                .setSortByMemberName(database.getMemberList()));
                        SingletonFileHandler.getInstance()
                                .loggingAction("All members was viewed, sorted by Name.");
                    } else {
                        SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
                        SingletonFileHandler.getInstance()
                                .loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 1
                case 2 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SingletonSuperSorterThreeThousand.getInstance()
                                .setSortByMemberAge(database.getMemberList()));
                        SingletonFileHandler.getInstance().loggingAction("All members was viewed, sorted by Age.");
                    } else {
                        SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
                        SingletonFileHandler.getInstance()
                                .loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 2
                case 3 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SingletonSuperSorterThreeThousand.getInstance()
                                .setSortByMemberID(database.getMemberList()));
                        SingletonFileHandler.getInstance()
                                .loggingAction("All members was viewed, sorted by ID.");
                    } else {
                        SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
                        SingletonFileHandler.getInstance()
                                .loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 3
                case 4 -> {
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        ((Chairman) employee).printMembers(SingletonSuperSorterThreeThousand.getInstance()
                                .setSortByMemberPhoneNumber(database.getMemberList()));
                        SingletonFileHandler.getInstance()
                                .loggingAction("All members was viewed, sorted by Phone number.");
                    } else {
                        SingletonUI.getInstance().printLn("You don't have the privilege to use this function");
                        SingletonFileHandler.getInstance()
                                .loggingAction("Unauthorised user tried to access \"Print a list of all members\".");
                    } // End of if / else statement
                    chooseSortMethod = false;
                } // End of case 4
                case 0 -> {
                    SingletonUI.getInstance().printLn("Returning to Head Menu");
                    chooseSortMethod = false;
                } // End of case 0
                default -> SingletonUI.getInstance().printLn("Invalid input, please try again"); // ENd of default case
            } // End of switch case
        } // End of while loop
    } // End of method


    private void innerMenuAddSwimResults(Employee employee, SingletonDatabase database) {
        MenuRun innerMenu = new MenuRun("\u001B[1mADD SWIMRESULTS\u001B[0m", "Which team is swimmer on?", new String[]{
                "Choose either: ",
                "1. Junior Team",
                "2. Adult Team",
                "3. See all your members",
                "0. Return to Head Menu"
        }); // End of innerMenu creation


        boolean chooseTeam = true;
        while (chooseTeam) {
            innerMenu.printMenu();
            int readInput = SingletonUI.getInstance().readInt();
            switch (readInput) {
                case 1, 2, 3 -> {
                    if (employee instanceof Chairman) {
                        try {
                            SingletonUI.getInstance()
                                    .printLn("As chairman, please enter the coach name, you wish to see respective members of");
                            Coach adminOverride = ((Chairman) employee).chooseCoach(SingletonUI.getInstance(), database, false); // Creates temporary user for admin
                            SingletonSuperSorterThreeThousand.getInstance().setSortByTeam(readInput, adminOverride, database.getSwimmersCoachAssociationList());
                            CompetitiveSwimmer swimmer = adminOverride.loadSwimmer(SingletonUI.getInstance(), database);

                            if (database.getSwimmersCoachAssociationList().entrySet().stream().anyMatch(memberCoachEntry ->
                                    memberCoachEntry.getKey().equals(swimmer) && memberCoachEntry.getValue().equals(adminOverride))) {

                                swimmer.getSwimmingDisciplineList().forEach(swimmingDiscipline -> SingletonUI.getInstance().print(" | " + swimmingDiscipline.getSwimmingDisciplineType()));
                                SingletonUI.getInstance().printLn(" | ");
                                SwimmingDiscipline.SwimmingDisciplineTypes disciplineType = SingletonUI.getInstance().setSwimmingDisciplineType();
                                adminOverride.addSwimResult(SingletonUI.getInstance(), swimmer, disciplineType); // Runs temporary user method
                                SingletonFileHandler.getInstance().appendResult(database.getSwimmersCoachAssociationList(), swimmer,
                                        disciplineType);
                                SingletonFileHandler.getInstance().loggingAction("A swim result was added.");
                            } else {
                                SingletonUI.getInstance().printLn("\nYou have no access to other coaches swimmers");
                            }
                        } catch (NullPointerException e) {
                            SingletonUI.getInstance().printLn("Swimmer does not exist");
                        } // End of try catch statement

                    } else {

                        try {
                            SingletonSuperSorterThreeThousand.getInstance().setSortByTeam(readInput, ((Coach) employee), database.getSwimmersCoachAssociationList());
                            CompetitiveSwimmer swimmer = ((Coach) employee).loadSwimmer(SingletonUI.getInstance(), database);

                            if (database.getSwimmersCoachAssociationList().entrySet().stream().anyMatch(memberCoachEntry ->
                                    memberCoachEntry.getKey().equals(swimmer) && memberCoachEntry.getValue().equals(employee))) {

                                swimmer.getSwimmingDisciplineList().forEach(swimmingDiscipline -> SingletonUI.getInstance().print(" | " + swimmingDiscipline.getSwimmingDisciplineType()));
                                SingletonUI.getInstance().printLn(" | ");
                                SwimmingDiscipline.SwimmingDisciplineTypes disciplineType = SingletonUI.getInstance().setSwimmingDisciplineType();
                                ((Coach) employee).addSwimResult(SingletonUI.getInstance(), swimmer, disciplineType); // Runs method as Coach
                                SingletonFileHandler.getInstance().appendResult(database.getSwimmersCoachAssociationList(), swimmer,
                                        disciplineType);
                                SingletonFileHandler.getInstance().loggingAction("A swim result was added.");
                            } else {
                                SingletonUI.getInstance().printLn("\nYou have no access to other coaches swimmers");
                            }
                        } catch (NullPointerException e) {
                            SingletonUI.getInstance().printLn("Swimmer does not exist");

                        } // End of try / catch statement
                    } // End of inner if / else statement

                    chooseTeam = false;
                }
                case 0 -> {
                    SingletonUI.getInstance().printLn("Returning to Head Menu");
                    chooseTeam = false;
                }
                default -> SingletonUI.getInstance().printLn("Not a valid option");
            } // End of switch statement
        } // End of while loop
    } // End of method


    /*
     * This method will log out the user and terminate the program
     */
    private boolean logOut() {
        SingletonUI.getInstance().printLn("Until next time!");
        SingletonFileHandler.getInstance().loggingAction("Program terminated.");
        System.exit(0);
        return false;
    } // End of Method
} // End of Class