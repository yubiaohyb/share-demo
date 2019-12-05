package com.yubiaohyb.sharedemo.algorithm.sort;

/**
 * description  -  排序工具集
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 19:53
 */
public final class SortUtils {
    private final static BubbleSort BUBBLE_SORT = new BubbleSort();
    private final static CombSort COMB_SORT = new CombSort();
    private final static InsertionSort INSERTION_SORT = new InsertionSort();
    private final static OptimizedBubbleSort OPTIMIZED_BUBBLE_SORT = new OptimizedBubbleSort();
    private final static QuickSort QUICK_SORT = new QuickSort();
    private final static SelectionSort SELECTION_SORT = new SelectionSort();
    private final static ShellSort SHELL_SORT = new ShellSort();
    private final static RadixSort RADIX_SORT = new RadixSort();
    private final static CountSort COUNT_SORT = new CountSort();
    private final static HeapSort HEAP_SORT = new HeapSort();
    
    public static void bubbleSort(double[] arr) {
        BUBBLE_SORT.sort(arr);
    }

    public static void combSort(double[] arr) {
        COMB_SORT.sort(arr);
    }

    public static void insertionSort(double[] arr) {
        INSERTION_SORT.sort(arr);
    }

    public static double[] mergeSort(double[] arr1, double[] arr2) {
        return MergeSort.sort(arr1, arr2);
    }

    public static void optimizedBubbleSort(double[] arr) {
        OPTIMIZED_BUBBLE_SORT.sort(arr);
    }

    public static void quickSort(double[] arr) {
        QUICK_SORT.sort(arr);
    }

    public static void quickSort(double[] arr, int indexI, int indexJ) {
        QUICK_SORT.sort(arr, indexI, indexJ);
    }

    public static void selectionSort(double[] arr) {
        SELECTION_SORT.sort(arr);
    }

    public static void shellSort(double[] arr) {
        SHELL_SORT.sort(arr);
    }

    public static void radixSort(double[] arr) {
        RADIX_SORT.sort(arr);
    }

    public static void countSort(double[] arr) {
        COUNT_SORT.sort(arr);
    }

    public static void heapSort(double[] arr) {
        HEAP_SORT.sort(arr);
    }
}
