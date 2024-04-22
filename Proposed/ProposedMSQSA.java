package Proposed;

import Util.Baseline;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ProposedMSQSA {

    private static final int SEQUENTIAL_THRESHOLD = 100;

    public static<T extends Comparable<? super T>> void parallelMergeSort(final T[] array, final Comparator<T> comparator) {
        final ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new ParallelMergeSortTask<>(array, 0, array.length - 1, comparator));
    }

    private static class ParallelMergeSortTask<T extends Comparable<? super T>> extends RecursiveAction {
        private final T[] array;
        private final int low;
        private final int high;
        private final Comparator<T> comparator;

        public ParallelMergeSortTask(T[] array, int low, int high, Comparator<T> comparator) {
            this.array = array;
            this.low = low;
            this.high = high;
            this.comparator = comparator;
        }

        @Override
        protected void compute() {
            if (low < high) {
                if (high - low <= SEQUENTIAL_THRESHOLD) {
                    Baseline._mergesort(array, low, high, comparator);
                    return;
                }

                int mid = low + (high - low) / 2;

                ParallelMergeSortTask<T> leftTask = new ParallelMergeSortTask<>(array, low, mid, comparator);
                ParallelMergeSortTask<T> rightTask = new ParallelMergeSortTask<>(array, mid + 1, high, comparator);

                leftTask.fork();
                rightTask.compute();
                leftTask.join();

                Baseline.merge(array, low, mid, high, comparator);
            }
        }
    }
}
