package com.yubiaohyb.sharedemo.algorithm.sort;

/**
 * description  -  排序工具集
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 19:53
 */
public final class SortUtils {
    private static final BubbleSort BUBBLE_SORT = new BubbleSort();
    private static final CombSort COMB_SORT = new CombSort();
    private static final InsertionSort INSERTION_SORT = new InsertionSort();
    private static final OptimizedBubbleSort OPTIMIZED_BUBBLE_SORT = new OptimizedBubbleSort();
    private static final QuickSort QUICK_SORT = new QuickSort();
    private static final SelectionSort SELECTION_SORT = new SelectionSort();
    private static final ShellSort SHELL_SORT = new ShellSort();
    private static final RadixSort RADIX_SORT = new RadixSort();
    private static final CountSort COUNT_SORT = new CountSort();
    private static final HeapSort HEAP_SORT = new HeapSort();
    
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
