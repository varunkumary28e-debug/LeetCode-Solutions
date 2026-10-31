class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        long MOD = 1_000_000_007;

        // dp[i][j] stores the number of unique valid paths to reach cell (i, j)
        long[][] dp = new long[m][n];
        dp[0][0] = 1;

        // Precompute next landing positions (jump tables) for moving Right and Down
        // rightJump[i][j] = destination {target_r, target_c} if moving right from (i, j)
        // downJump[i][j] = destination {target_r, target_c} if moving down from (i, j)
        int[][][] rightJump = new int[m][n][2];
        int[][][] downJump = new int[m][n][2];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rightJump[i][j] = getJump(grid, i, j, 0, 1); // initially trying to move right
                downJump[i][j] = getJump(grid, i, j, 1, 0);  // initially trying to move down
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == 0) continue;

                // 1. Try moving Right from (i, j)
                int[] rDest = rightJump[i][j];
                if (rDest[0] != -1) {
                    dp[rDest[0]][rDest[1]] = (dp[rDest[0]][rDest[1]] + dp[i][j]) % MOD;
                }

                // 2. Try moving Down from (i, j)
                int[] dDest = downJump[i][j];
                if (dDest[0] != -1) {
                    dp[dDest[0]][dDest[1]] = (dp[dDest[0]][dDest[1]] + dp[i][j]) % MOD;
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }

    // Resolves cascading reflections for a move starting from (r, c) with initial step (dr, dc)
    private int[] getJump(int[][] grid, int r, int c, int dr, int dc) {
        int m = grid.length;
        int n = grid[0].length;

        int currR = r + dr;
        int currC = c + dc;

        // Check if initial step goes out of bounds
        if (currR < 0 || currR >= m || currC < 0 || currC >= n) {
            return new int[] {-1, -1};
        }

        // Handle cascading reflections if the cell is a mirror (1)
        while (currR >= 0 && currR < m && currC >= 0 && currC < n && grid[currR][currC] == 1) {
            // If we entered the mirror while moving horizontally (dc != 0), we turn down (dr = 1, dc = 0)
            // If we entered while moving vertically (dr != 0), we turn right (dr = 0, dc = 1)
            if (dc != 0) {
                dr = 1;
                dc = 0;
            } else {
                dr = 0;
                dc = 1;
            }
            currR += dr;
            currC += dc;
        }

        // Final destination check
        if (currR < 0 || currR >= m || currC < 0 || currC >= n) {
            return new int[] {-1, -1};
        }

        return new int[] {currR, currC};
    }
}