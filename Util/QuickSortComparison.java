package Util;

import Proposed.ProposedPQSA;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuickSortComparison {
    private static final int MAX_VAL = 1_000_000;

    private static List<Long> quickSortTimes;
    private static List<Long> parallelQuickSortTimes;
    private static int arraySize;

    public QuickSortComparison(int arraySize, int iterations) {
        this.arraySize = arraySize;
        QuickSortComparison.quickSortTimes = new ArrayList<>();
        QuickSortComparison.parallelQuickSortTimes = new ArrayList<>();
        System.out.println("Running Quicksort Methods....");
        runMethods(iterations);
        evaluateComparison();
    }

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
    public static void runMethods(int iterations) {

        for (int i = 0; i < iterations; i++) {

            Integer[] arr = fillArray(arraySize);

            Integer[] quickSortArr = arr.clone();
            Integer[] parallelQuickSortArr = arr.clone();

            long quickSortTime = getMethodExecutionTime(
                    () -> Baseline.quicksort(quickSortArr, Comparator.naturalOrder())
            );

            long parallelQuickSortTime = getMethodExecutionTime(
                    () -> ProposedPQSA.parallelQuicksort(parallelQuickSortArr, Comparator.naturalOrder())
            );

            quickSortTimes.add(quickSortTime);
            parallelQuickSortTimes.add(parallelQuickSortTime);
        }
    }

    private void evaluateComparison() {
        double quickSortAvgTime = quickSortTimes.stream().mapToLong(Long::valueOf).average().orElse(0);
        double parallelQuickSortAvgTime = parallelQuickSortTimes.stream().mapToLong(Long::valueOf).average().orElse(0);
        float percentSaved = Util.getPercentageSaved(parallelQuickSortAvgTime, quickSortAvgTime);

        System.out.print(
                "=================================================================\n" +
                        "quick Sort Times(ms): " + quickSortTimes + "\n" +
                        "\t\tAverage MS Time(ms): " + quickSortAvgTime + "\n" +
                        "Parallel quick Sort Times(ms): " + parallelQuickSortTimes + "\n" +
                        "\t\tAverage PMS Time(ms): " + parallelQuickSortAvgTime + "\n" +
                        "Average Time Save: " + percentSaved + "%\n" +
                        "=================================================================\n"
        );

    }

}
