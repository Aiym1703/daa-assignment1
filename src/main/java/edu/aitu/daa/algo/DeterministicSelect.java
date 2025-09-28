package edu.aitu.daa.algo;
import edu.aitu.daa.util.ArrayUtil;
import edu.aitu.daa.util.Metrics;
public class DeterministicSelect {
    public static int select(int[] a, int k, Metrics m) {
        if (k < 0 || k >= a.length) throw new IllegalArgumentException("k out of bounds");
        return select(a, 0, a.length - 1, k, m);
    }
    private static int select(int[] a, int lo, int hi, int k, Metrics m) {
        while (true) {
            if (lo == hi) return a[lo];

            if (m != null) m.enter();
            int pivot = medianOfMedians(a, lo, hi, m);
            int[] bounds = partitionAroundPivot(a, lo, hi, pivot, m);
            if (m != null) m.exit();

            int leftEnd = bounds[0];
            int rightStart = bounds[1];

            if (k < leftEnd) {
                hi = leftEnd - 1;
            } else if (k >= rightStart) {
                lo = rightStart;
            } else {
                return pivot;}
            if (hi - lo < 0) throw new IllegalStateException("Invalid state");
        }
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 5) {
            insertion(a, lo, hi, m);
            return a[lo + n / 2];
        }
        int numGroups = (n + 4) / 5;
        for (int i = 0; i < numGroups; i++) {
            int gLo = lo + i * 5;
            int gHi = Math.min(gLo + 4, hi);
            insertion(a, gLo, gHi, m);
            int medianIndex = gLo + (gHi - gLo) / 2;
            ArrayUtil.swap(a, lo + i, medianIndex);
            if (m != null) m.incSwaps();
        }
        return select(a, lo, lo + numGroups - 1, lo + numGroups / 2, m);
    }
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

    private static int[] partitionAroundPivot(int[] a, int lo, int hi, int pivot, Metrics m) {
        int lt = lo, i = lo, gt = hi;
        while (i <= gt) {
            if (m != null) m.incComparisons();
            if (a[i] < pivot) {
                ArrayUtil.swap(a, lt++, i++);
                if (m != null) m.incSwaps();
            } else if (a[i] > pivot) {
                ArrayUtil.swap(a, i, gt--);
                if (m != null) m.incSwaps();
            } else {
                i++;
            }
        }
        return new int[]{lt, gt + 1};
    }
}
