import Util.*;

public class Main {
    static private final int ARRAY_SIZE = 100_000_000;
    static private final int ITERATIONS = 1;
    public static void main(String[] args) {

            new MergeSortComparison(ARRAY_SIZE, ITERATIONS);
            new QuickSortComparison(ARRAY_SIZE, ITERATIONS);

    }
}