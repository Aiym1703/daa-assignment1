# Assignment 1

## 1. Architecture Notes
- **MergeSort:** Uses a *reusable auxiliary buffer* for merging, avoiding repeated allocations. For small subarrays, an *insertion sort cutoff* is applied. Before merging, we also check `a[mid] <= a[mid+1]`, skipping unnecessary merges on already sorted data. This reduces allocations and improves performance.
- **QuickSort:** Implements *randomized pivot selection* with *Hoare partition*. Always recurses on the smaller partition while iterating over the larger one. This bounds recursion depth to `O(log n)` even for adversarial inputs, reducing stack usage significantly.
- **Deterministic Select (Median-of-Medians):** Divides into groups of 5, selects the median of medians as pivot, and recurses only into the side containing k. At least 30% of elements are discarded each step. Depth remains logarithmic (base < 2), and runtime is linear overall.
- **Closest Pair (2D):** Standard divide & conquer algorithm. Points are initially sorted by x. Recursively solves subproblems, then merges by y. The “strip” region is checked with the classic ≤7–8 neighbor bound. A temporary buffer is reused for y-merges to minimize allocations.
- **Shared Metrics:** The project tracks `comparisons`, `swaps`, `allocations`, and `max_depth`. Each recursive call uses `Metrics.enter()` and `Metrics.exit()` so `max_depth` reflects the peak recursion depth.

---

## 2. Recurrence Analysis

- **MergeSort**  
  Recurrence: `T(n) = 2T(n/2) + Θ(n)`  
  By **Master Theorem (Case 2)** → `Θ(n log n)`. The cutoff to insertion sort and the “already sorted” check reduce constant factors but not the asymptotic bound.

- **QuickSort (randomized, recurse-smaller-first)**  
  Recurrence: `T(n) = T(αn) + T((1−α)n) + Θ(n)`  
  Expected → `Θ(n log n)`. Worst-case is `Θ(n²)`, but randomization makes it highly unlikely. Smaller-first recursion guarantees recursion depth `O(log n)`.

- **Deterministic Select (Median-of-Medians)**  
  Recurrence: `T(n) = T(n/5) + T(7n/10) + Θ(n)`  
  By **Akra–Bazzi** → `Θ(n)`. Linear runtime with moderate constants, and predictable worst-case behavior.

- **Closest Pair**  
  Recurrence: `T(n) = 2T(n/2) + Θ(n)`  
  By Master Theorem → `Θ(n log n)`. Far better than the brute force `Θ(n²)`.

---

## 3. Experimental Results

Experiments were run with varying input sizes. For each algorithm, we measured runtime, recursion depth, and metrics.  

Example tables:

### MergeSort
![mergesort-table](https://github.com/user-attachments/assets/a869a11e-4d29-4659-a64e-43a53ab0a094)

### QuickSort
![quicksort-table](https://github.com/user-attachments/assets/a764db58-4923-414c-b256-2b7fc512d4fb)

### Select
![select-table](https://github.com/user-attachments/assets/d83e0fcb-2dc8-4b0e-ac2b-805872adb240)

### Closest Pair
![closest-table](https://github.com/user-attachments/assets/48a427c1-71be-44e2-a398-250739fe392e)

---
<img width="1170" height="777" alt="image" src="https://github.com/user-attachments/assets/e3262ea2-b921-46f5-9a61-8c81c7701c9c" />
<img width="983" height="693" alt="image" src="https://github.com/user-attachments/assets/37dfd46b-ef28-4986-9f82-8d2c69904262" />
<img width="953" height="696" alt="image" src="https://github.com/user-attachments/assets/d3422d20-42e5-413d-bf3c-e25eb5045c35" />



## 4. Constant-Factor Effects

- **Cache & memory locality:** QuickSort’s in-place partitioning benefits from cache locality, often making it faster than MergeSort on random data. MergeSort’s buffer requires extra memory but guarantees stable sequential writes.  
- **Insertion sort cutoff:** Eliminates overhead on small subarrays.  
- **Already sorted check (MergeSort):** Skips entire merges when the halves are already ordered, saving work.  
- **GC & allocations:** Reusing buffers significantly reduces garbage collection pressure. Closest Pair also reuses temporary arrays during y-merging.

---

## 5. Summary — Theory vs Measurements

- **MergeSort vs QuickSort:** Both scale as `Θ(n log n)`. QuickSort is often faster in practice due to better cache behavior, while MergeSort wins on nearly sorted or adversarial cases.  
- **Select (MoM5):** Runtime grows nearly linearly; doubling `n` roughly doubles the time. Safe worst-case, predictable growth.  
- **Closest Pair:** Also `Θ(n log n)`, but with higher constants due to floating-point operations and merging overhead. Still far better than quadratic brute force.  
- **Overall:** Results match theoretical expectations. Small deviations at low `n` come from JVM warm-up, branch mispredictions, and cutoff optimizations.





