import java.util.*;

class Solution {
    public int solution(int[][] routes) {
        int n = routes.length;
        
        for(int[] route : routes){
            if(route[0] > route[1]){
                int tmp = route[0];
                route[0] = route[1];
                route[1] = tmp;
            }
        }
        
        Arrays.sort(routes, (a,b) -> a[1] - b[1]);
        int answer = 1;
        int camera = routes[0][1];
        for(int i= 0 ; i < n ; i++){
            if(routes[i][0] > camera){
                camera = routes[i][1];
                answer++;
            }
        }
        
        return answer;
    }
}