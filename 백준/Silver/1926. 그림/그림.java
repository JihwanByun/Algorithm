import java.util.*;

public class Main {
    static int n, m;
    static int[][] paper;
    static boolean[][] visited;

    // 상하좌우 방향
    static int[] dx = { -1, 1, 0, 0 };
    static int[] dy = { 0, 0, -1, 1 };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt(); // 세로
        m = sc.nextInt(); // 가로
        paper = new int[n][m];
        visited = new boolean[n][m];

        // 도화지 입력
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                paper[i][j] = sc.nextInt();

        int count = 0; // 그림 개수
        int maxArea = 0; // 가장 큰 그림 넓이

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (paper[i][j] == 1 && !visited[i][j]) {
                    int area = bfs(i, j);
                    maxArea = Math.max(maxArea, area);
                    count++;
                }
            }
        }

        System.out.println(count);
        System.out.println(maxArea);
    }

    static int bfs(int x, int y) {
        Queue<int[]> queue = new LinkedList<>();
        visited[x][y] = true;
        queue.add(new int[] { x, y });

        int area = 1;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cx = cur[0];
            int cy = cur[1];

            for (int dir = 0; dir < 4; dir++) {
                int nx = cx + dx[dir];
                int ny = cy + dy[dir];

                // 경계 검사 + 방문 여부 + 그림(1) 여부
                if (nx >= 0 && ny >= 0 && nx < n && ny < m) {
                    if (!visited[nx][ny] && paper[nx][ny] == 1) {
                        visited[nx][ny] = true;
                        queue.add(new int[] { nx, ny });
                        area++;
                    }
                }
            }
        }

        return area;
    }
}
