package utility;

import java.io.File;
import java.util.Scanner;

public class FileHandler {

    public String checkUsername(String username) {
        try {
            Scanner readLoginCredentials = new Scanner(new File("files/passwd.txt"));

            while (readLoginCredentials.hasNextLine()) {
                String loadedUsername = readLoginCredentials.next();
                if (username.equals(loadedUsername)) {
                    readLoginCredentials.close();
                    return loadedUsername;
                } else {
                    readLoginCredentials.next();
                }
            }

        } catch (Exception e) {
            System.out.println("File doesn't exist\n");
        }
        System.out.println("Username doesn't exist\n");
        return "0";
    }

    public String checkPassword(String password) {
        try {
            Scanner readLoginCredentials = new Scanner(new File("files/passwd.txt"));

            while (readLoginCredentials.hasNextLine()) {
                String loadedPassword = readLoginCredentials.next();
                if (loadedPassword.equals(password)) {
                    readLoginCredentials.close();
                    return loadedPassword;
                }
            }
        } catch (Exception e) {
            System.out.println("File doesn't exist\n");
        }
        System.out.println("Password is incorrect  ");
        return "0";
    }
}