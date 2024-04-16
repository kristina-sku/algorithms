import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Baseline implements Comparator<Object>, Comparable<Object> {

    //quicksort
    public static <T extends Comparable<? super T>> void quicksort(final T[] array, final Comparator<T> comparator) {
        int low = 0;
        int high = array.length - 1;

        _quicksort(array, low, high, comparator);
    }

    static <T extends Comparable<? super T>> void _quicksort(final T[] array, int low, int high, final Comparator<T> comparator) {
        if (low < high) {
            int pivotIndex = partition(array, low, high, comparator);
            _quicksort(array, low, pivotIndex - 1, comparator);
            _quicksort(array, pivotIndex + 1, high, comparator);
        }
    }

    public static <T extends Comparable<? super T>> int partition(final T[] array, int low, int high, final Comparator<T> comparator) {
        // select the pivot element
        T pivot = array[high];

        int i = low - 1;

        // iterate through the array and move elements less than the pivot to the left
        for (int j = low; j < high; j++) {
            if (comparator.compare(array[j], pivot) < 0) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, high);
        return i + 1;
    }

    private static <T extends Comparable<? super T>> void swap(final T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // 2-way merge sort
    public static <T extends Comparable<? super T>> void mergesort(final T[] array, final Comparator<T> comparator) {
        int length = array.length;
        if (length < 2) {
            return;
        }

        final T[] left = Arrays.copyOfRange(array, 0, array.length / 2);
        final T[] right = Arrays.copyOfRange(array, array.length / 2, array.length);

        mergesort(left, comparator);
        mergesort(right, comparator);

        int leftIndex = 0;
        int rightIndex = 0;
        int index = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            T leftItem = left[leftIndex];
            T rightItem = right[rightIndex];

            // If left < right
            if (comparator.compare(leftItem, rightItem) < 0) {
                array[index] = left[leftIndex];
                leftIndex++;
            } else {
                array[index] = right[rightIndex];
                rightIndex++;
            }
            index++;
        }

        // merge the remaining elements(if there are any)
        while (leftIndex < left.length) {
            array[index] = left[leftIndex];
            leftIndex++;
            index++;
        }

        while (rightIndex < right.length) {
            array[index] = right[rightIndex];
            rightIndex++;
            index++;
        }
    }

    // Bucket sort
    public static <T extends Comparable<? super T>> void bucketSort(T[] array, Comparator<T> comparator) {
        int n = array.length;
        List<T>[] buckets = new List[n];

        // Initialize each bucket
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }

        // Distribute elements into buckets
        for (T item : array) {
            int bucketIndex = (int) (n * ((double) comparator.compare(item, null)));
            buckets[bucketIndex].add(item);
        }

        // Sort each bucket
        for (List<T> bucket : buckets) {
            if (!bucket.isEmpty()) {
                mergesort(bucket.toArray((T[]) new Object[bucket.size()]), comparator);
            }
        }

        // Concatenate the buckets
        int index = 0;
        for (List<T> bucket : buckets) {
            for (T item : bucket) {
                array[index++] = item;
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
