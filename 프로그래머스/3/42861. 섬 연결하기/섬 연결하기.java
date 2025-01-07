import java.util.*;

class Solution {
    public int solution(int n, int[][] costs) {
        // Representing the graph as an adjacency list
        List<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        // Building the graph
        for (int[] cost : costs) {
            graph[cost[0]].add(new Edge(cost[1], cost[2]));
            graph[cost[1]].add(new Edge(cost[0], cost[2]));
        }

        // Prim's Algorithm
        boolean[] visited = new boolean[n];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
        int totalCost = 0;
        int edgesUsed = 0;

        // Start from node 0
        pq.addAll(graph[0]);
        visited[0] = true;

        while (!pq.isEmpty() && edgesUsed < n - 1) {
            Edge edge = pq.poll();
            if (!visited[edge.to]) {
                visited[edge.to] = true;
                totalCost += edge.cost;
                edgesUsed++;
                pq.addAll(graph[edge.to]);
            }
        }

        return edgesUsed == n - 1 ? totalCost : -1; // Return -1 if the graph is disconnected
    }
}

class Edge {
    int to, cost;

    Edge(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}
