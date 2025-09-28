package edu.aitu.daa.algo;
import edu.aitu.daa.util.ArrayUtil;
import edu.aitu.daa.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
public class MergeSortTest {

    @Test
    void sortsRandomAndAdversarial() {
        Random r = ThreadLocalRandom.current();
        int[] sizes = {0,1,2,3,10,100,1000};
        for (int n : sizes) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = r.nextInt(1000);
            int[] b = Arrays.copyOf(a, n);
            Arrays.sort(b);
            Metrics m = new Metrics();
            MergeSort.sort(a, m);
            assertArrayEquals(b, a);
        }
        // adversarial: reverse sorted with duplicates
        int[] rev = new int[1000];
        for (int i = 0; i < rev.length; i++) rev[i] = rev.length - i;
        Arrays.sort(rev);
        int[] copy = Arrays.copyOf(rev, rev.length);
        for (int i = 0; i < copy.length/2; i++) {
            int j = copy.length-1-i; int t = copy[i]; copy[i]=copy[j]; copy[j]=t;
        }
        Metrics m = new Metrics();
        MergeSort.sort(copy, m);
        assertArrayEquals(rev, copy);
        int bound = (int)Math.ceil(Math.log(rev.length)/Math.log(2)) + 5;
        assertTrue(m.getMaxDepth() <= bound, "depth should be O(log n)");
    }
}
