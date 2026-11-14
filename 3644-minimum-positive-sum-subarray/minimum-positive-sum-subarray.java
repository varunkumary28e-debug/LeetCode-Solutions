class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int minSum = Integer.MAX_VALUE;
        int n = nums.size();
        
        for (int length = l; length <= r; length++) {
            int currentSum = 0;
            for (int i = 0; i < length; i++) {
                currentSum += nums.get(i);
            }
            
            if (currentSum > 0) {
                minSum = Math.min(minSum, currentSum);
            }
            
            for (int i = length; i < n; i++) {
                currentSum += nums.get(i) - nums.get(i - length);
                if (currentSum > 0) {
                    minSum = Math.min(minSum, currentSum);
                }
            }
        }
        
        return minSum == Integer.MAX_VALUE ? -1 : minSum;
    }
}