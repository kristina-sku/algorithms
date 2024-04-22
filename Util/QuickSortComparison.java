package Util;

import Proposed.ProposedPQSA;

import java.util.Comparator;

public class QuickSortComparison {
    private static final int ARR_SIZE = 1_000_000;
    private static final int MAX_VAL = 1_000_000;

    private static Integer[] fillArray(int size) {
        Integer[] arr = new Integer[size];
        Util.randFillArray(arr, MAX_VAL);
        return arr;
    }
    private static long getMethodExecutionTime(Runnable method) {
        long startTime = System.currentTimeMillis();
        method.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        Integer[] arr = fillArray(ARR_SIZE);

        Integer[] quickSortArray = arr.clone();
        Integer[] parallelQuickSortArray = arr.clone();

        long quickSortTime = getMethodExecutionTime(
                () -> Baseline.quicksort(quickSortArray, Comparator.naturalOrder())
        );

        long parallelQuickSortTime = getMethodExecutionTime(
                () -> ProposedPQSA.parallelQuicksort(parallelQuickSortArray, Comparator.naturalOrder())
        );

        System.out.println("Parallel QuickS Time: " + parallelQuickSortTime + "ms");
        System.out.println("QuickS Time: " + quickSortTime + "ms");

    }

}
