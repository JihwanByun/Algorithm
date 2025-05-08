import java.util.*;

public class Solution {
    public int solution(int n, int[][] results) {
        List<List<Integer>> winGraph = new ArrayList<>();
        List<List<Integer>> loseGraph = new ArrayList<>();

        // 그래프 초기화
        for (int i = 0; i <= n; i++) {
            winGraph.add(new ArrayList<>());
            loseGraph.add(new ArrayList<>());
        }

        // 경기 결과 저장
        for (int[] result : results) {
            int winner = result[0];
            int loser = result[1];
            winGraph.get(winner).add(loser); // 이긴 선수 목록
            loseGraph.get(loser).add(winner); // 진 선수 목록
        }

        int answer = 0;

        for (int i = 1; i <= n; i++) {
            int winCount = bfs(i, winGraph, n);
            int loseCount = bfs(i, loseGraph, n);

            if (winCount + loseCount == n - 1) {
                answer++;
            }
        }

        return answer;
    }

    private int bfs(int start, List<List<Integer>> graph, int n) {
        boolean[] visited = new boolean[n + 1];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        visited[start] = true;
        int count = 0;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            for (int next : graph.get(current)) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                    count++;
                }
            }
        }

        return count;
    }
}
