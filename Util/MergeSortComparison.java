package Util;

import Proposed.ProposedMSQSA;
import Proposed.ProposedPQSA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeSortComparison {
    private static final int MAX_VAL = 1_000_000;

    private static List<Long> mergeSortTimes;
    private static List<Long> parallelMergeSortTimes;
    private static int arraySize;

    public MergeSortComparison(int arraySize, int iterations) {
        this.arraySize = arraySize;
        mergeSortTimes = new ArrayList<>();
        parallelMergeSortTimes = new ArrayList<>();
        System.out.println("Running Mergesort Methods....");
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

            Integer[] mergeSortArray = arr.clone();
            Integer[] parallelmergeSortArray = arr.clone();

            long mergeSortTime = getMethodExecutionTime(
                    () -> Baseline.mergesort(mergeSortArray, Comparator.naturalOrder())
            );

            long parallelmergeSortTime = getMethodExecutionTime(
                    () -> ProposedMSQSA.parallelMergeSort(parallelmergeSortArray, Comparator.naturalOrder())
            );

            mergeSortTimes.add(mergeSortTime);
            parallelMergeSortTimes.add(parallelmergeSortTime);
        }
    }

    private void evaluateComparison() {
        double mergeSortAvgTime = mergeSortTimes.stream().mapToLong(Long::valueOf).average().orElse(0);
        double parallelMergeSortAvgTime = parallelMergeSortTimes.stream().mapToLong(Long::valueOf).average().orElse(0);
        float percentSaved = Util.getPercentageSaved(parallelMergeSortAvgTime, mergeSortAvgTime);

        System.out.print(
                "=================================================================\n" +
                        "Merge Sort Times(ms): " + mergeSortTimes + "\n" +
                        "\t\tAverage MS Time(ms): " + mergeSortAvgTime + "\n" +
                        "Parallel Merge Sort Times(ms): " + parallelMergeSortTimes + "\n" +
                        "\t\tAverage PMS Time(ms): " + parallelMergeSortAvgTime + "\n" +
                        "Average Time Save: " + percentSaved + "%\n" +
                "=================================================================\n"
        );

    }

}
