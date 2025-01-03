class Solution {
    public int solution(int m, int n, int[][] puddles) {
        int answer = 0;
        int DIVIDE = 1000000007;
        boolean[][] isPuddles = new boolean[n+1][m+1];
        int[][] dp = new int [n+1][m+1];
        
        for(int[] puddle : puddles){
            isPuddles[puddle[1]][puddle[0]] = true;
        }
        
        dp[0][1] = 1;
        for(int j = 1 ; j <= m ; j++){
            for(int i = 1 ; i<= n ; i++){
                
                if(isPuddles[i][j]) continue;
                
                dp[i][j] = (dp[i-1][j] + dp[i][j-1]) % DIVIDE;
            }
        }
        
        return dp[n][m];
    }
}