package utility.result_comparators;

import actors.SwimmingResult;

public class SortByDate extends ResultComparator{
    @Override
    public int compare(SwimmingResult swimmingResult, SwimmingResult otherSwimmingResult) {
        return swimmingResult.getDate().compareTo(otherSwimmingResult.getDate());
    }
}
