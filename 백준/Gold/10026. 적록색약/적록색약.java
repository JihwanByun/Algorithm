import java.util.*;

public class Main {
    static int N;
    static char[][] grid;
    static boolean[][] visited;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        grid = new char[N][N];
        
        for (int i = 0; i < N; i++) {
            grid[i] = sc.next().toCharArray();
        }
        
        // 적록색약이 아닌 사람의 경우
        visited = new boolean[N][N];
        int normalCount = countRegions(false);
        
        // 적록색약인 사람의 경우
        visited = new boolean[N][N];
        int colorBlindCount = countRegions(true);
        
        System.out.println(normalCount + " " + colorBlindCount);
    }
    
    static int countRegions(boolean isColorBlind) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (!visited[i][j]) {
                    bfs(i, j, isColorBlind);
                    count++;
                }
            }
        }
        return count;
    }
    
    static void bfs(int x, int y, boolean isColorBlind) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;
        char color = grid[x][y];
        
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cx = cur[0], cy = cur[1];
            
            for (int d = 0; d < 4; d++) {
                int nx = cx + dx[d];
                int ny = cy + dy[d];
                
                if (nx >= 0 && nx < N && ny >= 0 && ny < N && !visited[nx][ny]) {
                    if (isSameColor(color, grid[nx][ny], isColorBlind)) {
                        visited[nx][ny] = true;
                        queue.add(new int[]{nx, ny});
                    }
                }
            }
        }
    }
    
    static boolean isSameColor(char c1, char c2, boolean isColorBlind) {
        if (isColorBlind) {
            if ((c1 == 'R' || c1 == 'G') && (c2 == 'R' || c2 == 'G')) return true;
        }
        return c1 == c2;
    }
}