/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        // Base case: if the list is empty or has only one node without a cycle
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Traverse until fast reaches the end of the list
        while (fast != null && fast.next != null) {
            slow = slow.next;          // Moves 1 step
            fast = fast.next.next;     // Moves 2 steps
            
            // If they meet, there is a cycle
            if (slow == fast) {
                return true;
            }
        }
        
        // If fast reaches null, the list terminates (no cycle)
        return false;
    }
}