package Proposed;
import Util.*;

public class Main {
    static private final int ARRAY_SIZE = 1_000_000;
    static private final int ITERATIONS = 50;
    public static void main(String[] args) {
        new MergeSortComparison(ARRAY_SIZE,ITERATIONS);
    }
}