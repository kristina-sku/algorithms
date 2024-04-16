import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ProposedPQSA {

    private static final int SEQUENTIAL_THRESHOLD = 100;

    public static <T extends Comparable<? super T>> void parallelQuicksort(final T[] array, final Comparator<T> comparator) {
        final ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(new RecursiveQuickSortTask<>(array, 0, array.length - 1, comparator));
    }

    private static class RecursiveQuickSortTask<T extends Comparable<? super T>> extends RecursiveAction {

        private final T[] array;
        private final int low;
        private final int high;
        private final Comparator<T> comparator;

        public RecursiveQuickSortTask(T[] array, int low, int high, Comparator<T> comparator) {
            this.array = array;
            this.low = low;
            this.high = high;
            this.comparator = comparator;
        }

        @Override
        protected void compute() {
            // When size of partition is less than the threshold, sequentially quicksort it - more efficient
            if (high - low <= SEQUENTIAL_THRESHOLD) {
                Baseline._quicksort(array, low, high, comparator);
                return;
            }

            int pivotIndex = Baseline.partition(array, low, high, comparator);

            RecursiveQuickSortTask<T> leftTask = new RecursiveQuickSortTask<>(array, low, pivotIndex - 1, comparator);
            RecursiveQuickSortTask<T> rightTask = new RecursiveQuickSortTask<>(array, pivotIndex + 1, high, comparator);

            leftTask.fork();
            rightTask.compute();
            leftTask.join();
        }
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[1000];
        Util.randFillArray(arr,10000);
        System.out.println("Before: " + Arrays.toString(arr));

        parallelQuicksort(arr, Comparator.naturalOrder());
        System.out.println(Arrays.toString(arr));
    }
}
