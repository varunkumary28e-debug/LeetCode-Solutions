import java.util.*;

class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = new long[n - x + 1];

        // Precompute cost for all windows of size x using sliding window median
        MedianTracker tracker = new MedianTracker();
        for (int i = 0; i < n; i++) {
            tracker.add(nums[i]);
            if (i >= x) {
                tracker.remove(nums[i - x]);
            }
            if (i >= x - 1) {
                cost[i - x + 1] = tracker.getCost();
            }
        }

        // DP to find k non-overlapping windows of size x with minimum total cost
        // dp[j][i] = min cost using j windows up to index i
        long[][] dp = new long[k + 1][n + 1];
        for (int j = 1; j <= k; j++) {
            Arrays.fill(dp[j], Long.MAX_VALUE / 2);
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                dp[j][i] = dp[j][i - 1]; // Option 1: Don't take window ending at i
                if (i >= x) {
                    dp[j][i] = Math.min(dp[j][i], dp[j - 1][i - x] + cost[i - x]);
                }
            }
        }

        return dp[k][n];
    }

    // Helper class using two TreeMaps to maintain sliding window median and cost in O(log x)
    static class MedianTracker {
        TreeMap<Long, Integer> left = new TreeMap<>();
        TreeMap<Long, Integer> right = new TreeMap<>();
        int leftSize = 0, rightSize = 0;
        long leftSum = 0, rightSum = 0;

        void add(long val) {
            if (left.isEmpty() || val <= left.lastKey()) {
                left.put(val, left.getOrDefault(val, 0) + 1);
                leftSum += val;
                leftSize++;
            } else {
                right.put(val, right.getOrDefault(val, 0) + 1);
                rightSum += val;
                rightSize++;
            }
            balance();
        }

        void remove(long val) {
            if (left.containsKey(val)) {
                left.put(val, left.get(val) - 1);
                if (left.get(val) == 0) left.remove(val);
                leftSum -= val;
                leftSize--;
            } else {
                right.put(val, right.get(val) - 1);
                if (right.get(val) == 0) right.remove(val);
                rightSum -= val;
                rightSize--;
            }
            balance();
        }

        void balance() {
            while (leftSize < rightSize) {
                Map.Entry<Long, Integer> entry = right.firstEntry();
                long val = entry.getKey();
                right.put(val, right.get(val) - 1);
                if (right.get(val) == 0) right.remove(val);
                rightSum -= val;
                rightSize--;

                left.put(val, left.getOrDefault(val, 0) + 1);
                leftSum += val;
                leftSize++;
            }
            while (leftSize > rightSize + 1) {
                Map.Entry<Long, Integer> entry = left.lastEntry();
                long val = entry.getKey();
                left.put(val, left.get(val) - 1);
                if (left.get(val) == 0) left.remove(val);
                leftSum -= val;
                leftSize--;

                right.put(val, right.getOrDefault(val, 0) + 1);
                rightSum += val;
                rightSize++;
            }
        }

        long getCost() {
            long median = left.lastKey();
            // Total operations = (sum of elements > median) - (sum of elements < median)
            // adjusted for counts of elements matching the median
            return (rightSum - median * rightSize) + (median * leftSize - leftSum);
        }
    }
}