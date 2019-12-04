package com.yubiaohyb.sharedemo.algorithm;

/**
 * description  -  基数排序
 *
 * 核心思想：遵循桶排序思想，按照个位、十位、百位...余数进行分组，最终得到有序结果
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 20:33
 */
public final class RadixSort extends AbstractSort {

    @Override
    public void sort(double[] arr) {
        double max = getMax(arr);
        int dimension = getDimension(max);
        for (int i=0; i<= dimension; i++) {
            double[][] departedArr = new double[10][arr.length];
            int[] departArrIndex = new int[10];
            for (int j=0; j<=arr.length-1; j++) {
                int remainder = ((int)arr[j])%((int)Math.pow(10, i+1))/((int)Math.pow(10, i));
                departedArr[remainder][departArrIndex[remainder]] = arr[j];
                departArrIndex[remainder]++;
            }
            int n = 0;
            for (int k=0; k<10; k++) {
                if (departArrIndex[k]>0) {
//                    SortUtils.quickSort(departedArr[k], 0, departArrIndex[k]-1); 这里的排序多余了
                    for (int m=0; m<=departArrIndex[k]-1; m++) {
                        arr[n] = departedArr[k][m];
                        n++;
                    }
                }
            }
        }
    }

    private int getDimension(double max) {
        int dimension = 1;
        while (Math.pow(10, dimension) < max) {
            dimension++;
        }
        return dimension+1;
    }

    private double getMax(double[] arr) {
        double max = arr[0];
        for (int i=1; i<arr.length-1; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
