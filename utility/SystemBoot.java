package utility;

import actors.*;
import database.SwimmerCoachDatabase;

import java.util.ArrayList;

public class SystemBoot {

    // Utility / Controller ------------------
    Controller controller = new Controller();
    private Employee currentUser;
    ArrayList<Employee> enigmaUsers = new ArrayList<>();
    SwimmerCoachDatabase swimmerCoachDatabase = new SwimmerCoachDatabase();

    MenuRun menuRun = new MenuRun(">>>ENIGMA SOLUTION<<<", "Vælg en af nedenstående muligheder", new String[]{
            "1. Tiljøj et nyt medlem.",
            "2. Udprint af alle eksisterende medlemmer.",
            "3. Oversigt over medlemmer i restance.",
            "4. Tilføj nyt svømmeresultat.",
            "5. Oversigt over en svømmers resultater.",
            "6. Oversigt over top 5 konkurrerende svømmere for en given svømmedisciplin.",
            "9. Log ud."
    },currentUser, controller.ui, swimmerCoachDatabase);



    // Getter ----------------------------------
    public Employee getCurrentUser() {
        return currentUser;
    }

    // Setter -----------------------------------
    public void setRoleAndPrivilege(String username) {
        // Switch statement set role and privilege based on correct username
        for (Employee user : enigmaUsers) {
            if (user.getUsername().equals(username)) {
                currentUser = user;
            }
        }
    }

    private void loadAndSetUsers() {
        // Staff -----------------
        enigmaUsers.add(new Chairman(Employee.RoleType.ADMIN, Employee.PrivilegeType.ADMINISTRATOR));
        enigmaUsers.add(new Treasurer(Employee.RoleType.ACCOUNTANT, Employee.PrivilegeType.ECONOMYMANAGEMENT));
        enigmaUsers.add(new Coach("Thomas", "+45 01 23 58 13", "thomas123"));
        enigmaUsers.add(new Coach("Marry", "+45 01 23 58 13", "Marry123"));
        enigmaUsers.add(new Coach("Jen", "+45 01 23 58 13", "Jen123"));

        // Add coaches to coach list -------------------------------------------
        for (Employee user : enigmaUsers) {
            if (user instanceof Coach) {
                swimmerCoachDatabase.getCoachList().getCoaches().add((Coach) user);
            }
        }
    }
    private void loginSystem() {
        String user = controller.isLoggedIn();
        if (!user.equals("0")) {
            setRoleAndPrivilege(user);

            System.out.println(currentUser.getUsername());
            System.out.println(currentUser.getRole());
            System.out.println(currentUser.getPrivilege());
        }
    }


    private void startSystem() {
        loadAndSetUsers();
        
        while (true) {
            loginSystem();
            new MenuRun(">>>ENIGMA SOLUTION<<<", "Vælg en af nedenstående muligheder", new String[]{
                    "1. Tiljøj et nyt medlem.",
                    "2. Udprint af alle eksisterende medlemmer.",
                    "3. Oversigt over medlemmer i restance.",
                    "4. Tilføj nyt svømmeresultat.",
                    "5. Oversigt over en svømmers resultater.",
                    "6. Oversigt over top 5 konkurrerende svømmere for en given svømmedisciplin.",
                    "9. Log ud."
            },currentUser, controller.ui, swimmerCoachDatabase);
        }
    }

    public static void main(String[] args) {
        new SystemBoot().startSystem();
    }
}
