class Solution {
    public void solve(char[][] board) {
        if (board == null || board.length == 0) return;
        
        int m = board.length;
        int n = board[0].length;
        
        // 1. Move along the boundary and start DFS for any 'O' found
        for (int i = 0; i < m; i++) {
            if (board[i][0] == 'O') dfs(board, i, 0);
            if (board[i][n - 1] == 'O') dfs(board, i, n - 1);
        }
        
        for (int j = 0; j < n; j++) {
            if (board[0][j] == 'O') dfs(board, 0, j);
            if (board[m - 1][j] == 'O') dfs(board, m - 1, j);
        }
        
        // 2. Iterate through the board to flip surrounded 'O's to 'X' 
        // and restore safe 'T's back to 'O'
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                } else if (board[i][j] == 'T') {
                    board[i][j] = 'O';
                }
            }
        }
    }
    
    private void dfs(char[][] board, int r, int c) {
        int m = board.length;
        int n = board[0].length;
        
        // Boundary check and if current cell is not an 'O'
        if (r < 0 || c < 0 || r >= m || c >= n || board[r][c] != 'O') {
            return;
        }
        
        // Mark as visited/safe
        board[r][c] = 'T';
        
        // Traverse all 4 directions
        dfs(board, r + 1, c);
        dfs(board, r - 1, c);
        dfs(board, r, c + 1);
        dfs(board, r, c - 1);
    }
}