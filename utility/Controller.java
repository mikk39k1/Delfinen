package utility;

public class Controller {
    UI ui = new UI();
    FileHandler fileHandler = new FileHandler();

    // loginstuff ----------------------------------

    public void login() {
        String username = fileHandler.loadUsername(getUsername());
        if (!username.equals("0") && isPasswordCorrect(getPassword())) {

            System.out.println("You're signed in");
        }
    }


    public String getUsername() {
        System.out.println("Please enter your username");
        return ui.readLine();
    }

    public String getPassword() {
        System.out.println("Please enter your password");
        return ui.readLine();
    }

    public boolean isPasswordCorrect(String password) {
        return !fileHandler.loadPassword(password).equals("0");
    }
}
