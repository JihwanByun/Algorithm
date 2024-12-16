import java.util.*;

class Solution {
    public int solution(int[][] board, int[][] skill) {
        
        int answer = 0;
        int n = board.length;
        int m = board[0].length;
        
        int[][] preSum = new int[n+1][m+1];
        
        for(int[] s : skill){
            int power = s[5];
            if(s[0] == 1){
                power = -power;
            }
            preSum[s[1]][s[4] + 1] -= power;
            preSum[s[1]][s[2]] += power;
            preSum[s[3]+1][s[2]] -= power;
            preSum[s[3]+1][s[4]+1] += power;
        }
        
        for(int i = 0 ; i< n ; i++){
            for(int j = 0 ; j < m ; j++){
                preSum[i][j+1] += preSum[i][j]; 
            }
        }
        
        for(int j = 0 ; j < m ; j++){
            for(int i = 0 ; i< n; i++){
                preSum[i+1][j] += preSum[i][j];
            }
        }
        
        for(int i = 0 ; i< n ; i++){
            for(int j = 0 ; j < m ; j++){
                if(board[i][j] + preSum[i][j] > 0) answer++;
            }
        }
      
        return answer;
    }
}