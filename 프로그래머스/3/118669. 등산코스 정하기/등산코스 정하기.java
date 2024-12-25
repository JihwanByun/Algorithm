import java.util.*;

class Solution {
    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {
       
        Map<Integer, List<Edge>> map = new HashMap<>();
        Set<Integer> gateSet = new HashSet<>();
        Set<Integer> summitSet = new HashSet<>();
        
        for(int i = 1 ; i <= n ; i++){
            map.put(i,new ArrayList<Edge>());
        }
        int[] intensityMin = new int[n+1];
        for(int gate : gates){
            gateSet.add(gate);
        }
        for(int summit : summits){
            summitSet.add(summit);
        }
        for(int[] path : paths){
            map.get(path[0]).add(new Edge(path[1], path[2]));
            map.get(path[1]).add(new Edge(path[0], path[2]));
        }
        
        
        int[] answer =new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE};
        
        for(int gate : gates){
            
            intensityMin = new int[n+1];
            Arrays.fill(intensityMin, Integer.MAX_VALUE);
            
            for(int gateNum : gates){
                intensityMin[gateNum] = 0;
            }
            
            PriorityQueue<Node> pq =new PriorityQueue<>((a,b) -> a.maxCost - b.maxCost);
            pq.add(new Node(gate, 0));
            
            while(!pq.isEmpty()){

                Node cur = pq.poll();
                
                if(cur.maxCost > answer[1]) continue;
                
                if(summitSet.contains(cur.num)){
                    if(answer[1] > cur.maxCost || (answer[1] == cur.maxCost && answer[0] > cur.num)){
                        answer[1] = cur.maxCost;
                        answer[0] = cur.num;
                    }
                    continue;
                }
                for(Edge edge : map.get(cur.num)){
                    if(edge.cost < intensityMin[edge.to]){
                       intensityMin[edge.to] = edge.cost;
                        pq.add(new Node(edge.to, Math.max(cur.maxCost,edge.cost)));
                    }
                }
            }
        }
        
        return answer;
    }
}
    
    class Node{
        int num;
        int maxCost;
            
        Node(int num, int maxCost){
            this.num = num;
            this.maxCost = maxCost;
        }
    }
        
    class Edge{
        int to;
        int cost;
            
         Edge(int to, int cost){
            this.to = to;
            this.cost = cost;
        }
    }
