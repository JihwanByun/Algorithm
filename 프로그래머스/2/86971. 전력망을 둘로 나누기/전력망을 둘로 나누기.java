import java.util.*;

class Solution {
        
    static int answer = 101;
    static List<Integer>[] edges;
    static boolean[] visited;
    static boolean[][] ignoreEdges;
    
    public int solution(int n, int[][] wires) {
        
        edges = new ArrayList[n+1];
        ignoreEdges = new boolean[n+1][n+1];
        
        for(int i = 1 ; i <= n ; i++) {
            edges[i] = new ArrayList<>();
        }
        
        for(int[] wire : wires) {
            edges[wire[0]].add(wire[1]);
            edges[wire[1]].add(wire[0]);
        }
        
        for(int i = 1 ; i <= n ; i++) {
            for(int j = 0 ; j < edges[i].size() ; j++) {
                visited = new boolean[n+1];
                ignoreEdges[i][edges[i].get(j)] = true;
                ignoreEdges[edges[i].get(j)][i] = true;
                dfs(edges[i], i);
                ignoreEdges[i][edges[i].get(j)] = false;
                ignoreEdges[edges[i].get(j)][i] = false;
                
                int cnt = 0;
                for(int k = 1 ; k <= n ; k++) {
                    if(visited[k]) cnt++; 
                }
                //System.out.println("노드 " + i + "이랑 " + edges[i].get(j) +"이랑 연결이 끊어질 때 " + cnt + "만큼 방문함");
                    
                answer = Math.min(answer, Math.abs((n - cnt) - cnt));
            }
        }
            
        return answer;
    }
    
    void dfs(List<Integer> edge, int node) {
        
        visited[node] = true;
        
        for(int i = 0; i < edge.size() ; i++) {
            if((!ignoreEdges[edge.get(i)][node] || !ignoreEdges[node][edge.get(i)])&& !visited[edge.get(i)]) {   
                dfs(edges[edge.get(i)], edge.get(i));
            }    
        }
    }
}