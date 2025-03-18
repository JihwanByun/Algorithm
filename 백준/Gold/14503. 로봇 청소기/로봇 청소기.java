import java.util.*;

public class Main {
    static int N, M;
    static int[][] map;
    static boolean[][] cleaned;
    static int r, c, d;
    
    // 북, 동, 남, 서 방향 (반시계 방향 회전 기준)
    static int[] dr = {-1, 0, 1, 0}; // 북, 동, 남, 서
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 입력 받기
        N = sc.nextInt();
        M = sc.nextInt();
        r = sc.nextInt();
        c = sc.nextInt();
        d = sc.nextInt();
        
        map = new int[N][M];
        cleaned = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = sc.nextInt();
            }
        }
        
        // 청소 시작
        System.out.println(cleanRoom());
    }

    static int cleanRoom() {
        int cleanCount = 0;

        while (true) {
            // 1. 현재 칸이 청소되지 않았다면 청소
            if (!cleaned[r][c]) {
                cleaned[r][c] = true;
                cleanCount++;
            }

            boolean hasCleanable = false;

            // 2. 주변 4칸 확인
            for (int i = 0; i < 4; i++) {
                d = (d + 3) % 4; // 반시계 방향 회전
                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < N && nc >= 0 && nc < M && map[nr][nc] == 0 && !cleaned[nr][nc]) {
                    // 청소되지 않은 빈 칸이 있으면 이동 후 1번으로 돌아감
                    r = nr;
                    c = nc;
                    hasCleanable = true;
                    break;
                }
            }

            // 3. 청소할 곳이 없으면 후진 시도
            if (!hasCleanable) {
                int backDir = (d + 2) % 4;
                int br = r + dr[backDir];
                int bc = c + dc[backDir];

                // 후진할 수 있으면 후진 후 반복 계속
                if (br >= 0 && br < N && bc >= 0 && bc < M && map[br][bc] == 0) {
                    r = br;
                    c = bc;
                } else {
                    // 후진도 불가능하면 작동 종료
                    break;
                }
            }
        }

        return cleanCount;
    }
}
