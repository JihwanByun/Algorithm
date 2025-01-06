import java.util.*;

class Solution {
    public int solution(int[] A, int[] B) {
        int n = A.length;
        
        Integer[] wrapperA = Arrays.stream(A).boxed().toArray(Integer[]::new);
        Integer[] wrapperB = Arrays.stream(B).boxed().toArray(Integer[]::new);
        
        
        Arrays.sort(wrapperA, Comparator.reverseOrder());
        Arrays.sort(wrapperB, Comparator.reverseOrder());
        
        int win = 0 ;
        int bIdx = 0;
        for(Integer num : wrapperA){
            if(wrapperB[bIdx] > num){
                win++;
                bIdx++;
            }
        }
        
        return win;
    }
}