class Solution {
    public int climbStairs(int n, int[] costs) {
        // Since costs is 1-indexed, let's create a 1-indexed array of size n + 1 for easier access
        int[] c = new int[n + 1];
        for (int i = 0; i < n; i++) {
            c[i + 1] = costs[i];
        }

        long[] dp = new long[n + 1];
        java.util.Arrays.fill(dp, Long.MAX_VALUE);
        
        // Base case: starting at step 0 with cost 0
        dp[0] = 0;

        for (int j = 1; j <= n; j++) {
            // We can jump to step j from j-1, j-2, or j-3 (if they exist $\ge 0$)
            for (int i = Math.max(0, j - 3); i < j; i++) {
                long jumpCost = (long) (j - i) * (j - i) + c[j];
                dp[j] = Math.min(dp[j], dp[i] + jumpCost);
            }
        }

        return (int) dp[n];
    }
}