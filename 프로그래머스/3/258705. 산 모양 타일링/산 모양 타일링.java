class Solution {
    public int solution(int n, int[] tops) {
        int MOD = 10007;
        int[] dp = new int[n+1];
        dp[0] = 1;
        if(tops[0] == 0) dp[1] = 3;
        else dp[1] = 4;
        
        for(int i = 1; i < n; i++) {
            if(tops[i] == 0) {
                dp[i+1] = ((dp[i] * 3) % MOD - dp[i-1] + MOD) % MOD;
            } else {
                dp[i+1] = ((dp[i] * 4) % MOD - dp[i-1] + MOD) % MOD;
            }
        }
                                    
        return dp[n];
    }
}