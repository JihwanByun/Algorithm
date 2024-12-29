import java.util.*;
class Solution {
    static int[] dr = {0,0,1,-1}; //가로 세로
    static int[] dc = {1,-1,0,0};
    static int n;
    
    public int solution(int[][] board) {
        n = board.length;
        int[][][] dp = new int[n][n][4]; // 각 위치에서 각 방향별 최소 비용
        
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                for(int k = 0; k < 4; k++) {
                    dp[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }
        
        // 시작점 초기화
        for(int i = 0; i < 4; i++) {
            dp[0][0][i] = 0;
        }
        
        PriorityQueue<Node> pq = new PriorityQueue<>((a,b) -> a.cost - b.cost);
        pq.add(new Node(0,0,0,0)); //가로방향
        pq.add(new Node(0,0,0,2)); //세로방향
        
        while(!pq.isEmpty()) {
            Node cur = pq.poll();
            
            if(dp[cur.r][cur.c][cur.dir] < cur.cost) continue;
            
            for(int dir = 0; dir < 4; dir++) {
                int newR = cur.r + dr[dir];
                int newC = cur.c + dc[dir];
                
                if(newR < 0 || newC < 0 || newR >= n || newC >= n) continue;
                if(board[newR][newC] == 1) continue;
                
                int newCost = cur.cost + 100;
                if((cur.dir < 2 && dir >= 2) || (cur.dir >= 2 && dir < 2)) {
                    newCost += 500; // 코너를 도는 경우
                }
                
                if(dp[newR][newC][dir] > newCost) {
                    dp[newR][newC][dir] = newCost;
                    pq.add(new Node(newR, newC, newCost, dir));
                }
            }
        }
        
        // 도착점에서의 최소 비용 찾기
        int answer = Integer.MAX_VALUE;
        for(int i = 0; i < 4; i++) {
            if(dp[n-1][n-1][i] != Integer.MAX_VALUE) {
                answer = Math.min(answer, dp[n-1][n-1][i]);
            }
        }
        
        return answer;
    }
}

class Node {
    int r, c, cost, dir;
    
    Node(int r, int c, int cost, int dir) {
        this.r = r;
        this.c = c;
        this.cost = cost;
        this.dir = dir;
    }
}