import java.util.*;

public class Main {
    static int N;
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {-1, 1, 0, 0}; // 상하좌우
    static int[] dy = {0, 0, -1, 1};
    static List<Integer> houseCounts;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = Integer.parseInt(sc.nextLine());
        map = new int[N][N];
        visited = new boolean[N][N];
        houseCounts = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < N; j++) {
                map[i][j] = line.charAt(j) - '0';
            }
        }

        // 모든 좌표 탐색
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 1 && !visited[i][j]) {
                    int count = dfs(i, j);
                    houseCounts.add(count);
                }
            }
        }

        Collections.sort(houseCounts);
        System.out.println(houseCounts.size());
        for (int count : houseCounts) {
            System.out.println(count);
        }
    }

    // DFS로 연결된 집들을 탐색
    static int dfs(int x, int y) {
        visited[x][y] = true;
        int count = 1;

        for (int dir = 0; dir < 4; dir++) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];

            if (nx >= 0 && ny >= 0 && nx < N && ny < N) {
                if (map[nx][ny] == 1 && !visited[nx][ny]) {
                    count += dfs(nx, ny);
                }
            }
        }

        return count;
    }
}
