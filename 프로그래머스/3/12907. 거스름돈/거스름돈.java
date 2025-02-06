import java.util.*;

class Solution {
    public int solution(int n, int[] money) {
        int[] dp = new int[n + 1];
        dp[0] = 1; // 0원을 만드는 경우의 수는 1 (아무 동전도 사용하지 않는 경우)
        
        for (int coin : money) { // 각 동전별로 반복
            for (int i = coin; i <= n; i++) { 
                dp[i] += dp[i - coin] % 1000000007; // 현재 금액(i)에서 coin을 뺐을 때 남은 금액의 방법 수를 더함
            }
        }
        
        return dp[n]; // n원을 만드는 경우의 수 반환
    }
}
