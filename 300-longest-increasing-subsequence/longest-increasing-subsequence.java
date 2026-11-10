import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Solution {
    public int lengthOfLIS(int[] nums) {
        List<Integer> tails = new ArrayList<>();
        
        for (int num : nums) {
            int idx = Collections.binarySearch(tails, num);
            // If element is not present, binarySearch returns(-(insertion point) - 1)
            if (idx < 0) {
                idx = -idx - 1;
            }
            
            // If num is larger than all elements in tails, append it
            if (idx == tails.size()) {
                tails.add(num);
            } else {
                // Otherwise, replace the first element that is >= num
                tails.set(idx, num);
            }
        }
        
        return tails.size();
    }
}