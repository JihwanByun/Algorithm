import java.util.*;

class Solution {
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
        Map<Integer, List<Edge>> graph = new HashMap<>();
        Set<Integer> gateSet = new HashSet<>();
        Set<Integer> summitSet = new HashSet<>();

        // 그래프 초기화
        for (int i = 1; i <= n; i++) {
            graph.put(i, new ArrayList<>());
        }
        for (int[] path : paths) {
            graph.get(path[0]).add(new Edge(path[1], path[2]));
            graph.get(path[1]).add(new Edge(path[0], path[2]));
        }
        for (int gate : gates) gateSet.add(gate);
        for (int summit : summits) summitSet.add(summit);

        // 우선순위 큐를 사용한 다익스트라 알고리즘
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.cost - b.cost);
        int[] minIntensity = new int[n + 1];
        Arrays.fill(minIntensity, Integer.MAX_VALUE);

        for (int gate : gates) {
            pq.add(new Node(gate, 0));
            minIntensity[gate] = 0;
        }

        int minSummit = Integer.MAX_VALUE;
        int minCost = Integer.MAX_VALUE;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            if (cur.cost > minIntensity[cur.num]) continue;
            if (summitSet.contains(cur.num)) {
                if (cur.cost < minCost || (cur.cost == minCost && cur.num < minSummit)) {
                    minCost = cur.cost;
                    minSummit = cur.num;
                }
                continue;
            }

            for (Edge edge : graph.get(cur.num)) {
                int nextCost = Math.max(cur.cost, edge.weight);
                if (nextCost < minIntensity[edge.to]) {
                    minIntensity[edge.to] = nextCost;
                    pq.add(new Node(edge.to, nextCost));
                }
            }
        }

        return new int[]{minSummit, minCost};
    }
}

class Node {
    int num, cost;
    Node(int num, int cost) {
        this.num = num;
        this.cost = cost;
    }
}

class Edge {
    int to, weight;
    Edge(int to, int weight) {
        this.to = to;
        this.weight = weight;
    }
}
