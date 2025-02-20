import java.util.*;

public class Main {
    static int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
    static int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        
        for (int t = 0; t < T; t++) {
            int l = sc.nextInt();
            int sx = sc.nextInt(), sy = sc.nextInt();
            int ex = sc.nextInt(), ey = sc.nextInt();
            
            System.out.println(bfs(l, sx, sy, ex, ey));
        }
    }
    
    static int bfs(int l, int sx, int sy, int ex, int ey) {
        if (sx == ex && sy == ey) return 0;
        
        boolean[][] visited = new boolean[l][l];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{sx, sy, 0});
        visited[sx][sy] = true;
        
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0], y = cur[1], moves = cur[2];
            
            for (int i = 0; i < 8; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if (nx >= 0 && nx < l && ny >= 0 && ny < l && !visited[nx][ny]) {
                    if (nx == ex && ny == ey) return moves + 1;
                    
                    visited[nx][ny] = true;
                    queue.add(new int[]{nx, ny, moves + 1});
                }
            }
        }
        return -1;
    }
}