class Solution {
    public String gcdOfStrings(String str1, String str2) {
        // If concatenating in different orders doesn't match, no common divisor string exists
        if (!(str1 + str2).equals(str2 + str1)) {
            return "";
        }
        
        // Find the Greatest Common Divisor of the lengths of str1 and str2
        int gcdLength = gcd(str1.length(), str2.length());
        
        // The prefix of length gcdLength is our answer
        return str1.substring(0, gcdLength);
    }
    
    // Helper method to compute GCD using Euclidean algorithm
    private int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}