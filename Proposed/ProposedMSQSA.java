package Proposed;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ProposedMSQSA {

    private static final ForkJoinPool pool = ForkJoinPool.commonPool();

    public static void runSortingAlgorithm(int numThreads, int numIntegers) {
        int[] array = generateRandomArray(numIntegers); // Generate random integers

        long startTime = System.nanoTime();

        // each thread sorts  corresponding subarray w/ nonrecursive quicksort
        for (int i = 0; i < numThreads; i++) {
            int startIndex = i * (numIntegers / numThreads); // calc start index
            int endIndex = (i + 1) * (numIntegers / numThreads) - 1; // calc end index
            if (i == numThreads - 1) { // adjust end index for  last thread
                endIndex = numIntegers - 1;
            }
            pool.invoke(new QuickSortTask(array, startIndex, endIndex));
        }

        // main thread merges sorted subarrays
        int interval = numIntegers / numThreads;
        int j = 0;
        while (interval < numIntegers) {
            while (j + interval < numIntegers) {
                merge(array, j, j + interval - 1, Math.min(j + 2 * interval - 1, numIntegers - 1));
                j += 2 * interval;
            }
            j = 0;
            interval *= 2;
        }

        long endTime = System.nanoTime(); // stop clock
        double executionTime = (endTime - startTime) / 1_000_000_000.0; // convert to second

        System.out.println("Sorted array: " + Arrays.toString(array));
        System.out.printf("Execution time: %.6f seconds%n", executionTime);
    }

    // Helper method to generate random array
    private static int[] generateRandomArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(1000); // gen random integers between 0 and 999
        }
        return array;
    }

    // merge subarrays when sorted
    private static void merge(int[] array, int start, int mid, int end) {
        int[] tempArray = new int[end - start + 1];
        int i = start, j = mid + 1, k = 0;

        while (i <= mid && j <= end) {
            if (array[i] <= array[j]) {
                tempArray[k++] = array[i++];
            } else {
                tempArray[k++] = array[j++];
            }
        }

        // copy  elements from  left subarray if any
        while (i <= mid) {
            tempArray[k++] = array[i++];
        }

        // copy  elements from  right  if any
        while (j <= end) {
            tempArray[k++] = array[j++];
        }

        // copy from tempArray back to the original array
        System.arraycopy(tempArray, 0, array, start, tempArray.length);
    }

    // nested class for quick sort task
    private static class QuickSortTask extends RecursiveAction {
        private final int[] array;
        private final int low;
        private final int high;

        public QuickSortTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (low < high) {
                int pivotIndex = partition(array, low, high);
                QuickSortTask leftTask = new QuickSortTask(array, low, pivotIndex - 1);
                QuickSortTask rightTask = new QuickSortTask(array, pivotIndex + 1, high);
                leftTask.fork();
                rightTask.compute();
                leftTask.join();
            }
        }

        private int partition(int[] array, int low, int high) {
            int pivot = array[high];
            int i = low - 1;
            for (int j = low; j < high; j++) {
                if (array[j] < pivot) {
                    i++;
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
            int temp = array[i + 1];
            array[i + 1] = array[high];
            array[high] = temp;
            return i + 1;
        }
    }
}
