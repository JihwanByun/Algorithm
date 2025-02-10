import java.util.*;

class Solution {
    public int solution(int[] money) {
        int n = money.length;
        int answer = 0;
        
        if(n == 3) {
            int tmp = Math.max(money[0], money[1]);
            answer = Math.max(tmp, money[2]);
            return answer;
        }
        int candidate1 = 0; 
        int[] dp = new int[n];
        dp[0] = money[0];
        dp[1] = 0;
        dp[2] = money[0] + money[2];
        candidate1 = dp[2];
        // 처음걸 포함하는 경우
        for(int i = 3 ; i < n-1 ; i++) {
            dp[i] = Math.max(dp[i-2] + money[i], dp[i-3] + money[i]);
            answer = Math.max(answer, dp[i]);
        }
        
        //처음걸 포함하지 않는 경우
        dp = new int[n];
        dp[0] = 0;
        dp[1] = money[1];
        dp[2] = money[2];
        int candidate2 = Math.max(dp[1], dp[2]);
        for(int i = 3 ; i < n ; i++) {
            dp[i] = Math.max(dp[i-2] + money[i], dp[i-3] + money[i]);
            candidate2 = Math.max(answer, dp[i]);
        }
        
        answer = Math.max(candidate1, candidate2);
        
        return answer;
    }
}