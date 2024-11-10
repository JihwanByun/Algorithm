import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        int k = sc.nextInt();

        List<Integer>[] graph = new List[n+1];

        for(int i = 1 ; i <= n ; i++){
            graph[i] = new ArrayList<>();
        }


        for(int i = 0 ; i < m; i++){
            int num1 = sc.nextInt();
            int num2 = sc.nextInt();

            graph[num1].add(num2);
            graph[num2].add(num1);
        }

        //독립적인 Pairs들의 수를 세기
        boolean[] visited =new boolean[n+1];
        List<Integer> sizes = new ArrayList<>();

        for(int i = 1 ; i<= n ;i++){

            if(!visited[i]){
                int size;
                size = dfs(graph,visited,i,0);
                sizes.add(size);
            }
        }

        boolean[] dp = new boolean[n+1];
        dp[0] = true;

        for(int i = 0 ; i < sizes.size(); i++){
            for(int j = k ; j >= sizes.get(i) ;j--){
                dp[j] = dp[j] || dp[j-sizes.get(i)];
            }
        }
        System.out.println(dp[k] ? "SAFE" : "DOOMED");
    }


    public static int dfs(List<Integer>[] graph, boolean[] visited, int idx, int cnt) {
        visited[idx] = true; // 현재 노드를 방문 표시
        cnt++; // 현재 노드를 포함하여 개수를 증가

        for (int a : graph[idx]) {
            if (!visited[a]) {
                cnt = dfs(graph, visited, a, cnt); // 반환된 cnt를 누적
            }
        }
        return cnt; // 최종 누적된 cnt 반환
    }

}