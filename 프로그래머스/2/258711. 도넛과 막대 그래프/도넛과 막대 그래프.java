import java.util.*;

class Solution {
    public int[] solution(int[][] edges) {
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Integer> outDegree = new HashMap<>();
        Set<Integer> allNodes = new HashSet<>();
        
        // 간선 정보 처리
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            
            outDegree.put(from, outDegree.getOrDefault(from, 0) + 1);
            inDegree.put(to, inDegree.getOrDefault(to, 0) + 1);
            
            allNodes.add(from);
            allNodes.add(to);
        }
        
        // 생성된 정점 찾기
        int createdNode = -1;
        for (int node : allNodes) {
            if (outDegree.getOrDefault(node, 0) >= 2 && inDegree.getOrDefault(node, 0) == 0) {
                createdNode = node;
                break;
            }
        }
        
        // 그래프 구성 (생성 정점에서 나가는 간선은 제외)
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            int from = edge[0], to = edge[1];
            if (from == createdNode) continue;
            graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
        }

        // 전체 방문 처리용
        Set<Integer> visited = new HashSet<>();

        // 도넛 / 막대 / 8자
        int donut = 0, bar = 0, eight = 0;

        // 생성 정점에서 나가는 간선들을 따라 분류
        for (int[] edge : edges) {
            if (edge[0] != createdNode) continue;
            int start = edge[1];
            if (visited.contains(start)) continue;

            int[] result = classifyGraph(start, graph, visited);

            int v = result[0], e = result[1], b = result[2];
            if (v == e) donut++;
            else if (v == e + 1) bar++;
            else if (v + 1 == e && b > 0) eight++;
        }

        return new int[]{createdNode, donut, bar, eight};
    }

    private int[] classifyGraph(int start, Map<Integer, List<Integer>> graph, Set<Integer> visited) {
        Set<String> visitedEdges = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start);
        
        int vertexCount = 0;
        int edgeCount = 0;
        int branchCount = 0;

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (visited.contains(curr)) continue;
            visited.add(curr);
            vertexCount++;

            List<Integer> neighbors = graph.getOrDefault(curr, Collections.emptyList());
            edgeCount += neighbors.size();
            if (neighbors.size() >= 2) branchCount++;

            for (int next : neighbors) {
                String edge = curr + "->" + next;
                if (visitedEdges.contains(edge)) continue;
                visitedEdges.add(edge);
                queue.offer(next);
            }
        }

        return new int[]{vertexCount, edgeCount, branchCount};
    }
}
