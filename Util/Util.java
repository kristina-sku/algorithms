package Util;

import java.util.Random;

public class Util {
    public static void randFillArray(Integer[] arr, int bound) {
        Random rand = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt(bound); // You can adjust the range here
        }
    }
}
