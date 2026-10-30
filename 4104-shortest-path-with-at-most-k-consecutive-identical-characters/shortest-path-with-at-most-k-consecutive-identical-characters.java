import java.util.*;

class Solution {
    public int shortestPath(int n, int[][] edges, String labels, int k) {
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }
        for (int[] edge : edges) {
            adj[edge[0]].add(new int[]{edge[1], edge[2]});
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        int startCharCount = 1;
        pq.offer(new int[]{0, 0, startCharCount});

        int[][] dist = new int[n][k + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], Integer.MAX_VALUE);
        }
        dist[0][startCharCount] = 0;

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int d = curr[0];
            int u = curr[1];
            int count = curr[2];

            if (u == n - 1) {
                return d;
            }

            if (d > dist[u][count]) {
                continue;
            }

            for (int[] neighbor : adj[u]) {
                int v = neighbor[0];
                int weight = neighbor[1];
                
                char nextChar = labels.charAt(v);
                char currChar = labels.charAt(u);
                
                int nextCount;
                if (nextChar == currChar) {
                    nextCount = count + 1;
                } else {
                    nextCount = 1;
                }

                if (nextCount <= k) {
                    if (d + weight < dist[v][nextCount]) {
                        dist[v][nextCount] = d + weight;
                        pq.offer(new int[]{dist[v][nextCount], v, nextCount});
                    }
                }
            }
        }

        int ans = Integer.MAX_VALUE;
        for (int c = 1; c <= k; c++) {
            ans = Math.min(ans, dist[n - 1][c]);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}