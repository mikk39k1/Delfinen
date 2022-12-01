package utility.result_comparators;

import actors.SwimmingResult;

public class SortByIsCompetitive extends ResultComparator {
    @Override
    public int compare(SwimmingResult swimResult, SwimmingResult otherSwimResult) {
        return Boolean.compare(swimResult.isCompetitive(), otherSwimResult.isCompetitive());
    }
}
