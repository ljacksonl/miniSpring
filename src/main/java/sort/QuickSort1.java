package sort;

public class QuickSort1 {
    public static void main(String[] args) {
        int[] arr = new int[]{72,6,57,88,60,42,83,73,48,85};
        quicksort1(arr,0,arr.length-1);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    public static void quicksort (int[] arr,int low,int high){
        int i = low;
        int j = high;
        //存放基准值，挖第一个坑
        int tmp = arr[i];
        while (i<j){
            // 从右向左找小于tmp的数来填arr[i]
            while (i<j && arr[j] >= tmp) j--;
            //将arr[j]填到arr[i]中，arr[j]就形成了一个新的坑
            //arr[i++] = arr[j] => arr[i] = arr[j];  i++;
            if (i<j) arr[i++] = arr[j];
            // 从左向右找大于或等于x的数来填arr[j]
            while (i<j && arr[i] <= tmp) i++;
            //将arr[i]填到arr[j]中，arr[i]就形成了一个新的坑
            if (i<j) arr[j--] = arr[i];
        }
        //退出时，i等于j。将tmp填到这个坑中。
        arr[i] = tmp;
//        System.out.println(i);

        //分而治之
        if (low < i-1) quicksort(arr, low, i-1);
        if (high > i+1) quicksort(arr, i+1, high);
    }

    public static void quicksort1 (int[] arr,int low,int high){
        int i = low;
        int j = high;
        int tmp = arr[i];

        while (i<j){
            while (i<j && tmp < arr[j]){
                j--;
            }
            if (i<j){
                arr[i] = arr[j];
                i++;
            }
            while (i<j && tmp > arr[i]){
                i++;
            }
            if (i<j){
                arr[j] = arr[i];
                j--;
            }
        }
        arr[i]= tmp;

        if (i-1 >low){
            quicksort1(arr, low, i-1);
        }
        if (i+1 < high){
            quicksort1(arr, i+1, high);
        }
    }
}
