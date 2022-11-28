package utility;

import actors.*;
import database.Database;

import java.util.ArrayList;

public class SystemBoot {

    // Utility / Controller ------------------
    UI ui = new UI();
    public FileHandler fileHandler = new FileHandler();
    private Employee currentUser;
    ArrayList<Employee> enigmaUsers = new ArrayList<>();
    Database swimmerCoachDatabase = new Database();

    // Setter -----------------------------------
    private void setRoleAndPrivilege(String username) {
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
                swimmerCoachDatabase.getCoachList().add((Coach) user);
            }
        }
    }

    private void loginSystem() {
        String user = isLoggedIn();
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
                    // Slet en burger
                    "2. Udprint af alle eksisterende medlemmer.",
                    "3. Oversigt over medlemmer i restance.",
                    "4. Tilføj nyt svømmeresultat.",
                    "5. Se svømme resultater", // vælge om se alle eller en enkeltsvømmer
                    "6. Oversigt over top 5 konkurrerende svømmere for en given svømmedisciplin.", // Forskellige sort typer,
                    "8. Oversigt over alle members for en coach",
                    "9. Log ud."
            },currentUser, swimmerCoachDatabase);

        }
    }

    public static void main(String[] args) {
        new SystemBoot().startSystem();
    }


    // loginStuff ----------------------------------
    public String isLoggedIn() {
        String username = fileHandler.checkUsername(getUsername());

        if (!username.equals("0")) {
            for (int i = 1; i < 4; i++) {

                if (isPasswordCorrect(getPassword())) {

                    System.out.println("You're signed in");
                    return username;
                } else if (i != 3) {
                    System.out.println("you have " + (3 - i) + ((3 - i > 1) ? " tries left\n" : " try left\n"));
                }
            }
        }
        return "0";
    }

    private String getUsername() {
        System.out.print("Please enter your username: ");
        return ui.readLine();
    }

    private String getPassword() {
        System.out.print("Please enter your password: ");
        return ui.readLine();
    }

    private boolean isPasswordCorrect(String password) {
        return !fileHandler.checkPassword(password).equals("0");
    }
}
