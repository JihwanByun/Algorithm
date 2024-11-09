import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt(); // 항아리 수
        int m = sc.nextInt(); // inseparable pair 수
        int k = sc.nextInt(); // 지하에 배치할 항아리 수

        // 그래프 초기화
        List<Integer>[] graph = new ArrayList[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        // inseparable pairs 입력
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            graph[x].add(y);
            graph[y].add(x);
        }

        // 각 연결 요소의 크기를 저장할 리스트
        List<Integer> componentSizes = new ArrayList<>();
        boolean[] visited = new boolean[n + 1];

        // 연결 요소별로 크기를 계산
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                int size = dfs(i, graph, visited);
                componentSizes.add(size);
            }
        }

        // Knapsack-like 문제로 해결: 각 요소 크기로 정확히 k를 만들 수 있는지 확인
        boolean[] dp = new boolean[k + 1];
        dp[0] = true;

        for (int size : componentSizes) {
            for (int j = k; j >= size; j--) {
                dp[j] = dp[j] || dp[j - size];
            }
        }

        System.out.println(dp[k] ? "SAFE" : "DOOMED");
    }

    // DFS를 통해 연결 요소의 크기를 계산
    private static int dfs(int node, List<Integer>[] graph, boolean[] visited) {
        visited[node] = true;
        int size = 1;

        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                size += dfs(neighbor, graph, visited);
            }
        }
        return size;
    }
}