class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        long ans = 0, cost = 0;
        int j = n - 1;
        Deque<int[]> dq = new ArrayDeque<>();

        for (int i = n - 1; i >= 0; i--) {
            int count = 1;
            while (!dq.isEmpty() && dq.peekFirst()[0] < nums[i]) {
                int[] front = dq.pollFirst();
                cost += (long) (nums[i] - front[0]) * front[1];
                count += front[1];
            }
            dq.addFirst(new int[]{nums[i], count});

            while (cost > k) {
                int[] back = dq.peekLast();
                cost -= back[0] - nums[j];
                if (--back[1] == 0) {
                    dq.pollLast();
                }
                j--;
            }

            ans += j - i + 1;
        }

        return ans;
    }
}