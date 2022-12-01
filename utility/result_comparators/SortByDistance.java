package utility.result_comparators;

import actors.SwimmingResult;

public class SortByDistance extends ResultComparator {
    @Override
    public int compare(SwimmingResult swimResult, SwimmingResult otherSwimResult) {
        return Integer.compare(swimResult.getDistance(), otherSwimResult.getDistance());
    }
}
