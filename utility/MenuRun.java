package utility;

import actors.*;
import database.Database;

public class MenuRun {
    UI ui = new UI();
    FileHandler fileHandler = new FileHandler();
    private final String[] menuOptions;
    private final String menuHeader;
    private final String leadtext;

    public MenuRun(String menuHeader,String leadtext ,String[] menuOptions, Employee employee, Database swimmerCoachDatabase) {
        this.menuHeader = menuHeader;
        this.menuOptions = menuOptions;
        this.leadtext = leadtext;
        menuLooping(employee,swimmerCoachDatabase);
    }

    public MenuRun(String menuHeader, String leadtext, String[] menuOptions) {
        this.menuHeader = menuHeader;
        this.menuOptions = menuOptions;
        this.leadtext = leadtext;
    }

    public void menuLooping(Employee employee, Database swimmerCoachDatabase) {
        boolean isSignedIn = true;

        while (isSignedIn) {
            printMenu();
            int userChoice = ui.readInt();

            switch (userChoice) {

                case 1 -> {//add new member to all members lists
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
                        //Chairman method adding member with parameter - new member which creates member fixes
                        ui.printLn(">> CREATE MEMBER <<");
                        ((Chairman) employee).addMember(ui, ((Chairman) employee).createMember(ui),
                                swimmerCoachDatabase);
                        fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList());
                    } else {
                        ui.printLn("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 2 -> {//print all members
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
                        ((Chairman) employee).printMembers(swimmerCoachDatabase);
                    } else {
                        ui.printLn("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 3 -> {//Prints list of members who hasn't paid
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMYMANAGEMENT) ||
                            employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                    } else {
                        ui.printLn("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 4 -> {//add swimResult
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                            employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        if (employee instanceof Chairman) {
                            Coach adminOverride = new Coach();
                            adminOverride.addSwimResult(ui, swimmerCoachDatabase);
                            fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList());

                        } else {
                            ((Coach) employee).addSwimResult(ui, swimmerCoachDatabase);
                            fileHandler.writeToFullMembersList(swimmerCoachDatabase.getMemberList());
                        }

                    } else {
                        ui.printLn("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 5 -> { //method for printing swimResult for ONE competitor --
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                            employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        //Method reads input from user: swimDiscipline and period of time to get results
                        if (employee instanceof Chairman) {
                            Coach adminOverride = new Coach();
                            adminOverride.checkCompetitorSwimResults(
                                    adminOverride.lookupSwimmer(ui, swimmerCoachDatabase));
                        } else {
                            ((Coach) employee).checkCompetitorSwimResults(
                                    ((Coach) employee).lookupSwimmer(ui, swimmerCoachDatabase));
                        }
                    } else {
                        ui.printLn("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 6 -> {//method for printing top 5 results for specific discipline
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                            employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        //Method reads input from user: swimDiscipline and period of time to get results
                        if (employee instanceof Chairman) {
                            Coach adminOverride = new Coach();
                            /*adminOverride.checkCompetitorSwimResults(
                                    adminOverride.foundSwimmer(ui, swimmerCoachDatabase));
                             */
                        } else {
                            ((Coach) employee).checkCompetitorSwimResults(
                                    ((Coach) employee).lookupSwimmer(ui, swimmerCoachDatabase));
                        }
                    } else {
                        ui.printLn("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 8 -> {// this method prints all members for specific coach

                    if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                            employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        //Method reads input from user: swimDiscipline and period of time to get results
                        if (employee instanceof Chairman) {
                            Coach adminOverride = ((Chairman) employee).chooseCoach(ui,swimmerCoachDatabase);
                            adminOverride.findMembersOfCoach(swimmerCoachDatabase, adminOverride);

                        } else {
                            ((Coach) employee).findMembersOfCoach(swimmerCoachDatabase, ((Coach) employee));
                        }
                    } else {
                        ui.printLn("Du har ikke login rettigheder til denne funktion");
                    }

                }
                case 9 -> {
                    ui.printLn("Logger ud");
                    isSignedIn = false;
                }
                default -> ui.printLn("Vælg en eksisterende mulighed.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println(menuHeader);
        System.out.println(leadtext);
        for (String menuOption : menuOptions) {
            System.out.println(menuOption);
        }
    }
}
