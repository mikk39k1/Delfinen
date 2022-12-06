package utility;

import actors.*;
import utility.member_comparators.SortByMemberAge;
import utility.member_comparators.SortByMemberID;
import utility.member_comparators.SortByMemberName;
import utility.member_comparators.SortByMemberPhoneNumber;
import utility.result_comparators.SortByTime;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SuperSorterThreeThousand {
    // Member SORT SECTION ----------------------------------------------------------
    private final SortByMemberAge sortByMemberAge = new SortByMemberAge();
    private final SortByMemberID sortByMemberID = new SortByMemberID();
    private final SortByMemberName sortByMemberName = new SortByMemberName();
    private final SortByMemberPhoneNumber sortByMemberPhoneNumber = new SortByMemberPhoneNumber();
    private final SortByTime sortByTime = new SortByTime();    // We will always sort by Time before presenting results

    protected List<Member> setSortByMemberAge(ArrayList<Member> membersList) {
        ((List<Member>) membersList).sort(sortByMemberAge);
        return membersList;
    }

    protected List<Member> setSortByMemberID(ArrayList<Member> membersList) {
        ((List<Member>) membersList).sort(sortByMemberID);
        return membersList;
    }

    protected List<Member> setSortByMemberName(ArrayList<Member> membersList) {
        ((List<Member>) membersList).sort(sortByMemberName);
        return membersList;
    }

    protected List<Member> setSortByMemberPhoneNumber(ArrayList<Member> membersList) {
        ((List<Member>) membersList).sort(sortByMemberPhoneNumber);
        return membersList;
    }


    // Result SORT SECTION ----------------------------------------------------------
    /*
     * This method gathers all results from all swimmers from AssociationList, then returns them as an ArrayList
     */
    public ArrayList<SwimmingResult> getAllSwimmingResults(
            HashMap<Member, Coach> memberCoachHashMap,
            SwimmingDiscipline.SwimmingDisciplineTypes swimType) {

        ArrayList<SwimmingResult> swimResultList = new ArrayList<>();

        memberCoachHashMap.keySet().forEach(
                memberCoachEntry -> ((CompetitiveSwimmer)memberCoachEntry).getSwimmingDisciplineList().forEach(
                        swimmingDiscipline -> {
                            if (swimmingDiscipline.getSwimmingDisciplineType().equals(swimType)) {
                                swimResultList.addAll(swimmingDiscipline.getSwimmingDisciplineResults());
                            } // End of if statement
                        } // End of lambda -> predicate method entrance statement
                )// End of forEach lambda -> predicate method entrance statement
        ); // End of ArrayList, build in forEach method
        return swimResultList;
    } // End of method


    /*
    * This method gathers all results from one specific swimmer and returns them as an ArrayList
     */
    protected ArrayList<SwimmingResult> oneSwimmersResultList(
            CompetitiveSwimmer swimmer,
            HashMap<Member, Coach> memberCoachHashMap,
            SwimmingDiscipline.SwimmingDisciplineTypes swimType) {

        ArrayList<SwimmingResult> swimResultList = new ArrayList<>();

        memberCoachHashMap.keySet().forEach(
                member -> {
                    if (member.getName().equals(swimmer.getName())) {
                        ((CompetitiveSwimmer)member).getSwimmingDisciplineList().forEach(
                                swimmingDiscipline -> {
                                    if (swimmingDiscipline.getSwimmingDisciplineType().equals(swimType)) {
                                        swimResultList.addAll(swimmingDiscipline.getSwimmingDisciplineResults());
                                    }
                                }
                        );
                    }
                }
        );
        return swimResultList;
    } // End of method



    /*
    * This method sorts based on distance, look inside ui.class to understand properties being presented
     */
    public ArrayList<SwimmingResult> setSortByDistance(UI ui, ArrayList<SwimmingResult> swimmingResults) {
        switch (ui.setDistance()) {
            case 100 -> {
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDistance() != 100);
                swimmingResults.sort(sortByTime);
            } // End of case 100
            case 200 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDistance() != 200);
                swimmingResults.sort(sortByTime);
            } // End of case 200
            case 500 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDistance() != 500);
                swimmingResults.sort(sortByTime);
            } // End of case 500
        } // End of switch statement
        return swimmingResults;
    } // End of method


    /*
    * This method sorts results based on competitiveness. True = Competition / False = Training session
     */
    protected ArrayList<SwimmingResult> setSortByIsCompetitive(UI ui, ArrayList<SwimmingResult> swimmingResults) {

        if (!ui.setCompetitiveness()) {
            swimmingResults.removeIf(SwimmingResult::isCompetitive);
            swimmingResults.sort(sortByTime);
        } else {
            swimmingResults.removeIf(swimmingResult -> !swimmingResult.isCompetitive());
            swimmingResults.sort(sortByTime);
        } // End of if / else statement
        return swimmingResults;
    } // End of method



    /*
    * This method sorts results based on Rank. Read more in ui.class defining return values with info.
     */
    protected ArrayList<SwimmingResult> setSortByRank(UI ui, ArrayList<SwimmingResult> swimmingResults) {

        switch(ui.setRank()) {
            case 3 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getRank() > 3);
                swimmingResults.sort(sortByTime);
            } // End of case 100
            case 5 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getRank() > 5);
                swimmingResults.sort(sortByTime);
            } // End of case 200
            case 10 ->{
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getRank() > 10);
                swimmingResults.sort(sortByTime);
            } // End of case 500
        } // End of switch statement
        return swimmingResults;
    } // End of method


    /*
    * This method sorts results based on date attributes.
     */
    protected ArrayList<SwimmingResult> setSortByDate(UI ui, ArrayList<SwimmingResult> swimmingResults) {

        switch (ui.chooseTimeFrame()) {
            case 1 -> {
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDate().isBefore(LocalDate.now().minusMonths(3)));
                swimmingResults.sort(sortByTime);
            } // End of case 1
            case 2 -> {
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDate().isBefore(LocalDate.now().minusMonths(6)));
                swimmingResults.sort(sortByTime);
            } // End of case 2
            case 3 -> {
                swimmingResults.removeIf(swimmingResult -> !(swimmingResult.getDate().getYear() == LocalDate.now().getYear()));
                swimmingResults.sort(sortByTime);
            } // End of case 3
            case 4 -> {
                int specificYear = LocalDate.now().getYear() - ui.readYear();
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDate().isBefore(LocalDate.now().minusYears(specificYear)));
                swimmingResults.sort(sortByTime);
            } // End of case 4
            case 5 -> {
                int specificYear = LocalDate.now().getYear() - ui.readYear();
                swimmingResults.removeIf(swimmingResult -> !swimmingResult.getDate().isEqual(LocalDate.now().minusYears(specificYear)));
                swimmingResults.sort(sortByTime);
            } // End of case 5
            case 6 -> {
                LocalDate date = ui.setDate();
                swimmingResults.removeIf(swimmingResult -> !swimmingResult.getDate().isEqual(date));
                swimmingResults.sort(sortByTime);
            } // End of case 6
        } // End of switch statement
        return swimmingResults;
    } // End of method



    protected void setSortByTeam(int readInput, Coach coach, HashMap<Member, Coach> memberCoachHashMap) {
        HashMap<Member, Coach> temporaryMemberCoachHashMap = new HashMap<>(memberCoachHashMap);
        ArrayList<Member> members = new ArrayList<>();
        switch (readInput) {
            case 1 -> {
                temporaryMemberCoachHashMap.keySet().removeIf(member -> member.getAge() > 18);
                temporaryMemberCoachHashMap.values().removeIf(coachValues -> !coachValues.getName().equals(coach.getName()));
                members = new ArrayList<>(temporaryMemberCoachHashMap.keySet());
                members.sort(sortByMemberName);
            } // End of case 1
            case 2 -> {
                temporaryMemberCoachHashMap.keySet().removeIf(member -> member.getAge() < 18);
                temporaryMemberCoachHashMap.values().removeIf(coachValues -> !coachValues.getName().equals(coach.getName()));
                members = new ArrayList<>(temporaryMemberCoachHashMap.keySet());
                members.sort(sortByMemberName);
            } // End of case 2
            case 3 -> {
                temporaryMemberCoachHashMap.values().removeIf(coachValues -> !coachValues.getName().equals(coach.getName()));
                members = new ArrayList<>(temporaryMemberCoachHashMap.keySet());
                members.sort(sortByMemberName);
            } // End of case 3
        } // End of switch statement
        for (Member member : members) {
            System.out.printf("%nID: %-8d Name: %-30s Date of Birth: %-15s Tel: %-15s Membership Status: %-10b",
                    member.getUniqueID(),member.getName(),
                    member.getDateOfBirth(),member.getPhoneNumber(), member.isIsMembershipActive());
        } // End of for loop
    } // End of method

    public void topFiveBestPerformance(SwimmingDiscipline.SwimmingDisciplineTypes swimDiscipline,
                                       int readInputDistance, HashMap<Member, Coach> memberCoachHashMap) {
        ArrayList<Member> temporarySwimmers = new ArrayList<>(memberCoachHashMap.keySet());

        temporarySwimmers.forEach(member -> ((CompetitiveSwimmer) member).getSwimmingDisciplineList().forEach(swimmingDiscipline ->
                {
                    if (((CompetitiveSwimmer) member).getSwimmingDisciplineList().contains(swimmingDiscipline)) {
                    }
                })
        );


        temporarySwimmers.forEach(member -> ((CompetitiveSwimmer) member).getSwimmingDisciplineList().forEach(swimmingDiscipline ->
                swimmingDiscipline.getSwimmingDisciplineResults().sort(sortByTime)));


        temporarySwimmers.forEach(member -> ((CompetitiveSwimmer) member).getSwimmingDisciplineList().forEach(swimmingDiscipline ->
                System.out.println(swimmingDiscipline.getSwimmingDisciplineResults())));

    }

    public void topFiveSmadder(SwimmingDiscipline.SwimmingDisciplineTypes swimDiscipline,
                               int readInputDistance, HashMap<Member, Coach> memberCoachHashMap) {
        ArrayList<Member> swimmersLocalList = new ArrayList<>(memberCoachHashMap.keySet());
        for (Member swimmer : swimmersLocalList) {
            int count = 0;
            for (int i = 0; i < ((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().size(); i++) {
                if (!((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().get(i).
                        getSwimmingDisciplineType().equals(swimDiscipline)) {
                    ((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().remove(i);
                    count++;
                }
                if (((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().size() == count){
                    swimmersLocalList.remove(swimmer);
                }
            }
        }

        for (Member swimmer : swimmersLocalList) {
            if (((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().size() > 0) {
                for (int x = 0; x < ((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().
                        get(0).getSwimmingDisciplineResults().size(); x++) {
                    if (((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().get(0).
                            getSwimmingDisciplineResults().get(x).getDistance() != readInputDistance) {
                        ((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().
                                get(0).getSwimmingDisciplineResults().remove(x);
                    }
                }
            }
        }

        for (Member swimmer : swimmersLocalList) {
            if (((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().size() > 0) {
                System.out.println(((CompetitiveSwimmer) swimmer).getName() + " " +
                        ((CompetitiveSwimmer) swimmer).
                                getSwimmingDisciplineList().get(0).getSwimmingDisciplineResults().get(0).getSwimTime() + " "
                        + ((CompetitiveSwimmer) swimmer).
                        getSwimmingDisciplineList().get(0).getSwimmingDisciplineType().toString());
            }
        }
    }

    public void topFiveSituation(UI ui, HashMap<Member, Coach> memberCoachHashMap) {
        ArrayList<SwimmingResult> swimmingResultArrayList = new ArrayList<>();

        int chooseDistance = ui.setDistance();
        SwimmingDiscipline.SwimmingDisciplineTypes chooseDiscipline = ui.setSwimmingDisciplineType();

        memberCoachHashMap.forEach((key, value) -> ((CompetitiveSwimmer) key).getSwimmingDisciplineList().removeIf(swimmingDiscipline ->
                !swimmingDiscipline.getSwimmingDisciplineType().equals(chooseDiscipline)));

        memberCoachHashMap.forEach((member, coach) -> ((CompetitiveSwimmer) member).getSwimmingDisciplineList().forEach(swimmingDiscipline ->
                swimmingDiscipline.getSwimmingDisciplineResults().removeIf(swimmingResult -> swimmingResult.getDistance() != chooseDistance)));

        ArrayList<Member> listOfSort = new ArrayList<>(memberCoachHashMap.keySet());
        listOfSort.forEach(member -> ((CompetitiveSwimmer)member).getSwimmingDisciplineList().forEach(
                swimmingDiscipline -> {
                    swimmingResultArrayList.addAll(swimmingDiscipline.getSwimmingDisciplineResults());
                }
                ));
        swimmingResultArrayList.sort(sortByTime);
        System.out.println(swimmingResultArrayList);


    }
} // End of class


 // End of class