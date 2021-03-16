package com.wyj.interview;

import org.junit.Test;

import java.util.Arrays;

public class QuickSort {


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


    private int partition(int arr[], int begin, int end) {
        int i = begin-1;//i记录一趟遍历下来比pivot小的最后一个元素的位置
        int pivot = arr[end];
        for (int j = begin; j <end ; j++) {
            if (arr[j]<=pivot){
                //只要比pivot小，就记录到a[i]中
                i++;
                int swap =arr[i];
                arr[i]=arr[j];
                arr[j]=swap;
            }
        }
        //最后将pivot插入到比它小的后边
        int pivotIndex=i+1;
        int swap = arr[pivotIndex];
        arr[pivotIndex]=pivot;
        arr[end]=swap;
        return pivotIndex;
    }

    @Test
    public void demo(){
        int[] arr= {5, 9, 4, 6, 5, 3,7};
//        partition(arr,0,arr.length-1);
        popSort(arr);
        Arrays.stream(arr).forEach(System.out::print);
    }


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
