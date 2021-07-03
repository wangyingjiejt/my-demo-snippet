package com.wyj.interview;

import org.junit.Test;

import java.util.Arrays;

public class QuickSort {

    private void swap(int[] arr,int a,int b){
        int tmp = arr[a];
        arr[a]=arr[b];
        arr[b]=tmp;
    }

    /**************************************************单向快排**************************************************/

    @Test
    public void test(){
        int[] arr= {5, 9, 4, 6, 5, 3,7};
        quickSort(arr,0,arr.length-1);
        Arrays.stream(arr).forEach(System.out::println);
    }

    public void quickSort(int arr[], int begin, int end) {
        if (begin < end) {
            int pivotIndex = partition(arr, begin, end);
            quickSort(arr,begin,pivotIndex-1);
            quickSort(arr,pivotIndex+1,end);
        }
    }



    /**
     * 采用单向遍历的方式
     * @author W.Y.J
     * @Date 2021/7/2 11:26 下午
     * @param arr
     * @param begin
     * @param end
     * @return int
     */
    private int partition(int arr[], int begin, int end) {
        int i = begin-1;//i记录一趟遍历下来比pivot小的最后一个元素的位置
        int pivot = arr[end];
        for (int j = begin; j <end ; j++) {
            if (arr[j]<=pivot){
                //只要比pivot小，就记录到a[i]中
                i++;
                swap(arr,i,j);
            }
        }
        //最后将pivot插入到比它小的后边
        int pivotIndex=i+1;
        swap(arr,pivotIndex,end);
        return pivotIndex;
    }
    /*******************************************双向快排**************************************************/


    @Test
    public void testDoubleQuickSort(){
        int[] arr= {5, 9, 4, 6, 5, 3,7};
        doubleQuickSort(arr,0,arr.length-1);
        Arrays.stream(arr).forEach(System.out::println);
    }


    /**
     * 使用两个指针left 、right向中间遍历
     * left对应的值比pivot小，则left++继续向右遍历，否则暂停遍历
     * right
     * @author W.Y.J
     * @Date 2021/7/3 5:30 下午
     * @param arr
     * @param begin
     * @param end
     * @return void
     */
    public void doubleQuickSort(int arr[], int begin, int end) {
        if (begin < end) {
            int pivotIndex = doublePartitions(arr, begin, end);
            doubleQuickSort(arr,begin,pivotIndex-1);
            doubleQuickSort(arr,pivotIndex+1,end);
        }
    }

    private int doublePartitions(int[] arr, int begin, int end) {
        int left = begin+1;
        int right =end;
        int pivot=arr[begin];
        while (left<right){
            while (left<=right&&arr[left]<=pivot)
                left++;
            while (left<=right&&arr[right]>pivot)
                right--;
            if (left<right){
                swap(arr,left,right);
            }
        }
        //最后将第一个作为pivot支点的元素和right交换位置
        swap(arr,begin,right);

        return right;
    }

    /*******************************************冒泡排序*************************************************/
    @Test
    public void demo(){
        int[] arr= {5, 9, 4, 6, 5, 3,7};
//        partition(arr,0,arr.length-1);
        popSort(arr);
        Arrays.stream(arr).forEach(System.out::print);
    }


    /**
     * 冒泡排序
     * @author W.Y.J
     * @Date 2021/7/2 11:27 下午
     * @param arr
     * @return void
     */
    public void popSort(int[] arr){
        int length = arr.length;
        for (int i =0;i<length;i++){
            //先找出最大的放到最后
            for (int j =0;j<length-i-1;j++)
            if(arr[j]>arr[j+1]){
                int swap = arr[j+1];
                arr[j+1]=arr[j];
                arr[j]=swap;
            }
        }
    }
}
