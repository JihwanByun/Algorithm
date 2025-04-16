import java.util.*;

class Solution {
    static List<int[]> darts = new ArrayList<>();
    static int[][] dp;
    
    public int[] solution(int target) {
        
        for(int i = 1 ; i <= 20 ; i++) {
            darts.add(new int[]{i,1});
            darts.add(new int[]{i*2,0});
            darts.add(new int[]{i*3,0});
        }
        darts.add(new int[]{50,1});
        
        dp = new int[target+1][2];
        
        for(int i = 1 ; i <= target; i++) {
            dp[i][0] = Integer.MAX_VALUE;
        }
    
        for(int num = 1 ; num <= target ; num++) {
            for(int[] dart : darts) {
                int val = dart[0];
                int single = dart[1];
                
                if(num >= val) {
                    int newVal = 1 + dp[num - val][0];
                    int newSingle = single + dp[num-val][1];
                    
                    if(newVal < dp[num][0] || (newVal == dp[num][0] && newSingle > dp[num][1])) {
                        dp[num][0] = newVal;
                        dp[num][1] = newSingle;
                    }
                }
            }    
        }
        
    
        return dp[target];
    }          
}