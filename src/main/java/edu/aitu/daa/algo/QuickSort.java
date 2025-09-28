package edu.aitu.daa.algo;
import edu.aitu.daa.util.ArrayUtil;
import edu.aitu.daa.util.Metrics;

import java.util.concurrent.ThreadLocalRandom;
public class QuickSort {
    public static void sort(int[] a, Metrics m) {
        if (a.length <= 1) return;
        sort(a, 0, a.length - 1, m);}
    private static void sort(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            if (m != null) { m.enter(); }
            int p = partitionRandom(a, lo, hi, m);
            if (m != null) { m.exit(); }
            if (p - lo < hi - p) {
                if (lo < p) sort(a, lo, p, m);
                lo = p + 1;
            } else {
                if (p + 1 < hi) sort(a, p + 1, hi, m);
                hi = p;
            }
        }
    }

    private static int partitionRandom(int[] a, int lo, int hi, Metrics m) {
        int idx = ThreadLocalRandom.current().nextInt(lo, hi + 1);
        ArrayUtil.swap(a, lo, idx);
        if (m != null) m.incSwaps();
        int pivot = a[lo];
        int i = lo - 1, j = hi + 1;
        while (true) {
            do {
                i++;
                if (m != null) m.incComparisons();
            } while (a[i] < pivot);
            do {
                j--;
                if (m != null) m.incComparisons();
            } while (a[j] > pivot);
            if (i >= j) return j;
            ArrayUtil.swap(a, i, j);
            if (m != null) m.incSwaps();
        }
    }
}
