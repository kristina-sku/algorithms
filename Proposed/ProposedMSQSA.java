package Proposed;

import Util.Baseline;
import Util.Util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ProposedMSQSA {

    private static final int SEQUENTIAL_THRESHOLD = 100;

    public static <T extends Comparable<? super T>> void MSQSASort(T[] array, Comparator<T> comparator) {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        int num_threads = Math.min(pool.getParallelism(), array.length); // Adjust num_threads if less than array size
        pool.invoke(new ParallelMSQSATask<>(array, 0, array.length - 1, num_threads, comparator));
    }

    private static class ParallelMSQSATask<T extends Comparable<? super T>> extends RecursiveAction {

        private final T[] array;
        private final int leftStart;
        private final int rightEnd;
        private final int num_threads;
        private final Comparator<T> comparator;

        public ParallelMSQSATask(T[] array, int leftStart, int rightEnd, int num_threads, Comparator<T> comparator) {
            this.array = array;
            this.leftStart = leftStart;
            this.rightEnd = rightEnd;
            this.num_threads = num_threads;
            this.comparator = comparator;
        }

        @Override
        protected void compute() {
            if (rightEnd - leftStart <= SEQUENTIAL_THRESHOLD) {
                Baseline._quicksort(array, leftStart, rightEnd, comparator);
                return;
            }

            int mid = leftStart + (rightEnd - leftStart) / 2;

            // Create subtasks for left and right halves
            ParallelMSQSATask<T> leftTask = new ParallelMSQSATask<>(array, leftStart, mid, num_threads, comparator);
            ParallelMSQSATask<T> rightTask = new ParallelMSQSATask<>(array, mid + 1, rightEnd, num_threads, comparator);

            // Fork left and right tasks
            leftTask.fork();
            rightTask.fork();

            // Wait for subtasks to finish
            leftTask.join();
            rightTask.join();

            mergeSubarrays(array, leftStart,rightEnd, comparator);
        }


        private void mergeSubarrays(T[] arr, int low, int high, Comparator<T> comparator) {
            int mid = (low + high) / 2;
            int rightStart = mid + 1;

            T[] temp = Arrays.copyOfRange(arr, low, high + 1);

            int i = low; // Index for the left subarray
            int j = rightStart; // Index for the right subarray
            int k = low; // Index for the merged array

            while (i <= mid && j <= high) {
                if (comparator.compare(temp[i - low], temp[j - low]) <= 0) {
                    arr[k++] = temp[i++ - low]; // Copy element from left subarray
                } else {
                    arr[k++] = temp[j++ - low]; // Copy element from right subarray
                }
            }

            // Copy any remaining elements from left subarray
            while (i <= mid) {
                arr[k++] = temp[i++ - low];
            }

            // Copy any remaining elements from right subarray
            while (j <= high) {
                arr[k++] = temp[j++ - low];
            }
        }
    }
}
