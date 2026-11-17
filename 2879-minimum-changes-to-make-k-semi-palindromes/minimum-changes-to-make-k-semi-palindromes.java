class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int len = j - i + 1;
                int minChanges = len;
                for (int d = 1; d * d <= len; d++) {
                    if (len % d == 0) {
                        minChanges = Math.min(minChanges, getCost(s, i, j, d));
                        if (d * d != len && d != 1) {
                            minChanges = Math.min(minChanges, getCost(s, i, j, len / d));
                        }
                    }
                }
                cost[i][j] = minChanges;
            }
        }

        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            java.util.Arrays.fill(dp[i], 1000000);
        }
        dp[0][0] = 0;

        for (int j = 1; j <= k; j++) {
            for (int i = 1; i <= n; i++) {
                for (int p = (j - 1) * 2; p < i - 1; p++) {
                    dp[i][j] = Math.min(dp[i][j], dp[p][j - 1] + cost[p][i - 1]);
                }
            }
        }

        return dp[n][k];
    }

    private int getCost(String s, int start, int end, int d) {
        int changes = 0;
        int groupLen = (end - start + 1) / d;
        for (int i = 0; i < d; i++) {
            for (int left = 0, right = groupLen - 1; left < right; left++, right--) {
                if (s.charAt(start + i + left * d) != s.charAt(start + i + right * d)) {
                    changes++;
                }
            }
        }
        return changes;
    }
}