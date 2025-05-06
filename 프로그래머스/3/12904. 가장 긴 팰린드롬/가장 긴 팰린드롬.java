class Solution {
    public int solution(String s) {
        
        int answer = 1;
        
        char[] c = s.toCharArray();
        for(int i = 1 ; i < s.length() ; i++) {
            int cnt = 0;
            
            int l = i ; int r = i;
            
            while(l >= 0 && r < c.length && c[l] == c[r]) {
                l--;
                r++;
                cnt++;
            }
            answer = Math.max(cnt*2 - 1, answer);
            
            cnt = 0;
            l = i-1; r = i;
            while(l >= 0 && r < c.length && c[l] == c[r]) {
                l--;
                r++;
                cnt++;
            }
            
            answer = Math.max(cnt*2, answer);
        }
        
        return answer;
    }
}