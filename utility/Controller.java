package utility;

public class Controller {
    UI ui = new UI();

    // loginstuff ----------------------------------
    public void loginToSystem() {
        String username = getUsername();
        if (isPasswordCorrect(username)) {
            System.out.println("You entered a valid username and password, you're now signed in");
        } else
            System.out.println("Wrong username or password");
    }

    public String getUsername() {
        System.out.println("Please enter your username");
        return ui.readLine();
    }

    public boolean isPasswordCorrect(String userName) {
        while (true) {
            System.out.println("Please enter your password");
            switch (userName) {
                case "thomas123":
                    if (ui.readLine().equals("swimCoach123")) {
                        System.out.println("you're");
                        return true;
                    }
                    break;
                case "marry123":
                    if (ui.readLine().equals("swimCoach123")) {
                        return true;
                    }
                    break;
                case "Jen123":
                    if (ui.readLine().equals("swimCoach123")) {
                        return true;
                    }
                    break;
            }
            return false;
        }
    }
}