package utility;

public class Controller {
    UI ui = new UI();

    // loginstuff ----------------------------------
    public void loginToSystem() {
        String username = getUsername();
        if (isPasswordCorrect(username)) {
            System.out.println("You entered a valid username and password, you're now signed in");
        }
    }

    public String getUsername() {
        while (true) {
            System.out.println("Please enter your username");

            return scan
        }
    }

    public boolean isPasswordCorrect(String userName) {
        while (true) {
            System.out.println("Please enter your password");
            switch (userName) {
                case "thomas123":
                    if (scan.nextLine.eguals("swimCoach123")) {
                        set
                        return true;
                    }
                    break;
                case "marry123":
                    if (scan.nextLine.eguals("swimCoach123")) {
                        return true;
                    }
                    break;
                case "Jen123":

                    break;
            }
            return false;
        }
    }
}