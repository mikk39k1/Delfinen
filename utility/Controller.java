package utility;

public class Controller {
    UI ui = new UI();
    FileHandler fileHandler = new FileHandler();

    // loginstuff ----------------------------------

    public String isLoggedIn() {
        String username = fileHandler.loadUsername(getUsername());

        if (!username.equals("0")) {
            for (int i = 1; i < 4; i++) {

                if (isPasswordCorrect(getPassword())) {

                    System.out.println("You're signed in");
                    return username;
                } else if (i != 3) {
                    System.out.println("You have " + (3 - i) + ((3 - i > 1) ? " tries " : " try " + "left."));
                }
            }
        } else {
            System.out.println("Invalid username");
        }
        return "0";
    }


    public String getUsername() {
        System.out.println("Please enter your username");
        return ui.readLine();
    }

    public String getPassword() {
        System.out.println("Please enter your password");
        return ui.readLine();
    }

    private boolean isPasswordCorrect(String password) {
        return !fileHandler.checkPassword(password).equals("0");
    }
}
