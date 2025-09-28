package edu.aitu.daa.algo;
import edu.aitu.daa.util.Metrics;
public class MergeSort {
    private static final int CUTOFF = 24; // insertion sort threshold
    public static void sort(int[] a, Metrics m) {
        int[] aux = new int[a.length];
        if (m != null) m.incAllocations();
        sort(a, aux, 0, a.length - 1, m);}

    private static void sort(int[] a, int[] aux, int lo, int hi, Metrics m) {
        if (lo >= hi) return;
        if (hi - lo + 1 <= CUTOFF) {
            insertion(a, lo, hi, m);
            return;}
        if (m != null) m.enter();
        int mid = lo + ((hi - lo) >> 1);
        sort(a, aux, lo, mid, m);
        sort(a, aux, mid + 1, hi, m);
        if (m != null) m.exit();

        if (m != null) m.incComparisons();
        if (a[mid] <= a[mid + 1]) return;

        merge(a, aux, lo, mid, hi, m);}

    private static void insertion(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= lo) {
                if (m != null) m.incComparisons();
                if (a[j] > key) {
                    a[j + 1] = a[j];
                    if (m != null) m.incSwaps();
                    j--;
                } else break;
            }
            a[j + 1] = key;
        }
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi, Metrics m) {
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        if (m != null) m.addSwaps(hi - lo + 1);
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            if (m != null) m.incComparisons();
            if (aux[i] <= aux[j]) a[k++] = aux[i++];
            else a[k++] = aux[j++];
        }
        while (i <= mid) a[k++] = aux[i++];
        while (j <= hi) a[k++] = aux[j++];
    }
}
