package utility;

import actors.*;
import utility.member_comparators.SortByMemberAge;
import utility.member_comparators.SortByMemberID;
import utility.member_comparators.SortByMemberName;
import utility.member_comparators.SortByMemberPhoneNumber;
import utility.result_comparators.SortByTime;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SingleTonSuperSorterThreeThousand {

    private static final utility.SingleTonSuperSorterThreeThousand SingleTonSuperSorterThreeThousand = new SingleTonSuperSorterThreeThousand();


    // Constructor -----------------------
    private SingleTonSuperSorterThreeThousand() {
    }

    // Getter ----------------------------
    public static utility.SingleTonSuperSorterThreeThousand getInstance() {
        return SingleTonSuperSorterThreeThousand;
    }

    // Member SORT SECTION ----------------------------------------------------------
    private final SortByMemberAge sortByMemberAge = new SortByMemberAge();
    private final SortByMemberID sortByMemberID = new SortByMemberID();
    private final SortByMemberName sortByMemberName = new SortByMemberName();
    private final SortByMemberPhoneNumber sortByMemberPhoneNumber = new SortByMemberPhoneNumber();
    private final SortByTime sortByTime = new SortByTime();    // We will always sort by Time before presenting results

     List<Member> setSortByMemberAge(ArrayList<Member> membersList) {
        ((List<Member>) membersList).sort(sortByMemberAge);
        return membersList;
    }

     List<Member> setSortByMemberID(ArrayList<Member> membersList) {
        ((List<Member>) membersList).sort(sortByMemberID);
        return membersList;
    }

     List<Member> setSortByMemberName(ArrayList<Member> membersList) {
        ((List<Member>) membersList).sort(sortByMemberName);
        return membersList;
    }

     List<Member> setSortByMemberPhoneNumber(ArrayList<Member> membersList) {
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
     ArrayList<SwimmingResult> oneSwimmersResultList(
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
     ArrayList<SwimmingResult> setSortByDistance(SingleTonUI singleTonUi, ArrayList<SwimmingResult> swimmingResults) {
         ArrayList<SwimmingResult> sortedList = new ArrayList<>();
        switch (singleTonUi.setDistance()) {
            case 100 -> {
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getDistance() == 100).toList());
                sortedList.sort(sortByTime);
            } // End of case 100
            case 200 -> {
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getDistance() == 200).toList());
                sortedList.sort(sortByTime);
            } // End of case 200
            case 500 -> {
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getDistance() == 500).toList());
                sortedList.sort(sortByTime);
            } // End of case 500
        } // End of switch statement
        return sortedList;
    } // End of method


    /*
     * This method sorts results based on competitiveness. True = Competition / False = Training session
     */
     ArrayList<SwimmingResult> setSortByIsCompetitive(SingleTonUI singleTonUi, ArrayList<SwimmingResult> swimmingResults) {
         ArrayList<SwimmingResult> sortedList = new ArrayList<>();

        if (!singleTonUi.setCompetitiveness()) {
            sortedList = new ArrayList<>(swimmingResults.stream()
                    .filter(SwimmingResult::isCompetitive).toList());
            sortedList.sort(sortByTime);
        } else {
            sortedList = new ArrayList<>(swimmingResults.stream()
                    .filter(swimmingResult -> !swimmingResult.isCompetitive()).toList());
            sortedList.sort(sortByTime);
        } // End of if / else statement
        return sortedList;
    } // End of method


    /*
     * This method sorts results based on Rank. Read more in ui.class defining return values with info.
     */
     ArrayList<SwimmingResult> setSortByRank(SingleTonUI singleTonUi, ArrayList<SwimmingResult> swimmingResults) {
         ArrayList<SwimmingResult> sortedList = new ArrayList<>();
        switch (singleTonUi.setRank()) {
            case 3 -> {
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getRank() < 3).toList());
                sortedList.sort(sortByTime);
            } // End of case 100
            case 5 -> {
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getRank() < 5).toList());
                sortedList.sort(sortByTime);
            } // End of case 200
            case 10 -> {
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getRank() < 10).toList());
                sortedList.sort(sortByTime);
            } // End of case 500
        } // End of switch statement
        return sortedList;
    } // End of method


    /*
     * This method sorts results based on date attributes.
     */
     ArrayList<SwimmingResult> setSortByDate(SingleTonUI singleTonUi, ArrayList<SwimmingResult> swimmingResults) {
         ArrayList<SwimmingResult> sortedList = new ArrayList<>();
        switch (singleTonUi.chooseTimeFrame()) {
            case 1 -> {
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getDate()
                                .isAfter(LocalDate.now().minusMonths(3))).toList());
                sortedList.sort(sortByTime);
            } // End of case 1
            case 2 -> {
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getDate()
                                .isAfter(LocalDate.now().minusMonths(6))).toList());
                sortedList.sort(sortByTime);
            } // End of case 2
            case 3 -> {
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getDate()
                                .getYear() == LocalDate.now().getYear()).toList());
                sortedList.sort(sortByTime);
            } // End of case 3
            case 4 -> {
                int specificYear = LocalDate.now().getYear() - singleTonUi.readYear();
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getDate()
                                .isAfter(LocalDate.now().minusYears(specificYear))).toList());
                sortedList.sort(sortByTime);
            } // End of case 4
            case 5 -> {
                int specificYear = LocalDate.now().getYear() - singleTonUi.readYear();
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getDate()
                                .isEqual(LocalDate.now().minusYears(specificYear))).toList());
                sortedList.sort(sortByTime);
            } // End of case 5
            case 6 -> {
                LocalDate date = singleTonUi.setDate();
                sortedList = new ArrayList<>(swimmingResults.stream()
                        .filter(swimmingResult -> swimmingResult.getDate().isEqual(date)).toList());
                sortedList.sort(sortByTime);
            } // End of case 6
        } // End of switch statement
        return sortedList;
    } // End of method

     void setSortByTeam(int readInput, Coach coach, HashMap<Member, Coach> memberCoachHashMap) {
        HashMap<Member, Coach> temporaryMemberCoachHashMap = new HashMap<>(memberCoachHashMap);
        ArrayList<Member> sortedList = new ArrayList<>();
        switch (readInput) {
            case 1 -> {
                sortedList = new ArrayList<>(memberCoachHashMap.keySet().stream()
                        .filter(member -> member.getAge() > 18).toList());
                sortedList.sort(sortByMemberName);
            } // End of case 1
            case 2 -> {
                sortedList = new ArrayList<>(memberCoachHashMap.keySet().stream()
                        .filter(member -> member.getAge() < 18).toList());
                sortedList.sort(sortByMemberName);
            } // End of case 2
            case 3 -> {
                temporaryMemberCoachHashMap.values().removeIf(coachValues -> !coachValues.getName().equals(coach.getName()));
                sortedList = new ArrayList<>(temporaryMemberCoachHashMap.keySet());
                sortedList.sort(sortByMemberName);
            } // End of case 3
        } // End of switch statement
        for (Member member : sortedList) {
            System.out.printf("%nID: %-8d Name: %-30s Date of Birth: %-15s Tel: %-15s Membership Status: %-10b",
                    member.getUniqueID(), member.getName(),
                    member.getDateOfBirth(), member.getPhoneNumber(), member.isIsMembershipActive());
        } // End of for loop
    } // End of method

     void topFiveSmadderButRefactored(SwimmingDiscipline.SwimmingDisciplineTypes discipline,
                                            int distance, HashMap<Member, Coach> memberCoachHashMap) {
        List<CompetitiveSwimmer> allCompMembers = memberCoachHashMap.keySet().stream()
                .map(member -> (CompetitiveSwimmer) member).toList();

        HashMap<String, SwimmingResult> displayResults = new HashMap<>();
        ArrayList<SwimmingResult> swimmersResult = new ArrayList<>();

        for (CompetitiveSwimmer member : allCompMembers) {
            for (SwimmingDiscipline typeOfDiscipline : member.getSwimmingDisciplineList()) {
                if (typeOfDiscipline.getSwimmingDisciplineType() != discipline) continue;
                swimmersResult.clear();
                for (SwimmingResult result : typeOfDiscipline.getSwimmingDisciplineResults()) {

                    //if (result.isCompetitive() != isCompetitive) continue;
                    if (result.getDistance() != distance) continue;
                    swimmersResult.add(result);
                }
                if (swimmersResult.isEmpty()) continue;
                swimmersResult.sort(sortByTime);
                displayResults.put(member.getUniqueID() + " " + member.getName(), swimmersResult.get(0));
            }
        }
        List<Map.Entry<String, SwimmingResult>> filteredList = new LinkedList<>(displayResults.entrySet());

        filteredList.sort(Comparator.comparingInt(o -> o.getValue().getSwimTime()));
        filteredList.stream().limit(5).forEach(swimResults -> {
                    System.out.print(swimResults.getKey() + " ");
                    swimResults.getValue().printResults();
                }
        );
    }
} // End of class


// End of class