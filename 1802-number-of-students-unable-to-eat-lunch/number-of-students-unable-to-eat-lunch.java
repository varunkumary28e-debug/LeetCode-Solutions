import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

class Solution {
    public int countStudents(int[] students, int[] sandwiches) {
        Queue<Integer> queue = new LinkedList<>();
        for (int student : students) {
            queue.add(student);
        }

        // Push in reverse so sandwiches[0] is at the top (peek / pop)
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = sandwiches.length - 1; i >= 0; i--) {
            stack.push(sandwiches[i]);
        }

        int rotations = 0;

        while (!queue.isEmpty() && rotations < queue.size()) {
            if (queue.peek().equals(stack.peek())) {
                queue.poll();
                stack.pop();
                rotations = 0;
            } else {
                queue.add(queue.poll());
                rotations++;
            }
        }

        return queue.size();
    }
}