package edu.aitu.daa.util;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
public class ArrayUtil {
    public static int[] randomIntArray(int n, int bound) {
        Random r = ThreadLocalRandom.current();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt(bound);
        return a;
    }

    public static int[] copy(int[] a) {
        return Arrays.copyOf(a, a.length);
    }

    public static void shuffle(int[] a) {
        Random r = ThreadLocalRandom.current();
        for (int i = a.length - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            swap(a, i, j);
        }
    }

    public static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i] < a[i-1]) return false;
        return true;
    }
}
