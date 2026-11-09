import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        int n = arrival.length;
        PriorityQueue<int[]> busyServers = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        TreeSet<Integer> availableServers = new TreeSet<>();
        
        for (int i = 0; i < k; i++) {
            availableServers.add(i);
        }
        
        int[] requestCount = new int[k];
        int maxRequests = 0;
        
        for (int i = 0; i < n; i++) {
            int startTime = arrival[i];
            int endTime = startTime + load[i];
            
            // Free up servers that have finished their tasks by the current arrival time
            while (!busyServers.isEmpty() && busyServers.peek()[0] <= startTime) {
                availableServers.add(busyServers.poll()[1]);
            }
            
            // If no servers are available, drop the request
            if (availableServers.isEmpty()) {
                continue;
            }
            
            // Find the first available server >= (i % k)
            Integer serverId = availableServers.ceiling(i % k);
            if (serverId == null) {
                serverId = availableServers.first(); // Wrap around
            }
            
            availableServers.remove(serverId);
            busyServers.offer(new int[]{endTime, serverId});
            
            requestCount[serverId]++;
            maxRequests = Math.max(maxRequests, requestCount[serverId]);
        }
        
        // Collect all servers that handled the maximum number of requests
        List<Integer> busiest = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (requestCount[i] == maxRequests) {
                busiest.add(i);
            }
        }
        
        return busiest;
    }
}