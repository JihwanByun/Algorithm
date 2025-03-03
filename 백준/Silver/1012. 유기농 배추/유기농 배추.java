import java.util.*;

public class Main {
    static int M, N, K;
    static int[][] field;
    static boolean[][] visited;
    static int[] dx = {0, 0, -1, 1}; // 상하좌우 이동
    static int[] dy = {-1, 1, 0, 0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt(); // 테스트 케이스 개수

        while (T-- > 0) {
            M = sc.nextInt();
            N = sc.nextInt();
            K = sc.nextInt();

            field = new int[N][M]; // 배추밭
            visited = new boolean[N][M]; // 방문 여부

            // 배추 위치 입력
            for (int i = 0; i < K; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                field[y][x] = 1; // 주의: (x, y) 좌표로 입력됨
            }

            int wormCount = 0; // 지렁이 개수

            // 모든 배추를 탐색하며 BFS 수행
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    if (field[i][j] == 1 && !visited[i][j]) {
                        bfs(i, j);
                        wormCount++; // 새로운 그룹 발견 → 지렁이 필요
                    }
                }
            }
            System.out.println(wormCount);
        }
        sc.close();
    }

    // BFS 탐색 (큐 활용)
    static void bfs(int y, int x) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{y, x});
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int cy = curr[0], cx = curr[1];

            for (int d = 0; d < 4; d++) {
                int ny = cy + dy[d];
                int nx = cx + dx[d];

                if (ny >= 0 && ny < N && nx >= 0 && nx < M) {
                    if (field[ny][nx] == 1 && !visited[ny][nx]) {
                        visited[ny][nx] = true;
                        queue.offer(new int[]{ny, nx});
                    }
                }
            }
        }
    }
}