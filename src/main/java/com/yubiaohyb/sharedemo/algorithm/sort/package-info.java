/**
 * 抽象类{@link com.yubiaohyb.sharedemo.algorithm.sort.AbstractSort}内部封装了数据元素互换位置用的方法
 * 接口{@link com.yubiaohyb.sharedemo.algorithm.sort.Sort}作为对外提供的公共接口
 * <p>
 * 基础排序
 * <ul>
 * <li/>冒泡排序({@link com.yubiaohyb.sharedemo.algorithm.sort.BubbleSort}和{@link com.yubiaohyb.sharedemo.algorithm.sort.OptimizedBubbleSort})
 * <li/>插入排序({@link com.yubiaohyb.sharedemo.algorithm.sort.InsertionSort})
 * <li/>选择排序({@link com.yubiaohyb.sharedemo.algorithm.sort.SelectionSort})</ul>
 *
 * <br/>高效排序
 * <ul>
 * <li/>梳排序({@link com.yubiaohyb.sharedemo.algorithm.sort.CombSort})
 * <li/>希尔排序({@link com.yubiaohyb.sharedemo.algorithm.sort.ShellSort})
 * <li/>快速排序({@link com.yubiaohyb.sharedemo.algorithm.sort.QuickSort})
 * <li/>堆排序({@link com.yubiaohyb.sharedemo.algorithm.sort.HeapSort})
 * </ul>
 *
 * <br/>空间换时间排序
 * <ul>
 * <li/>归并排序({@link com.yubiaohyb.sharedemo.algorithm.sort.MergeSort})
 * <li/>基数排序({@link com.yubiaohyb.sharedemo.algorithm.sort.RadixSort})
 * <li/>计数排序({@link com.yubiaohyb.sharedemo.algorithm.sort.CountSort})
 * </ul>
 * </p>
 *
 * <pre>
 * 术语
 * 稳定排序：在数组中a==b，且a在b的前面，经过排序后a仍然保持在b的前面
 * 非稳定排序：与上面相反
 * 原地排序：数组在排序过程中，不申请多余空间，仅使用已有的内存进行数据的比较与交换
 * 非原地排序：需要借助辅助数组来完成排序
 * 时间复杂度：算法执行所需消耗的时间
 * 空间复杂度：算法执行所需消耗的空间
 *
 * 个人理解：算法的稳定性与算法的具体实现细节有一定关系
 * </pre>
 *
 * @author Yubiao Huang (https://github.com/yubiaohyb)
 * @version $$Id$$
 * @since 2019/12/3 14:06
 */
package com.yubiaohyb.sharedemo.algorithm.sort;
