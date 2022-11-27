package utility;

import actors.*;
import database.SwimmerCoachDatabase;

import java.text.DateFormat;

public class MenuRun {
    Menu menu;
    private String[] menuOptions = {"1. Tiljøj nyt medlem.", "2. Udprint af alle eksisterende medlemmer.",
            "3. Oversigt over medlemmer i resistance.", "4. Tilføj et nyt svømmeresultat.",
            "5. Oversigt over konkurrerende svømmeres resultater.", "9. Log ud."};
    private String leadtext = "Vælg en af nedenstående muligheder";

    public void menuLooping(Employee employee, UI ui, SwimmerCoachDatabase swimmerCoachDatabase) {
        menu = new Menu("DELFINEN", leadtext, menuOptions);
        boolean isSignedIn = true;

        while (isSignedIn) {
            menu.printMenu();
            int userChoice = ui.readInt();

            switch (userChoice) {

                case 1 -> {//add new member to all members list
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
                        //Chariman methodd addmember with parameter newmember which createmember fixes
                        ((Chairman) employee).addMember(((Chairman) employee).createMember());
                    } else {
                        System.out.println("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 2 -> {//a print of all members
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ADMINISTRATOR)) {
                        //having all members printed goes here
                    } else {
                        System.out.println("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 3 -> {//Prints list of members who hasn't paid
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.ECONOMYMANAGEMENT)) {

                    } else {
                        System.out.println("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 4 -> {//add swimresultat
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT)) {
                        ((Coach) employee).addSwimResult(ui, swimmerCoachDatabase);
                    } else {
                        System.out.println("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 5 -> {//method for printing swimresult
                    if (employee.getPrivilege().equals(Employee.PrivilegeType.COMPETITIVE_SWIMMER_MANAGEMENT)) {
                        //Method reads input from user: swimdisciplin and period of time to get results
                        chooseWhichSwimResults(ui);
                    } else {
                        System.out.println("Du har ikke login rettigheder til denne funktion");
                    }
                }
                case 9 -> {//signing out

                }
                default -> {
                    System.out.println("Vælg en eksisterende mulighed.");
                    System.out.println();
                }
            }
        }
    }

    private void chooseWhichSwimResults(UI ui) {
        System.out.println("Hvilken svømme disciplin?");
        SwimmingDiscipline swimmingDiscipline = new SwimmingDiscipline(ui);
        System.out.println("Der sammenlignes resultater fra dags dato til den dato der angives - " +
                "skriv datoen du ønsker sammenlignet fra");
        String date = ui.setDate();//Should probably not be saved as a string but good for now
    }
}
