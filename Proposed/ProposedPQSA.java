package Proposed;

import Util.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ProposedPQSA {

    private static final int SEQUENTIAL_THRESHOLD = 100;

    public static <T extends Comparable<? super T>> void parallelQuicksort(final T[] array, final Comparator<T> comparator) {
        // Initialize pool instance to add process' too
        final ForkJoinPool pool = ForkJoinPool.commonPool();

        // Submit ParallelQuickSortTask to the pool to compute
        pool.invoke(new ParallelQuicksortTask<>(array, 0, array.length - 1, comparator));

    }

    private static class ParallelQuicksortTask<T extends Comparable<? super T>> extends RecursiveAction {

        private final T[] array;
        private final int low;
        private final int high;
        private final Comparator<T> comparator;

        public ParallelQuicksortTask(T[] array, int low, int high, Comparator<T> comparator) {
            this.array = array;
            this.low = low;
            this.high = high;
            this.comparator = comparator;
        }

        @Override
        protected void compute() {
            // Base Case - For small sub-arrays use sequential sorting for efficiency
            if (high - low <= SEQUENTIAL_THRESHOLD) {
                Baseline._quicksort(array, low, high, comparator);
                return;
            }

            int pivotIndex = Baseline.partition(array, low, high, comparator);

            // Creates subtasks for left & right partition
            ParallelQuicksortTask<T> leftTask = new ParallelQuicksortTask<>(array, low, pivotIndex - 1, comparator);
            ParallelQuicksortTask<T> rightTask = new ParallelQuicksortTask<>(array, pivotIndex + 1, high, comparator);

            // Fork left task to another thread if possible
            leftTask.fork();

            // Current thread computes right task
            rightTask.compute();

            // Wait for left task to finish before continuing
            leftTask.join();
        }
    }
}


