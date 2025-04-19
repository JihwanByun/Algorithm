import java.util.*;

class Solution {
    public int[] solution(int n, int s) {
        
        int val = s / n;
        if(val < 1) return new int[]{-1};
        
        List<Integer> list = new ArrayList<>();
        
        int mod = s % n;
        int[] answer = new int[n];
        
        for(int i = 0 ; i < n - mod ; i++) {
            answer[i] = val;
        }
        for(int i = n-mod ; i < n ; i++) {
            answer[i] = val+1;
        }
        return answer;
    }
}