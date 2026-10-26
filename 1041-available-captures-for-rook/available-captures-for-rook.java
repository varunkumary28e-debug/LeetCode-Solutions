class Solution {
    public int numRookCaptures(char[][] board) {
        int rx = 0, ry = 0;
        
        // Step 1: Find the position of the white rook ('R')
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 'R') {
                    rx = i;
                    ry = j;
                    break;
                }
            }
        }
        
        // Step 2: Define directions (up, down, left, right)
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int count = 0;
        
        // Step 3: Traverse in each of the 4 directions
        for (int[] dir : directions) {
            int x = rx;
            int y = ry;
            
            while (true) {
                x += dir[0];
                y += dir[1];
                
                // Check boundaries
                if (x < 0 || x >= 8 || y < 0 || y >= 8) {
                    break;
                }
                
                // If it hits a bishop, it's blocked
                if (board[x][y] == 'B') {
                    break;
                }
                
                // If it hits a pawn, it attacks it and stops in this direction
                if (board[x][y] == 'p') {
                    count++;
                    break;
                }
            }
        }
        
        return count;
    }
}