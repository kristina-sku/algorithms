package Proposed;

import Util.Util;

import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        // timing PQSA

//        System.out.println("Sequential threshold is set to 100");
//        testPQSA(100, 1000);
//        testPQSA(100, 10000);
//        testPQSA(100, 100000);

        ProposedMSQSA.runSortingAlgorithm(4, 100);



    }


    public static void testPQSA(int k, int j)
    {
        double totTime = 0;
        for(int i=0;i<k;i++) // test k times
        {
            Integer[] arr = new Integer[j]; // array of j integers
            Util.randFillArray(arr,1000);


            long startTime = System.nanoTime();
            ProposedPQSA.parallelQuicksort(arr, Comparator.naturalOrder());
            long endTime = System.nanoTime();

            long elapsedTime = endTime - startTime;
            double seconds = (double) elapsedTime / 1_000_000_000.0;
            totTime += seconds;
        }
        System.out.println("Total time taken for " + k + " iterations: " + (String.format("%.9f", totTime)) + " Seconds.");
        System.out.println("Average elapsed time for " + j + " integers: " + (String.format("%.9f", totTime / 10000)) + " Seconds.\n\n");

    }
}
//hi algoholics