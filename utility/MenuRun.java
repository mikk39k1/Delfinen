package utility;

import actors.*;
import database.SwimmerCoachDatabase;

import java.text.DateFormat;

public class MenuRun {
    Menu menu;
    private String[] menuOptions = {
            "1. Tiljøj et nyt medlem.",
            "2. Udprint af alle eksisterende medlemmer.",
            "3. Oversigt over medlemmer i restance.",
            "4. Tilføj nyt svømmeresultat.",
            "5. Oversigt over konkurrerende svømmeres resultater.",
            "9. Log ud."
    };
    private String leadtext = "Vælg en af nedenstående muligheder";

    public void menuLooping(Employee employee, UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {
        menu = new Menu("DELFINEN", leadtext, menuOptions);
        boolean isSignedIn = true;

        while (isSignedIn) {
            menu.printMenu();
            int userChoice = ui.readInt();

            switch (userChoice) {

                case 1 -> {//add new member to all members lists
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
                        //Chairman method adding member with parameter - new member which creates member fixes
                        ui.printLn(">> CREATE MEMBER <<");
                        ((Chairman) employee).addMember(ui, ((Chairman) employee).createMember(ui),
                                swimmerCoachDatabase);
                    } else {
                        ui.printLn("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 2 -> {//a print of all members
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

                        ((Coach) employee).addSwimResult(ui, swimmerCoachDatabase);
                    } else {
                        ui.printLn("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 5 -> { //method for printing swimResult for ONE competitor --
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT) ||
                            employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {

                        //Method reads input from user: swimDiscipline and period of time to get results
                        ((Coach) employee).checkCompetitorSwimResults(
                                ((Coach) employee).foundSwimmer(ui, swimmerCoachDatabase));
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

    private void chooseWhichSwimResults(UI ui) {
        ui.printLn("Hvilken svømme disciplin?");
        SwimmingDiscipline swimmingDiscipline = new SwimmingDiscipline(ui);
        ui.printLn("Der sammenlignes resultater fra dags dato til den dato der angives - " +
                "skriv datoen du ønsker sammenlignet fra");
        String date = ui.setDate();//Should probably not be saved as a string but good for now
    }
}
