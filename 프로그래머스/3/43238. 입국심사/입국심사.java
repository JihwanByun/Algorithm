import java.util.*;

class Solution {
    public long solution(int n, int[] times) {
        
        Arrays.sort(times);
        
        long right = (long) times[times.length - 1] * n ;
        long left = (long) times[0];
        
        long answer = Long.MAX_VALUE;
        
        while(right >= left){
            
            long mid = (right + left) / 2;
            long people = 0;
            
            for(int time: times){
                people += mid / time;
            }
            
            if(people >= n){
                right = mid - 1;
                answer = Math.min(answer, mid);
            } else {
                left = mid + 1;
            } 
        }
        
        return answer;
    }
}