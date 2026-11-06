import java.util.Arrays;

class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        int m = queries.length;
        int[][] sortedQueries = new int[m][2];
        for (int i = 0; i < m; i++) {
            sortedQueries[i][0] = queries[i];
            sortedQueries[i][1] = i;
        }
        
        Arrays.sort(sortedQueries, (a, b) -> Integer.compare(a[0], b[0]));
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));
        
        int[] ans = new int[m];
        int[] serverCounts = new int[n + 1];
        int activeServers = 0;
        
        int left = 0, right = 0;
        int logsLen = logs.length;
        
        for (int i = 0; i < m; i++) {
            int qTime = sortedQueries[i][0];
            int qIndex = sortedQueries[i][1];
            int windowStart = qTime - x;
            int windowEnd = qTime;
            
            while (right < logsLen && logs[right][1] <= windowEnd) {
                int serverId = logs[right][0];
                if (serverCounts[serverId] == 0) {
                    activeServers++;
                }
                serverCounts[serverId]++;
                right++;
            }
            
            while (left < logsLen && logs[left][1] < windowStart) {
                int serverId = logs[left][0];
                serverCounts[serverId]--;
                if (serverCounts[serverId] == 0) {
                    activeServers--;
                }
                left++;
            }
            
            ans[qIndex] = n - activeServers;
        }
        
        return ans;
    }
}