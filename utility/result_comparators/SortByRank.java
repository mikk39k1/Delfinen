package utility.result_comparators;

import actors.SwimmingResult;

public class SortByRank extends ResultComparator {
    @Override
    public int compare(SwimmingResult swimResult, SwimmingResult otherSwimResult) {
        return Integer.compare(swimResult.getRank(), otherSwimResult.getRank());
    }
}
