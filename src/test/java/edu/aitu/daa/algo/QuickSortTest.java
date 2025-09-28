package edu.aitu.daa.algo;
import edu.aitu.daa.util.ArrayUtil;
import edu.aitu.daa.util.Metrics;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
public class QuickSortTest {

    @Test
    void sortsAndBoundsDepth() {
        Random r = ThreadLocalRandom.current();
        int n = 20_000;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt();
        int[] b = Arrays.copyOf(a, n);
        Arrays.sort(b);
        Metrics m = new Metrics();
        QuickSort.sort(a, m);
        assertArrayEquals(b, a);

        int bound = 2 * (int)Math.floor(Math.log(n)/Math.log(2)) + 20;
        assertTrue(m.getMaxDepth() <= bound, "QS depth should be bounded ~ 2*log2(n)");
    }
}
