import java.util.*;

class Solution {
    
    public static int N;
    
    public int[] solution(int n, int[][] roads, int[] sources, int destination) {
        List<Integer> answer = new ArrayList<>();
        N = n;
        List<Integer>[] graph = new ArrayList[n+1];
        
        for(int i = 1 ; i <= n ; i++){
            graph[i] = new ArrayList<>();
        }
        
        for(int[] road : roads){
            graph[road[0]].add(road[1]);
            graph[road[1]].add(road[0]);
        }
        
        boolean[] visited = new boolean[N+1];
        visited[destination] = true;
        int[] dp = new int[N+1];
            
        Arrays.fill(dp, Integer.MAX_VALUE);
        Queue<Node> q = new LinkedList<>();
        dp[destination] = 0;
        q.add(new Node(destination, 0));
            
        while(!q.isEmpty()){
                
            Node cur = q.poll();
            
            for(int to : graph[cur.road]){
                if(visited[to] && dp[to] < cur.time + 1) continue;
                visited[to] = true;
                dp[to] = cur.time+1;
                q.add(new Node(to, cur.time+1));
            }
        }
        
        for(int i = 0 ; i < sources.length ; i++){
            if(dp[sources[i]] == Integer.MAX_VALUE) answer.add(-1);
            else answer.add(dp[sources[i]]);
        }
        return answer.stream().mapToInt(i -> i).toArray();
    }
}

class Node{
    
    int road;
    int time;
    
    Node(int road, int time){
        this.road = road;
        this.time = time;
    }
}