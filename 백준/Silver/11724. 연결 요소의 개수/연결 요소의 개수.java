import java.util.*;

public class Main {
    static List<Integer>[] graph;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 정점의 개수
        int M = sc.nextInt(); // 간선의 개수

        graph = new ArrayList[N + 1];
        visited = new boolean[N + 1];

        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph[u].add(v);
            graph[v].add(u);
        }

        int components = 0;
        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {
                dfs(i);
                components++;
            }
        }

        System.out.println(components);
    }

    static void dfs(int node) {
        Stack<Integer> stack = new Stack<>();
        stack.push(node);
        visited[node] = true;

        while (!stack.isEmpty()) {
            int current = stack.pop();
            for (int neighbor : graph[current]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    stack.push(neighbor);
                }
            }
        }
    }
}