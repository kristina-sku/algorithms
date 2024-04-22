package Util;

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

    public static <T extends Comparable<? super T>> void _quicksort(final T[] array, int low, int high, final Comparator<T> comparator) {
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
        _mergesort(array,0, array.length -1, comparator);
    }


        public static <T extends Comparable<? super T>> void _mergesort(final T[] array, int low, int high, final Comparator<T> comparator) {
        if (low < high) {
            int mid = low + (high - low) / 2;

            _mergesort(array, low, mid, comparator);
            _mergesort(array, mid + 1, high, comparator);

            merge(array, low, mid, high, comparator);
        }
    }

    public static <T extends Comparable<? super T>> void merge(final T[] array, int low, int mid, int high, final Comparator<T> comparator) {
        int leftSize = mid - low + 1;
        int rightSize = high - mid;

        // Create temporary arrays for left and right halves
        T[] leftArray = Arrays.copyOfRange(array, low, mid + 1);
        T[] rightArray = Arrays.copyOfRange(array, mid + 1, high + 1);

        int leftIndex = 0, rightIndex = 0, mergeIndex = low;

        // Merge left and right arrays back into the main array
        while (leftIndex < leftSize && rightIndex < rightSize) {
            if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {
                array[mergeIndex++] = leftArray[leftIndex++];
            } else {
                array[mergeIndex++] = rightArray[rightIndex++];
            }
        }

        // Copy remaining elements from left array, if any
        while (leftIndex < leftSize) {
            array[mergeIndex++] = leftArray[leftIndex++];
        }

        // Copy remaining elements from right array, if any
        while (rightIndex < rightSize) {
            array[mergeIndex++] = rightArray[rightIndex++];
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
