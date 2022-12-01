package utility.result_comparators;

import actors.SwimmingResult;

public class SortByTime extends ResultComparator {

    @Override
    public int compare(SwimmingResult swimResult, SwimmingResult otherSwimResult) {
        return swimResult.getSwimTime().substring(0,1).compareTo(otherSwimResult.getSwimTime().substring(0,1));
    }
}
