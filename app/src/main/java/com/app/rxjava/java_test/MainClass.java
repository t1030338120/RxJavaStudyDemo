package com.app.rxjava.java_test;

/**
 * 描述：
 * 作者：tyc
 */
public class MainClass {


    public static void main(String[] args) {
        int[] data = {16, 7, 3, 2, 17, 8, 13, 18, 23, 43};

        //        insertSort(); //插入排序
        //        shellSort(); //希尔排序
        //        selectSort();  //简单选择排序
        //        bubbleSort(); //冒泡排序
        //        quickSort(data, 0, data.length - 1);


        buildMaxHeap(data, data.length-1);

//        print(data);
        //        heapSort(data);
        //        System.out.println("排序后的数组：");
        //        print(data);

    }


    /**
     * 插入算法
     * 基本思想：在要排序的一组数中，假设前面(n-1) [n>=2] 个数已经是排好顺序的，
     * 现在要把第n个数插到前面的有序数中， 使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序。
     */
    public static void insertSort() {
        int[] data = {32, 5, 123, 43, 12, 34, 54, 9, 8, 6, 25};
        int j = 0;
        for (int i = 1; i < data.length; i++) {
            int temp = data[i];
            for (j = i; j > 0 && temp < data[j - 1]; j--) {
                data[j] = data[j - 1];
            }
            data[j] = temp;
        }

        for (int t = 0; t < data.length; t++) {
            System.out.print(data[t] + "  ");
        }
    }


    /**
     * 希尔算法
     * 基本思想：算法先将要排序的一组数按某个增量d（n/2,n为要排序数的个数）分成若干组， 每组中记录的下标相差d.
     * 对每组中全部元素进行直 接插入排序， 然后再用一个较小的增量（d/2）对它进行分组，在每组中再进行直接插入排序。
     * 当增量减到1时，进行直接插入排序后，排序完成。
     */
    public static void shellSort() {
        int[] data = {32, 5, 123, 43, 12, 34, 54, 9, 8, 6, 25};
        int j = 0;
        for (int gap = data.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < data.length; i++) {
                int temp = data[i];
                for (j = i; j >= gap && temp < data[j - gap]; j -= gap) {
                    data[j] = data[j - gap];
                }
                data[j] = temp;
            }
        }

        for (int t = 0; t < data.length; t++) {
            System.out.print(data[t] + "  ");
        }
    }


    /**
     * 简单选择排序
     * 基本思想：在要排序的一组数中，选出最小的一个数与第一个位置的数交换；
     * 然后在剩下的数当中再找最小的与第二个位置的数交换，如此循环到倒数第二个数和最后一个数比较为止。
     */
    public static void selectSort() {
        int[] data = {32, 5, 123, 43, 12, 34, 54, 9, 8, 6, 25};
        for (int i = 0; i < data.length; i++) {
            int position = i;   //记录最小的值在数组中的下标
            int temp = data[i];  //记录最小的值
            for (int j = data.length - 1; j > i; j--) {
                if (data[j] < temp) {
                    temp = data[j];
                    position = j;
                }
            }
            data[position] = data[i]; //交换数据
            data[i] = temp;
        }
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "  ");
        }
    }


    /**
     * 冒泡排序
     */
    public static void bubbleSort() {

        int[] data = {32, 5, 123, 43, 12, 34, 54, 9, 8, 6, 25};
        int temp = 0;
        for (int i = 0; i < data.length - 1; i++) {
            for (int j = 0; j < data.length - 1 - i; j++) {
                if (data[j] > data[j + 1]) {
                    temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }

        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "  ");
        }
    }


    //快速排序一次划分
    public static int partition(int r[], int first, int end) {
        int i = first;                        //初始化
        int j = end;
        int temp;

        while (i < j) {
            while (i < j && r[j] >= r[i])
                j--;                        //右侧扫描
            if (i < j) {
                temp = r[i];                 //将较小记录交换到前面
                r[i] = r[j];
                r[j] = temp;
                i++;
            }
            while (i < j && r[i] <= r[j])
                i++;                         //左侧扫描
            if (i < j) {
                temp = r[j];
                r[j] = r[i];
                r[i] = temp;                //将较大记录交换到后面
                j--;
            }
        }
        return i;                           //i为轴值记录的最终位置
    }


    /**
     * 快速排序
     * 基本思想：选择一个基准元素,通常选择第一个元素或者最后一个元素,通过一趟扫描，
     * 将待排序列分成两部分, 一部分比基准元素小,一部分大于等于基准元素,此时基准元素在其排好序后的正确位置,
     * 然后再用同样的方法递归地排序划分的两部分。
     */
    /**
     * @param r
     * @param first 数组的第一个值的下标，一般为0
     * @param end   数据的最后一个下标
     */
    public static void quickSort(int r[], int first, int end) {
        if (first < end) {                                   //递归结束
            int pivot = partition(r, first, end);  //一次划分
            quickSort(r, first, pivot - 1);//递归地对左侧子序列进行快速排序
            quickSort(r, pivot + 1, end);  //递归地对右侧子序列进行快速排序
        }
    }


    //堆排序 1、创建最大堆  2、堆顶与堆的最后一个元素交换位置
    public static void heapSort(int[] data) {
        for (int i = 0; i < data.length; i++) {
            buildMaxHeap(data, data.length - 1 - i);
            swap(data, 0, data.length - 1 - i);
        }
    }

    //交换数组中指定位置的值
    public static void swap(int[] data, int i, int j) {
        if (i == j) {
            return;
        }
        data[i] = data[i] + data[j];
        data[j] = data[i] - data[j];
        data[i] = data[i] - data[j];
    }


    // 建立最大堆 {16, 7, 3, 20, 17, 8, 13, 18}
    public static void buildMaxHeap(int[] data, int lastIndex) {
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) { //i=3; i =2; i=1; k=0
            // 保存当前正在判断的节点
            int k = i; //k=3; k =2; k=1; k=0
            System.out.println("===============k = "+k);
            // 若当前节点的子节点存在
            while (2 * k + 1 <= lastIndex) {
                // biggerIndex总是记录较大节点的值,先赋值为当前判断节点的左子节点
                int biggerIndex = 2 * k + 1;  //biggerIndex=7; biggerIndex=5; biggerIndex=3; biggerIndex=1
                if (biggerIndex < lastIndex) {//7<7; 5<7; 3<7/ 1<7
                    // 若右子节点存在，否则此时biggerIndex应该等于 lastIndex
                    if (data[biggerIndex] < data[biggerIndex + 1]) {
                        // 若右子节点值比左子节点值大，则biggerIndex记录的是右子节点的值
                        biggerIndex++;
                    }
                }
                //16	 20 	3	7	 17	8	 13 	 18
                if (data[k] < data[biggerIndex]) { //k=3 biggerIndex=7;( k=2; biggerIndex=6); k=1;biggerIndex=3
                    // 若当前节点值比子节点最大值小，则交换2者得值，交换后将biggerIndex值赋值给k
                    swap(data, k, biggerIndex);
                    k = biggerIndex;
                    print(data);
                } else {
                    print(data);
                    break;
                }
            }
        }
    }

    public static void print(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "\t");
        }
        System.out.println();
    }






}
