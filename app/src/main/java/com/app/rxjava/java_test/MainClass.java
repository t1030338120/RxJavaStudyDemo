package com.app.rxjava.java_test;

/**
 * 描述：
 * 作者：tyc
 */
public class MainClass {


    public static void main(String[] args) {
        int[] data = {32, 5, 123, 43, 12, 34, 54, 9, 8, 6, 25};

        //        insertSort(); //插入排序
        //        shellSort(); //希尔排序
        //        selectSort();  //简单选择排序
        //        bubbleSort(); //冒泡排序
        //        quickSort(data, 0, data.length - 1);

        sift(data, 3, data.length - 1);
        heapSort(data, data.length-1);
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + "  ");
        }
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
     *
     * @param r
     * @param first 数组的第一个值的下标，一般为0
     * @param end 数据的最后一个下标
     */
    public static void quickSort(int r[], int first, int end) {
        if (first < end) {                                   //递归结束
            int pivot = partition(r, first, end);  //一次划分
            quickSort(r, first, pivot - 1);//递归地对左侧子序列进行快速排序
            quickSort(r, pivot + 1, end);  //递归地对右侧子序列进行快速排序
        }
    }


    //筛选法调整堆
    public static void sift(int r[], int k, int m) {

        int i;
        int j;
        int temp;
        i = k;
        j = 2 * i + 1;                            //置i为要筛的结点，j为i的左孩子
        while (j <= m)                          //筛选还没有进行到叶子
        {
            if (j < m && r[j] < r[j + 1])
                j++;                          //比较i的左右孩子，j为较大者
            if (r[i] > r[j])
                break;             //根结点已经大于左右孩子中的较大者
            else {
                temp = r[i];
                r[i] = r[j];
                r[j] = temp;                   //将根结点与结点j交换
                i = j;
                j = 2 * i + 1;                     //被筛结点位于原来结点j的位置
            }
        }
    }


    //堆排序
    static void heapSort(int r[], int n)
    {

        int i;
        int temp;
        for (i=n/2; i>=0; i--)                //初始建堆，从最后一个非终端结点至根结点
            sift(r, i, n) ;
        for (i=n-1; i>0; i--)                //重复执行移走堆顶及重建堆的操作
        {
            temp=r[i];
            r[i]=r[0];
            r[0]=temp;
            sift(r, 0, i - 1);
        }
    /*    for(i=0;i<n;i++)
            cout<<r[i]<<" ";
        cout<<"\n";*/
    }

}
