class Solution {
    public int solution(int n, int[] stations, int w) {
        int answer = 0;
        int pre = 0;
        
        for(int station : stations){
          int mod =  (station - w -1 - pre) % (w*2+1);
          int divide = (station - w -1 - pre) / (w*2+1) ;
             
          if(mod > 0) answer += divide+1 ;
            else answer += divide;
            
            pre = station + w  ;
        }
        
        int divide = (n - pre) / (w*2+1);
        
         if((n - pre) % (w*2+1) > 0) answer += divide+1 ;
            else answer += divide;
        

        return answer;
    }
}