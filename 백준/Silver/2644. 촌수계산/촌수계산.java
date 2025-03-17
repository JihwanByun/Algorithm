import java.util.*;

public class Main {
    static List<List<Integer>> graph = new ArrayList<>();
    static boolean[] visited;
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt(); // 전체 사람 수
        int personA = sc.nextInt(); // 촌수를 계산할 첫 번째 사람
        int personB = sc.nextInt(); // 촌수를 계산할 두 번째 사람
        int m = sc.nextInt(); // 부모 자식 관계의 개수
        
        // 그래프 초기화
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (int i = 0; i < m; i++) {
            int parent = sc.nextInt();
            int child = sc.nextInt();
            graph.get(parent).add(child);
            graph.get(child).add(parent); // 양방향 관계
        }
        
        visited = new boolean[n + 1];
        int result = bfs(personA, personB);
        System.out.println(result);
    }
    
    static int bfs(int start, int target) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{start, 0}); // {현재 노드, 촌수}
        visited[start] = true;
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int node = current[0];
            int depth = current[1];
            
            if (node == target) return depth; // 목표 노드 도달 시 촌수 반환
            
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(new int[]{neighbor, depth + 1});
                }
            }
        }
        
        return -1; // 관계가 없을 경우 -1 반환
    }
}
