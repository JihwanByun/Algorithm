import java.util.*;

class Solution {
    public int solution(int n, int[][] computers) {
        int answer = 0;
        
        boolean[][] visited =new boolean[n][n];
        
        for(int i = 0 ; i < n  ;i++){
            for(int j = 0 ; j< n ;j++){
                if(i == j || visited[i][j]) continue;
                
                if(computers[i][j] == 1){
                    findNetwork(i,j,visited, computers);
                    answer++;
                }
            }
        }
        
        for(int i = 0 ; i < n ; i++){
            for(int j = 0 ; j< n ; j++){
                if(i == j && !visited[i][j])
                    answer++;
            }
        }
        
        return answer;
    }
    static void findNetwork(int i, int j, boolean[][] visited, int[][] computers){
        Queue<Integer> q = new LinkedList<>();
        visited[i][j] = true;
        q.add(j);
        
        while(!q.isEmpty()){
            int cur = q.poll();
            
            for(int k = 0 ; k< computers[cur].length; k++){
                if(visited[cur][k]) continue;
                
                if(computers[cur][k] == 1){
                    visited[cur][k] = true;
                    q.add(k);
                }
            
            }
            
        }
    }
}