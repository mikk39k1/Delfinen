package utility;

import actors.*;
import utility.member_comparators.SortByMemberAge;
import utility.member_comparators.SortByMemberID;
import utility.member_comparators.SortByMemberName;
import utility.member_comparators.SortByMemberPhoneNumber;
import utility.result_comparators.SortByTime;

import java.time.LocalDate;
import java.util.*;

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
                memberCoachEntry -> ((CompetitiveSwimmer) memberCoachEntry).getSwimmingDisciplineList().forEach(
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
                        ((CompetitiveSwimmer) member).getSwimmingDisciplineList().forEach(
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
            case 200 -> {
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getDistance() != 200);
                swimmingResults.sort(sortByTime);
            } // End of case 200
            case 500 -> {
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

        switch (ui.setRank()) {
            case 3 -> {
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getRank() > 3);
                swimmingResults.sort(sortByTime);
            } // End of case 100
            case 5 -> {
                swimmingResults.removeIf(swimmingResult -> swimmingResult.getRank() > 5);
                swimmingResults.sort(sortByTime);
            } // End of case 200
            case 10 -> {
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
                    member.getUniqueID(), member.getName(),
                    member.getDateOfBirth(), member.getPhoneNumber(), member.isIsMembershipActive());
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
    /*This method takes in hashmap that contain coach and competitive swimmer relation.
    It collects the competitive members, and adds their name, uniqueID and their best swim result for the given
    swim disciplin and distance.
     */
    public void topFiveSmadder(SwimmingDiscipline.SwimmingDisciplineTypes swimDiscipline,
                               int readInputDistance, HashMap<Member, Coach> memberCoachHashMap) {
        ArrayList<Member> swimmersLocalList = new ArrayList<>(memberCoachHashMap.keySet());//converting input hashmap to arraylist
        ArrayList<SwimmingResult> list = new ArrayList<>();//this is for sorting
        HashMap<String, SwimmingResult> membersResults = new HashMap<>(); //new hashmap for storing given swimmer with relevant results
        LinkedHashMap<String, SwimmingResult> sortedMap = new LinkedHashMap<>();//this is for sorting
        List<SwimmingResult> fixedList;//list that gets sorted may contain more than 5 swimmers this arraylist takes a sublist from list
        ArrayList<SwimmingResult> swimmersResults = new ArrayList<>();//contains the swimresults equal to discipline and distance for each swimmer while looping

        for (Member swimmer : swimmersLocalList) {
            for (int i = 0; i < ((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().size(); i++) {
                if (((CompetitiveSwimmer) swimmer).getSwimmingDisciplineList().get(i).
                        getSwimmingDisciplineType().equals(swimDiscipline)) {
                    swimmersResults.clear();
                    for (int x = 0; x < ((CompetitiveSwimmer) swimmer).
                            getSwimmingDisciplineList().get(i).getSwimmingDisciplineResults().size(); x++) {
                        if (((CompetitiveSwimmer) swimmer).
                                getSwimmingDisciplineList().get(i).
                                getSwimmingDisciplineResults().get(x).getDistance() == readInputDistance) {
                            swimmersResults.add(((CompetitiveSwimmer) swimmer).
                                    getSwimmingDisciplineList().get(i).getSwimmingDisciplineResults().get(x));
                        }
                    }
                    if (!swimmersResults.isEmpty()) {
                        swimmersResults.sort(sortByTime);
                        membersResults.put(swimmer.getUniqueID() + " " + swimmer.getName(), swimmersResults.get(0));
                    }
                }
            }
        }
        for (Map.Entry<String, SwimmingResult> entry : membersResults.entrySet()){
            list.add(entry.getValue());
        }
        Collections.sort(list, sortByTime);
        if(list.size() >= 5) {
            fixedList = list.subList(0, 5);
        }else {
            fixedList = list.subList(0, list.size()-1);
        }

        for (SwimmingResult swimmingResult : fixedList) {
            for(Map.Entry<String, SwimmingResult> entry : membersResults.entrySet()){
                if (entry.getValue().equals(swimmingResult)){
                    sortedMap.put(entry.getKey(), swimmingResult);
                }
            }
        }

        sortedMap.forEach((member, results) -> {
            System.out.print(member + " ");
            results.printResults();
        });
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
        listOfSort.forEach(member -> ((CompetitiveSwimmer) member).getSwimmingDisciplineList().forEach(
                swimmingDiscipline -> {
                    swimmingResultArrayList.addAll(swimmingDiscipline.getSwimmingDisciplineResults());
                }
        ));
        swimmingResultArrayList.sort(sortByTime);
        System.out.println(swimmingResultArrayList);
    }



    public void topFiveMemberResults(UI ui, HashMap<Member, Coach> memberCoachHashMap) {
        HashMap<SwimmingResult, Member> allResults = new HashMap<>();
        Set<Member> members = new HashSet<>();
        List<Map.Entry<SwimmingResult, Member>> uniqueMemberEntriesOnly = new ArrayList<>();

        memberCoachHashMap.keySet().forEach(member -> ((CompetitiveSwimmer) member).getSwimmingDisciplineList().forEach(
                swimmingDiscipline ->  swimmingDiscipline.getSwimmingDisciplineResults().forEach(
                        swimmingResult -> allResults.put(swimmingResult,member))));

        int distance = ui.setDistance();
        SwimmingDiscipline.SwimmingDisciplineTypes discipline = ui.setSwimmingDisciplineType();
        boolean isCompetitive = ui.setCompetitiveness();

        List<Map.Entry<SwimmingResult, Member>> list = new LinkedList<>(allResults.entrySet());
        list.sort(Comparator.comparingInt(o -> o.getKey().getSwimTime()));

        list.removeIf(swimmingResultMemberEntry -> swimmingResultMemberEntry.getKey().getDistance() != distance);
        list.removeIf(swimmingResultMemberEntry ->
                ((CompetitiveSwimmer)swimmingResultMemberEntry.getValue()).getSwimmingDisciplineList().removeIf(swimmingDiscipline ->
                        swimmingDiscipline.getSwimmingDisciplineType().equals(discipline)));
        list.removeIf((swimmingResultMemberEntry -> swimmingResultMemberEntry.getKey().isCompetitive() != isCompetitive));

        list.forEach(swimmingResultMemberEntry -> {
            if (!members.contains(swimmingResultMemberEntry.getValue())) {
                members.add(swimmingResultMemberEntry.getValue());
                uniqueMemberEntriesOnly.add(swimmingResultMemberEntry);
            }});

        uniqueMemberEntriesOnly.stream().limit(5).forEach(
                swimmingResultMemberEntry -> {
                    ui.printLn(swimmingResultMemberEntry.getValue().getName() + " - ID: " + swimmingResultMemberEntry.getValue().getUniqueID());
                    swimmingResultMemberEntry.getKey().printResults();
                    ui.printLn("");
                }
        );
    }

} // End of class


// End of class