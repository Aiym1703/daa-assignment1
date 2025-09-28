package edu.aitu.daa.cli;
import edu.aitu.daa.algo.ClosestPair;
import edu.aitu.daa.algo.DeterministicSelect;
import edu.aitu.daa.algo.MergeSort;
import edu.aitu.daa.algo.QuickSort;
import edu.aitu.daa.util.ArrayUtil;
import edu.aitu.daa.util.Csv;
import edu.aitu.daa.util.Metrics;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
public class Main {
    public static void main(String[] args) throws IOException {
        String algo = getArg(args, "--algo", "mergesort");
        int n = Integer.parseInt(getArg(args, "--n", "100000"));
        int trials = Integer.parseInt(getArg(args, "--trials", "5"));
        String out = getArg(args, "--out", "results.csv");
        int k = Integer.parseInt(getArg(args, "--k", String.valueOf(Math.max(0, n/2))));
        Csv csv = new Csv(out);
        csv.writeHeader("algo","n","trial","time_ms","max_depth","comparisons","swaps","allocations","result");

        Metrics m = new Metrics();
        Random r = ThreadLocalRandom.current();

        for (int t = 1; t <= trials; t++) {
            m.reset();
            long start, end;
            Object result = "";
            if (algo.equalsIgnoreCase("mergesort")) {
                int[] a = ArrayUtil.randomIntArray(n, n);
                start = System.nanoTime();
                MergeSort.sort(a, m);
                end = System.nanoTime();
                result = a[Math.min(n-1, n/2)];
            } else if (algo.equalsIgnoreCase("quicksort")) {
                int[] a = ArrayUtil.randomIntArray(n, n);
                start = System.nanoTime();
                QuickSort.sort(a, m);
                end = System.nanoTime();
                result = a[Math.min(n-1, n/2)];
            } else if (algo.equalsIgnoreCase("select")) {
                int[] a = ArrayUtil.randomIntArray(n, n);
                start = System.nanoTime();
                int val = DeterministicSelect.select(a, k, m);
                end = System.nanoTime();
                result = val;
            } else if (algo.equalsIgnoreCase("closest")) {
                ClosestPair.Point[] pts = new ClosestPair.Point[n];
                for (int i = 0; i < n; i++) {
                    pts[i] = new ClosestPair.Point(r.nextDouble(), r.nextDouble());
                }
                start = System.nanoTime();
                double d = ClosestPair.closest(pts, m);
                end = System.nanoTime();
                result = d;
            } else {
                throw new IllegalArgumentException("Unknown --algo=" + algo);
            }
            long ms = (end - start) / 1_000_000;
            csv.append(algo, n, t, ms, m.getMaxDepth(), m.getComparisons(), m.getSwaps(), m.getAllocations(), result);
        }
        System.out.println("Done. Wrote: " + out);
    }

    private static String getArg(String[] args, String key, String def) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals(key)) return args[i+1];
        }
        return def;
    }
}
