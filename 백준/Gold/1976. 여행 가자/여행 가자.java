import org.w3c.dom.ls.LSOutput;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Set<Integer> answerSet = new HashSet<>();
        Scanner sc =new Scanner(System.in);
        boolean canTravel = false;

        int n = sc.nextInt(); //도시 수
        int m = sc.nextInt(); //여행 계획에 속한 도시 수

        Set<Integer>[] graph = new HashSet[n+1];
        for(int i = 1 ; i <= n  ; i++){
            graph[i] = new HashSet<>();
        }

        for(int i = 1 ; i <= n ; i++){
            for(int j = 1; j <= n ; j++){
                boolean isConnected = sc.nextInt() == 1;
                if(isConnected){
                    graph[i].add(j);
                    graph[j].add(i);
                }
            }
        }

        for(int i = 0 ; i < m ; i++){
            answerSet.add(sc.nextInt());
        }
        for(int i = 1 ; i <= n ; i++){
            if(answerSet.contains(i)){
                canTravel = bfs(graph, answerSet, i);
                break;
            }
        }
        System.out.println(canTravel ? "YES" : "NO");
    }
    private static boolean bfs(Set<Integer>[] graph, Set<Integer> answerSet, int i){
        boolean[] visited = new boolean[graph.length];
        visited[i] = true;
        Queue<Integer> q = new LinkedList<>();
        q.add(i);

        while(!q.isEmpty()){
            int cur = q.poll();
            for(int country : graph[cur]) {
                if(visited[country]) continue;

                q.add(country);
                visited[country] =true;
            }
        }

        for(int mustGoCountry : answerSet){
            if(!visited[mustGoCountry]) return false;
        }

        return true;
    }
}