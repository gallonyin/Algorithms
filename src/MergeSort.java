package soup;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] array = new int[]{0, 2, 1, 4, 5, 3, 6};

        sort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    private static void sort(int[] array, int start, int end) {
        if (start < end) {
            int mid = (start + end) / 2;
            sort(array, start, mid);
            sort(array, mid + 1, end);
            merge(array, start, end);
        }
    }

    private static void merge(int[] array, int start, int end) {
        int mid = (start + end) / 2;
        int[] tempArray = new int[end - start + 1];
        int p1 = start;
        int p2 = mid + 1;
        int p = 0;
        while (p1 <= mid && p2 <= end) {
            if (array[p1] <= array[p2]) {
                tempArray[p++] = array[p1++];
            } else {
                tempArray[p++] = array[p2++];
            }
        }
        while (p1 <= mid) {
            tempArray[p++] = array[p1++];
        }
        while (p2 <= end) {
            tempArray[p++] = array[p2++];
        }
        for (int i = 0; i < tempArray.length; i++) {
            array[start + i] = tempArray[i];
        }
    }

}
