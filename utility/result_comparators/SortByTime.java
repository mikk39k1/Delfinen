package utility.result_comparators;

import actors.SwimmingResult;

public class SortByTime extends ResultComparator {

    @Override
    public int compare(SwimmingResult swimResult, SwimmingResult otherSwimResult) {
        return Integer.compare(swimResult.getSwimTime(), (otherSwimResult.getSwimTime()));
    }
}
