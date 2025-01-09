import java.util.*;

class Solution {
    public boolean solution(int n, int[][] path, int[][] order) {
        // 그래프 초기화
        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        for (int[] link : path) {
            graph[link[0]].add(link[1]);
            graph[link[1]].add(link[0]);
        }

        // 선행 조건 초기화
        Map<Integer, Integer> preMap = new HashMap<>();
        Map<Integer, Integer> unlockMap = new HashMap<>();
        for (int[] ord : order) {
            if (ord[1] == 0) return false; // 시작점이 후행 조건이면 불가능
            preMap.put(ord[1], ord[0]);
            unlockMap.put(ord[0], ord[1]);
        }

        // BFS 탐색
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n];
        Set<Integer> lockedRooms = new HashSet<>();

        queue.offer(0);
        visited[0] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            // 잠금 해제 처리
            if (unlockMap.containsKey(current)) {
                int unlockRoom = unlockMap.get(current);
                if (lockedRooms.contains(unlockRoom)) {
                    lockedRooms.remove(unlockRoom);
                    queue.offer(unlockRoom);
                    visited[unlockRoom] = true;
                }
            }

            // 이웃 탐색
            for (int neighbor : graph[current]) {
                if (visited[neighbor]) continue;

                // 선행 조건 확인
                if (preMap.containsKey(neighbor)) {
                    int preRoom = preMap.get(neighbor);
                    if (!visited[preRoom]) {
                        lockedRooms.add(neighbor); // 선행 조건이 충족되지 않음
                        continue;
                    }
                }

                // 선행 조건이 없거나 충족된 경우 방문
                queue.offer(neighbor);
                visited[neighbor] = true;
            }
        }

        // 모든 방 방문 여부 확인
        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }
}
