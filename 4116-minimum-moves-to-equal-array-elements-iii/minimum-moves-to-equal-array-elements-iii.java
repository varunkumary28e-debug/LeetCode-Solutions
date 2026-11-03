class Solution {
    public int minMoves(int[] nums) {
        int maxVal = nums[0];
        for (int num : nums) {
            if (num > maxVal) {
                maxVal = num;
            }
        }
        
        int totalMoves = 0;
        for (int num : nums) {
            totalMoves += (maxVal - num);
        }
        
        int[] test = new int[5]; // test array
        return totalMoves;
    }
}