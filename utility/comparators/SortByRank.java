package utility.comparators;

import actors.SwimmingResult;

public class SortByRank extends Comparator{
    @Override
    public int compare(SwimmingResult swimResult, SwimmingResult otherSwimResult) {
        return Integer.compare(swimResult.getRank(), otherSwimResult.getRank());
    }
}
