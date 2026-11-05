import java.util.Arrays;

class Solution {
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        // Sort arr2 to enable binary search
        Arrays.sort(arr2);
        int distanceValue = 0;

        for (int x : arr1) {
            if (!hasElementInRange(arr2, x - d, x + d)) {
                distanceValue++;
            }
        }

        return distanceValue;
    }

    // Helper method to check if any element in arr2 falls within [low, high]
    private boolean hasElementInRange(int[] arr2, int low, int high) {
        int left = 0, right = arr2.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr2[mid] >= low && arr2[mid] <= high) {
                return true; // Found an element within the forbidden range
            } else if (arr2[mid] < low) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
}