import java.util.*;

class Solution {
    public int solution(int n, int[][] edge) {
        
        int[] minDistance = new int[n+1];
        Arrays.fill(minDistance, 50001);
        minDistance[1] = 0;
        
        List<Integer>[] list = new ArrayList[n+1];
        
        for(int i = 1 ; i <= n ; i++) {
            list[i] = new ArrayList<>();
        }
        
        for(int[] vertex : edge) {
            list[vertex[0]].add(vertex[1]);
            list[vertex[1]].add(vertex[0]);
        }
        
        int maxDistance = 0;
        
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(1,0));
        
        while(!q.isEmpty()) {
            Node cur = q.poll();
            
            for(int linkedNode : list[cur.num]) {
                if(minDistance[linkedNode] <= cur.distance + 1) continue;
                
                if(cur.distance + 1 > maxDistance) {
                    maxDistance = cur.distance + 1;
                }
                minDistance[linkedNode] = cur.distance + 1;
                q.add(new Node(linkedNode, cur.distance+1));
            }
        }
        
        int answer = 0;
        
        for(int i = 1 ; i <= n ; i++) {
            if(minDistance[i] == maxDistance) {
                answer++;
            }
            // System.out.print(minDistance[i] + " ");
        }
        
        return answer;
    }
}

class Node{
    int num;
    int distance;
    Node(int num, int distance) {
        this.num = num;
        this.distance = distance;
    }
}