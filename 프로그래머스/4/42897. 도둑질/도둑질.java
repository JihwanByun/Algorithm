class Solution {
    public int solution(int[] money) {
        int n = money.length;
           // case 1: 첫 집 포함, 마지막 집 제외
        int[] dp = new int[n];
        
        dp[0] = money[0];
        dp[1] = Math.max(money[0], money[1]);

        for (int i = 2; i < n - 1; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + money[i]);
        }
        int case1 = dp[n - 2];

        // case 2: 첫 집 제외, 마지막 집 포함
        dp[0] = 0;
        dp[1] = money[1];

        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + money[i]);
        }
        int case2 = dp[n - 1];

        return Math.max(case1, case2);
    }
}