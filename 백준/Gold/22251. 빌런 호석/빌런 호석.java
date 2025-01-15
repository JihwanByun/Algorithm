import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int[][] nums = {
            {1,1,1,0,1,1,1}, // 0
            {0,0,1,0,0,0,1}, // 1
            {0,1,1,1,1,1,0}, // 2
            {0,1,1,1,0,1,1}, // 3
            {1,0,1,1,0,0,1}, // 4
            {1,1,0,1,0,1,1}, // 5
            {1,1,0,1,1,1,1}, // 6
            {0,1,1,0,0,0,1}, // 7
            {1,1,1,1,1,1,1}, // 8
            {1,1,1,1,0,1,1}  // 9
    };
    static int n, k, p;
    static int answer = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt(); // maximum floor
        k = sc.nextInt(); // number of digits
        p = sc.nextInt(); // number of LED flips allowed
        String x = sc.next(); // current floor

        // Convert current floor to array of digits
        int[] curFloor = new int[k];
        for (int i = 0; i < k; i++) {
            curFloor[i] = i < k - x.length() ? 0 : x.charAt(i - (k - x.length())) - '0';
        }

        dfs(curFloor, 0, 0);

        // Subtract 1 if the original number is counted
        System.out.println(answer - 1);
    }

    private static void dfs(int[] curFloor, int digit, int flips) {
        if (digit == k) {
            // Check if the number is valid (within range and not the same as original)
            int num = 0;
            for (int i = 0; i < k; i++) {
                num = num * 10 + curFloor[i];
            }
            if (num > 0 && num <= n) {
                answer++;
            }
            return;
        }

        // Try all possible digits at current position
        int originalDigit = curFloor[digit];
        for (int newDigit = 0; newDigit <= 9; newDigit++) {
            int requiredFlips = countFlips(originalDigit, newDigit);
            if (flips + requiredFlips <= p) {
                curFloor[digit] = newDigit;
                dfs(curFloor, digit + 1, flips + requiredFlips);
                curFloor[digit] = originalDigit; // backtrack
            }
        }
    }

    private static int countFlips(int from, int to) {
        int count = 0;
        // Compare each LED segment
        for (int i = 0; i < 7; i++) {
            if (nums[from][i] != nums[to][i]) {
                count++;
            }
        }
        return count;
    }
}