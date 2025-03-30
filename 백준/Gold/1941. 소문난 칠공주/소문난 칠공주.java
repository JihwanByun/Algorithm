import java.util.*;

public class Main {
    static char[][] map = new char[5][5];
    static boolean[] visited = new boolean[25];
    static int result = 0;
    static int[] dx = {-1, 1, 0, 0}; // 상하좌우
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            String line = sc.nextLine();
            for (int j = 0; j < 5; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        combination(0, 0, new ArrayList<>());
        System.out.println(result);
    }

    // 25개 중 7개 고르기
    static void combination(int start, int count, List<Integer> selected) {
        if (count == 7) {
            if (isValid(selected)) {
                result++;
            }
            return;
        }

        for (int i = start; i < 25; i++) {
            selected.add(i);
            combination(i + 1, count + 1, selected);
            selected.remove(selected.size() - 1);
        }
    }

    // 연결되어 있는지, S가 4개 이상인지 확인
    static boolean isValid(List<Integer> selected) {
        boolean[][] isSelected = new boolean[5][5];
        for (int idx : selected) {
            int r = idx / 5;
            int c = idx % 5;
            isSelected[r][c] = true;
        }

        // BFS로 연결 확인
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[5][5];
        int sCount = 0;
        int connected = 1;

        int sr = selected.get(0) / 5;
        int sc = selected.get(0) % 5;
        queue.add(new int[]{sr, sc});
        visited[sr][sc] = true;
        if (map[sr][sc] == 'S') sCount++;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int x = cur[0];
            int y = cur[1];

            for (int d = 0; d < 4; d++) {
                int nx = x + dx[d];
                int ny = y + dy[d];

                if (nx >= 0 && ny >= 0 && nx < 5 && ny < 5) {
                    if (!visited[nx][ny] && isSelected[nx][ny]) {
                        visited[nx][ny] = true;
                        queue.add(new int[]{nx, ny});
                        connected++;
                        if (map[nx][ny] == 'S') sCount++;
                    }
                }
            }
        }

        return connected == 7 && sCount >= 4;
    }
}
