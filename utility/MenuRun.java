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
                case 1 -> {addMember(employee,swimmerCoachDatabase);/*add new member to all members lists */ }
                case 2 -> {printAllMembers(employee,swimmerCoachDatabase);/*print all members */ }
                case 3 -> {printMembersInDebt(employee); /*Prints list of members who hasn't paid */}
                case 4 -> {addSwimResult(employee,swimmerCoachDatabase);/*add swimResult*/}
                case 5 -> {printCompetitiveSwimmersResult(employee,swimmerCoachDatabase); /*Prints 1 swimmers results*/}
                case 6 -> {printTopFiveByDiscipline(employee,swimmerCoachDatabase);/*Prints top 5 in 1 discipline*/}
                case 8 -> {printSwimmersByCoach(employee,swimmerCoachDatabase);/*Prints all members for specific coach*/}
                case 9 -> {logOut(isSignedIn); /*Logs you out of the system */}
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

    private void addMember(Employee employee, Database swimmerCoachDatabase){
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
    private void printAllMembers(Employee employee, Database swimmerCoachDatabase) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
        ((Chairman) employee).printMembers(swimmerCoachDatabase);
    } else {
        ui.printLn("Du har ikke login rettigheder til denne funktion");
    }
    }
    private void printMembersInDebt(Employee employee) {
        if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMYMANAGEMENT) ||
                employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
        } else {
            ui.printLn("Du har ikke login rettigheder til denne funktion");
        }
    }
    private void addSwimResult(Employee employee,Database swimmerCoachDatabase){
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
    private void printCompetitiveSwimmersResult(Employee employee, Database swimmerCoachDatabase){
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
    private void printTopFiveByDiscipline(Employee employee, Database swimmerCoachDatabase){
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
    private void printSwimmersByCoach(Employee employee,Database swimmerCoachDatabase){
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
    private boolean logOut(Boolean isSignedIn){
        ui.printLn("Logger ud");
        isSignedIn = false;
        return isSignedIn;
    }
}
