# Parallel Sorting Algorithms

This repository contains implementations of two parallel sorting algorithms using Java's ForkJoin framework: the Parallel Merge Sort Quick Sort Algorithm (MSQSA) and the Parallel Quick Sort Algorithm (PQSA). These implementations are made to handle large arrays by leveraging multi-threading. It compares time spent sorting vs Sequential Implementations of each algorithm.

## Usage

Edit the variables ```ITERATIONS``` and ```ARRAY_SIZE``` in ```Main.java```. Compile & Execute the Main Class.

## Findings

PQSA found to be 53.56% faster than Quicksort for sizes ```N >= 10^4```\
MSQSA found to be 40.46% faster than MergeSort for sizes ```N >= 10^4```
 
