package utility;

import database.MemberList;

public class Controller {
    UI ui = new UI();
    public FileHandler fileHandler = new FileHandler();

    MemberList memberList = new MemberList();


    // loginstuff ----------------------------------

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
