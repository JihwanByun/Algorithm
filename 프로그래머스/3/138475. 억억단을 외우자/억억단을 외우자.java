import java.util.*;

class Solution {
    public int[] solution(int e, int[] starts) {
        // Pre-calculate divisor counts for all numbers
        int[] divisorCounts = new int[e + 1];
        for (int i = 1; i <= e; i++) {
            for (int j = i; j <= e; j += i) {
                divisorCounts[j]++;
            }
        }
        
        // For each position, store the number with maximum divisors
        int[] maxDivisorNumbers = new int[e + 1];
        int maxCount = 0;
        int maxNum = e;
        
        // Iterate from right to left to handle the "smaller number wins" condition
        for (int i = e; i >= 1; i--) {
            if (divisorCounts[i] >= maxCount) {
                maxCount = divisorCounts[i];
                maxNum = i;
            }
            maxDivisorNumbers[i] = maxNum;
        }
        
        // Construct result array
        int[] result = new int[starts.length];
        for (int i = 0; i < starts.length; i++) {
            result[i] = maxDivisorNumbers[starts[i]];
        }
        
        return result;
    }
}