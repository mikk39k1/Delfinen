package utility;

import actors.CompetitiveSwimmer;
import actors.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private final File memberArrayListFile = new File("files/fullMembersList.txt");
    private PrintStream printMemberArrayListFile;
    private Scanner readMemberArrayListFile;

    public String checkUsername(String username) {//reads from passwd file and gets usernames
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

    public String checkPassword(String password) {//reads from passwd file and gets password
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

    public void writeToFullMembersList(ArrayList<Member> swimmers) {
        try {
            printMemberArrayListFile = new PrintStream(memberArrayListFile);
            for (Member swimmer : swimmers) {
                if (swimmer instanceof CompetitiveSwimmer)
                    printMemberArrayListFile.print(swimmer.getUniqueID() + " ");
                    printMemberArrayListFile.print(swimmer.getName() + " ");
                    printMemberArrayListFile.print(swimmer.getPhoneNumber() + " ");
                    printMemberArrayListFile.print(swimmer.getAge() + " ");
                    printMemberArrayListFile.print(swimmer.getName() + " ");

            }

        } catch (FileNotFoundException e) {
            System.out.println("Noget gik galt ej mulig at loade medlemslisten");
        }
    }
}