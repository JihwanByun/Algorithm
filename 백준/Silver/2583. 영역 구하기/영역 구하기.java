import java.util.*;

public class Main {
    static int M, N, K;
    static int[][] grid;
    static boolean[][] visited;
    static int[] dx = {0, 0, -1, 1}; // 상하좌우 이동
    static int[] dy = {-1, 1, 0, 0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 입력
        M = sc.nextInt(); // 세로 길이
        N = sc.nextInt(); // 가로 길이
        K = sc.nextInt(); // 직사각형 개수

        grid = new int[M][N]; // M x N 격자
        visited = new boolean[M][N];

        // 직사각형 채우기
        for (int i = 0; i < K; i++) {
            int x1 = sc.nextInt();
            int y1 = sc.nextInt();
            int x2 = sc.nextInt();
            int y2 = sc.nextInt();

            // 직사각형 내부를 1로 채움 (좌표 변환 필요)
            for (int y = y1; y < y2; y++) {
                for (int x = x1; x < x2; x++) {
                    grid[y][x] = 1; // 채워진 부분
                }
            }
        }

        // 영역 개수 & 넓이 저장 리스트
        List<Integer> areas = new ArrayList<>();
        
        // BFS로 영역 탐색
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == 0 && !visited[i][j]) {
                    areas.add(bfs(i, j));
                }
            }
        }

        // 정렬 후 출력
        Collections.sort(areas);
        System.out.println(areas.size()); // 영역 개수
        for (int area : areas) {
            System.out.print(area + " ");
        }
    }

    // BFS 탐색 (넓이 반환)
    static int bfs(int y, int x) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{y, x});
        visited[y][x] = true;
        int areaSize = 1;

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int cy = curr[0], cx = curr[1];

            for (int d = 0; d < 4; d++) {
                int ny = cy + dy[d];
                int nx = cx + dx[d];

                if (ny >= 0 && ny < M && nx >= 0 && nx < N && !visited[ny][nx] && grid[ny][nx] == 0) {
                    queue.add(new int[]{ny, nx});
                    visited[ny][nx] = true;
                    areaSize++;
                }
            }
        }

        return areaSize;
    }
}