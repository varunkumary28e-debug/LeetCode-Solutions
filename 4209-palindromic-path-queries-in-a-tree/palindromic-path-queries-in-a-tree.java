import java.util.*;

class Solution {
    private int[] head, heavy, depth, parent, pos, subtreeSize;
    private int curPos;
    private int[] tree;
    private char[] sChars;
    private List<List<Integer>> adj;

    private int dfs(int u, int p, int d, List<List<Integer>> graph) {
        depth[u] = d;
        parent[u] = p;
        subtreeSize[u] = 1;
        int maxSub = 0;
        heavy[u] = -1;
        for (int v : graph.get(u)) {
            if (v != p) {
                int sub = dfs(v, u, d + 1, graph);
                subtreeSize[u] += sub;
                if (sub > maxSub) {
                    maxSub = sub;
                    heavy[u] = v;
                }
            }
        }
        return subtreeSize[u];
    }

    private void decompose(int u, int h, List<List<Integer>> graph) {
        head[u] = h;
        pos[u] = curPos++;
        
        if (heavy[u] != -1) {
            decompose(heavy[u], h, graph);
        }
        
        for (int v : graph.get(u)) {
            if (v != parent[u] && v != heavy[u]) {
                decompose(v, v, graph);
            }
        }
    }

    private void updateTree(int node, int start, int end, int idx, int val) {
        if (start == end) {
            tree[node] = val;
            return;
        }
        int mid = (start + end) / 2;
        if (start <= idx && idx <= mid) {
            updateTree(2 * node, start, mid, idx, val);
        } else {
            updateTree(2 * node + 1, mid + 1, end, idx, val);
        }
        tree[node] = tree[2 * node] ^ tree[2 * node + 1];
    }

    private int queryTree(int node, int start, int end, int l, int r) {
        if (r < start || end < l) {
            return 0;
        }
        if (l <= start && end <= r) {
            return tree[node];
        }
        int mid = (start + end) / 2;
        return queryTree(2 * node, start, mid, l, r) ^ queryTree(2 * node + 1, mid + 1, end, l, r);
    }

    private int queryPath(int u, int v, int n) {
        int res = 0;
        while (head[u] != head[v]) {
            if (depth[head[u]] < depth[head[v]]) {
                int temp = u; u = v; v = temp;
            }
            res ^= queryTree(1, 0, n - 1, pos[head[u]], pos[u]);
            u = parent[head[u]];
        }
        if (depth[u] > depth[v]) {
            int temp = u; u = v; v = temp;
        }
        res ^= queryTree(1, 0, n - 1, pos[u], pos[v]);
        return res;
    }

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        head = new int[n];
        heavy = new int[n];
        depth = new int[n];
        parent = new int[n];
        pos = new int[n];
        subtreeSize = new int[n];
        curPos = 0;

        dfs(0, -1, 0, adj);
        decompose(0, 0, adj);

        tree = new int[4 * n];
        sChars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int mask = 1 << (sChars[i] - 'a');
            updateTree(1, 0, n - 1, pos[i], mask);
        }

        List<Boolean> ans = new ArrayList<>();
        for (String qStr : queries) {
            String[] parts = qStr.split(" ");
            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);
                sChars[u] = c;
                int mask = 1 << (c - 'a');
                updateTree(1, 0, n - 1, pos[u], mask);
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int xorMask = queryPath(u, v, n);
                // A path can be rearranged into a palindrome if it has at most one bit set
                ans.add((xorMask & (xorMask - 1)) == 0);
            }
        }

        return ans;
    }
}