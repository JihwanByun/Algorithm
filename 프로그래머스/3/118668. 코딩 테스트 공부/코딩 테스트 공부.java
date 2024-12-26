import java.util.*;

class Solution {
    public int solution(int alp, int cop, int[][] problems) {
        int maxAlp = alp;
        int maxCop = cop;
        
        // 최대 알고력, 코딩력 계산
        for(int[] p : problems) {
            maxAlp = Math.max(maxAlp, p[0]);
            maxCop = Math.max(maxCop, p[1]);
        }
        
        // 초기 알고력/코딩력이 목표보다 높을 경우 처리
        alp = Math.min(alp, maxAlp);
        cop = Math.min(cop, maxCop);
        
        // DP 배열 초기화
        int[][] dp = new int[maxAlp + 1][maxCop + 1];
        for(int i = 0; i <= maxAlp; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[alp][cop] = 0;
        
        // DP로 최소 시간 계산
        for(int i = alp; i <= maxAlp; i++) {
            for(int j = cop; j <= maxCop; j++) {
                if(dp[i][j] == Integer.MAX_VALUE) continue;
                
                // 알고력 1 증가
                if(i + 1 <= maxAlp) {
                    dp[i + 1][j] = Math.min(dp[i + 1][j], dp[i][j] + 1);
                }
                
                // 코딩력 1 증가
                if(j + 1 <= maxCop) {
                    dp[i][j + 1] = Math.min(dp[i][j + 1], dp[i][j] + 1);
                }
                
                // 문제 해결
                for(int[] p : problems) {
                    if(i >= p[0] && j >= p[1]) {
                        int nextAlp = Math.min(maxAlp, i + p[2]);
                        int nextCop = Math.min(maxCop, j + p[3]);
                        dp[nextAlp][nextCop] = Math.min(
                            dp[nextAlp][nextCop], 
                            dp[i][j] + p[4]
                        );
                    }
                }
            }
        }
        
        return dp[maxAlp][maxCop];
    }
}