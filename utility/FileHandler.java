package utility;

import actors.CompetitiveSwimmer;
import actors.Member;
import actors.SwimmingDiscipline;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileHandler {
    private final File memberArrayListFile = new File("files/fullMembersList.txt");
    private final File memberResultFile = new File("files/results.txt");

    private PrintStream printToFile;
    private PrintStream appendPrintToFile;
    private Scanner readFromFile;

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
            //printToFile = new PrintStream(memberArrayListFile);
            printToFile = new PrintStream(memberArrayListFile);

            for (Member swimmer : swimmers) {
                if (swimmer instanceof CompetitiveSwimmer) {
                    printToFile.print("true");
                } else {
                    appendPrintToFile.print("false");
                }

                printToFile.print(";" + swimmer.getUniqueID());
                printToFile.print(";" + swimmer.getName());
                printToFile.print(";" + swimmer.getPhoneNumber());
                printToFile.print(";" + swimmer.getAge());
                printToFile.print(";" + swimmer.isIsMembershipActive());

                if (swimmer instanceof CompetitiveSwimmer)
                    for (SwimmingDiscipline type : ((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList()) {
                        printToFile.print((";") + (type));
                    }
                printToFile.println("");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Noget gik galt");
        }
    }
    /*public void writeToResults(ArrayList<>){
        for (int i = 0; i < type.getSwimmingDisciplineResults().size(); i++) {
            try {
                appendPrintToFile = new PrintStream(new FileOutputStream(memberResultFile,true));

                appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).getDistance() + ";");
                appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).getDate() + ";");
                appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).getSwimTime() + ";");
                if (type.getSwimmingDisciplineResults().get(i).isCompetitive()) {
                    appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).isCompetitive() + ";");
                    appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).getRank() + ";");
                    appendPrintToFile.println();
                } else {
                    appendPrintToFile.print(type.getSwimmingDisciplineResults().get(i).isCompetitive() + ";");
                    appendPrintToFile.println();
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Ingen resultater at hente");
            }
        }
    } */

/*
    public ArrayList loadMemberList() {
        ArrayList<Member> memberList = new ArrayList<>();
        readMemberArrayListFile = new Scanner(memberArrayListFile);
        while (readMemberArrayListFile.hasNextLine()) {
            String s = readMemberArrayListFile.nextLine();
            String[] arrOfStr = s.split(";");


            return memberList;
        } catch(IOException e){
            e.printStackTrace();
        }
*/
}