package edu.aitu.daa.util;
public class Metrics {
    private long comparisons;
    private long swaps;
    private long allocations;
    private int currentDepth;
    private int maxDepth;

    public void incComparisons() { comparisons++; }
    public void addComparisons(long c) { comparisons += c; }
    public void incSwaps() { swaps++; }
    public void addSwaps(long s) { swaps += s; }
    public void incAllocations() { allocations++; }
    public void addAllocations(long a) { allocations += a; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getAllocations() { return allocations; }
    public int getMaxDepth() { return maxDepth; }

    public void enter() {
        currentDepth++;
        if (currentDepth > maxDepth) maxDepth = currentDepth;
    }
    public void exit() {
        currentDepth--;
        if (currentDepth < 0) currentDepth = 0;
    }

    public void reset() {
        comparisons = swaps = allocations = 0;
        currentDepth = maxDepth = 0;
    }
}
