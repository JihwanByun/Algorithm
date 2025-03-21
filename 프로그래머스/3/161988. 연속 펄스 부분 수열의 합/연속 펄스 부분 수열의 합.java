class Solution {
    public long solution(int[] sequence) {
        
        int n = sequence.length;
        
        int[] perse1 = new int[n];
        int[] perse2 = new int[n];
        long[] sum1 = new long[n];
        long[] sum2 = new long[n];
        perse1[0] = 1;
        perse2[0] = -1;
        
        for(int i = 1  ; i < n ; i++) {
            perse1[i] = -perse1[i-1];
            perse2[i] = -perse2[i-1];
        }

         for(int i = 0 ; i < n ; i++) {
            perse1[i] = perse1[i] * sequence[i];
            perse2[i] = perse2[i] * sequence[i];
         }
        
        sum1[0] = perse1[0];
        sum2[0] = perse2[0];
        
        long answer = Math.max(sum1[0], sum2[0]);
        for(int i = 1 ; i < n ; i++) {
            sum1[i] = Math.max(sum1[i-1] + perse1[i], perse1[i]);
            sum2[i] = Math.max(sum2[i-1] + perse2[i], perse2[i]);
            
            long max = Math.max(sum1[i], sum2[i]);
            answer = Math.max(answer, max);
        }
        
        return answer;
    }
}