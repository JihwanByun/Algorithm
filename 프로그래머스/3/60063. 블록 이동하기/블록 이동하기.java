import java.util.*;

class Solution {
    static int[][] directions = {{0,1}, {0,-1}, {-1,0}, {1,0}}; //우좌상하
    
    public int solution(int[][] board) {
        int answer = Integer.MAX_VALUE;
        int len = board.length;
        
        Queue<Robot> q = new LinkedList<>();
        boolean[][][][] visited = new boolean[len][len][len][len];
        
        // 초기 상태 방문 처리
        q.add(new Robot(0,0,0,1,0));
        visited[0][0][0][1] = true;
        visited[0][1][0][0] = true;  // 반대 방향도 방문 처리
        
        while(!q.isEmpty()) {
            Robot cur = q.poll();
            
            if(cur.time >= answer) continue;
            if((cur.r1 == len-1 && cur.c1 == len-1) || (cur.r2 == len-1 && cur.c2 == len-1)) {
                answer = Math.min(answer, cur.time);
                continue;
            }
            
            // 평행 이동
            for(int[] dir : directions) {
                int nr1 = cur.r1 + dir[0];
                int nc1 = cur.c1 + dir[1];
                int nr2 = cur.r2 + dir[0];
                int nc2 = cur.c2 + dir[1];
                
                if(!isValid(nr1,nc1,nr2,nc2,board)) continue;
                if(visited[nr1][nc1][nr2][nc2] || visited[nr2][nc2][nr1][nc1]) continue;
                
                visited[nr1][nc1][nr2][nc2] = visited[nr2][nc2][nr1][nc1] = true;
                q.add(new Robot(nr1,nc1,nr2,nc2,cur.time+1));
            }
            
            // 회전
            if(cur.r1 == cur.r2) { // 수평 상태
                // 위로 회전
                if(canRotate(cur.r1,cur.c1,cur.r2,cur.c2,-1,board)) {
                    // 왼쪽 축
                    if(!visited[cur.r1-1][cur.c1][cur.r1][cur.c1] && !visited[cur.r1][cur.c1][cur.r1-1][cur.c1]) {
                        visited[cur.r1-1][cur.c1][cur.r1][cur.c1] = visited[cur.r1][cur.c1][cur.r1-1][cur.c1] = true;
                        q.add(new Robot(cur.r1-1,cur.c1,cur.r1,cur.c1,cur.time+1));
                    }
                    // 오른쪽 축
                    if(!visited[cur.r2-1][cur.c2][cur.r2][cur.c2] && !visited[cur.r2][cur.c2][cur.r2-1][cur.c2]) {
                        visited[cur.r2-1][cur.c2][cur.r2][cur.c2] = visited[cur.r2][cur.c2][cur.r2-1][cur.c2] = true;
                        q.add(new Robot(cur.r2-1,cur.c2,cur.r2,cur.c2,cur.time+1));
                    }
                }
                
                // 아래로 회전
                if(canRotate(cur.r1,cur.c1,cur.r2,cur.c2,1,board)) {
                    // 왼쪽 축
                    if(!visited[cur.r1][cur.c1][cur.r1+1][cur.c1] && !visited[cur.r1+1][cur.c1][cur.r1][cur.c1]) {
                        visited[cur.r1][cur.c1][cur.r1+1][cur.c1] = visited[cur.r1+1][cur.c1][cur.r1][cur.c1] = true;
                        q.add(new Robot(cur.r1,cur.c1,cur.r1+1,cur.c1,cur.time+1));
                    }
                    // 오른쪽 축
                    if(!visited[cur.r2][cur.c2][cur.r2+1][cur.c2] && !visited[cur.r2+1][cur.c2][cur.r2][cur.c2]) {
                        visited[cur.r2][cur.c2][cur.r2+1][cur.c2] = visited[cur.r2+1][cur.c2][cur.r2][cur.c2] = true;
                        q.add(new Robot(cur.r2,cur.c2,cur.r2+1,cur.c2,cur.time+1));
                    }
                }
            } else { // 수직 상태
                // 왼쪽으로 회전
                if(canRotate(cur.r1,cur.c1,cur.r2,cur.c2,-1,board)) {
                    // 위쪽 축
                    if(!visited[cur.r1][cur.c1-1][cur.r1][cur.c1] && !visited[cur.r1][cur.c1][cur.r1][cur.c1-1]) {
                        visited[cur.r1][cur.c1-1][cur.r1][cur.c1] = visited[cur.r1][cur.c1][cur.r1][cur.c1-1] = true;
                        q.add(new Robot(cur.r1,cur.c1-1,cur.r1,cur.c1,cur.time+1));
                    }
                    // 아래쪽 축
                    if(!visited[cur.r2][cur.c2-1][cur.r2][cur.c2] && !visited[cur.r2][cur.c2][cur.r2][cur.c2-1]) {
                        visited[cur.r2][cur.c2-1][cur.r2][cur.c2] = visited[cur.r2][cur.c2][cur.r2][cur.c2-1] = true;
                        q.add(new Robot(cur.r2,cur.c2-1,cur.r2,cur.c2,cur.time+1));
                    }
                }
                
                // 오른쪽으로 회전
                if(canRotate(cur.r1,cur.c1,cur.r2,cur.c2,1,board)) {
                    // 위쪽 축
                    if(!visited[cur.r1][cur.c1][cur.r1][cur.c1+1] && !visited[cur.r1][cur.c1+1][cur.r1][cur.c1]) {
                        visited[cur.r1][cur.c1][cur.r1][cur.c1+1] = visited[cur.r1][cur.c1+1][cur.r1][cur.c1] = true;
                        q.add(new Robot(cur.r1,cur.c1,cur.r1,cur.c1+1,cur.time+1));
                    }
                    // 아래쪽 축
                    if(!visited[cur.r2][cur.c2][cur.r2][cur.c2+1] && !visited[cur.r2][cur.c2+1][cur.r2][cur.c2]) {
                        visited[cur.r2][cur.c2][cur.r2][cur.c2+1] = visited[cur.r2][cur.c2+1][cur.r2][cur.c2] = true;
                        q.add(new Robot(cur.r2,cur.c2,cur.r2,cur.c2+1,cur.time+1));
                    }
                }
            }
        }
        
        return answer;
    }
    
    private boolean canRotate(int r1, int c1, int r2, int c2, int direction, int[][] board) {
        if(r1 == r2) { // 수평 상태
            int r = r1 + direction;
            return isValid(r,c1,r,c2,board) && board[r][Math.min(c1,c2)] != 1 && board[r][Math.max(c1,c2)] != 1;
        } else { // 수직 상태
            int c = c1 + direction;
            return isValid(r1,c,r2,c,board) && board[Math.min(r1,r2)][c] != 1 && board[Math.max(r1,r2)][c] != 1;
        }
    }
    
    private boolean isValid(int r1, int c1, int r2, int c2, int[][] board) {
        return r1 >= 0 && r1 < board.length && c1 >= 0 && c1 < board.length &&
               r2 >= 0 && r2 < board.length && c2 >= 0 && c2 < board.length &&
               board[r1][c1] != 1 && board[r2][c2] != 1;
    }
}

class Robot {
    int r1, c1, r2, c2, time;
    
    Robot(int r1, int c1, int r2, int c2, int time) {
        this.r1 = r1;
        this.c1 = c1;
        this.r2 = r2;
        this.c2 = c2;
        this.time = time;
    }
}