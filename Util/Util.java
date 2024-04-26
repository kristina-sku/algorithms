package Util;

import java.util.Random;

public class Util {
    public static void randFillArray(Integer[] arr, int bound) {
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(bound); // You can adjust the range here
        }
    }

    public static float getPercentageSaved(double time1, double time2) {
        float percentage = (float) ((time2 - time1) / time2 * 100);
        return percentage;
    }

    public static boolean isSorted(Integer[] arr) {
        if (arr == null || arr.length <= 1) {
            return true;
        }
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return false;
            }
        }
        return true;
    }



}
