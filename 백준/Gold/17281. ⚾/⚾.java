import java.util.*;

public class Main {
    static int N;
    static int[][] innings;
    static boolean[] visited = new boolean[9];
    static int[] order = new int[9];
    static int maxScore = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        innings = new int[N][9];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 9; j++) {
                innings[i][j] = sc.nextInt();
            }
        }

        // 1번 선수는 4번 타자(index 3)에 고정
        order[3] = 0; // 선수 번호: 0 (1번 선수)
        visited[0] = true;
        setOrder(0);

        System.out.println(maxScore);
    }

    // 순열 생성 (1번 선수는 4번 타자 고정)
    static void setOrder(int depth) {
        if (depth == 9) {
            maxScore = Math.max(maxScore, playGame());
            return;
        }

        if (depth == 3) {
            setOrder(depth + 1); // 4번 타자는 고정되어 있으므로 skip
            return;
        }

        for (int i = 1; i < 9; i++) {
            if (!visited[i]) {
                visited[i] = true;
                order[depth] = i;
                setOrder(depth + 1);
                visited[i] = false;
            }
        }
    }

    // 주어진 타순으로 게임 시뮬레이션
    static int playGame() {
        int score = 0;
        int batterIdx = 0;

        for (int i = 0; i < N; i++) {
            int outCount = 0;
            boolean[] base = new boolean[3]; // 1루, 2루, 3루

            while (outCount < 3) {
                int player = order[batterIdx];
                int result = innings[i][player];

                if (result == 0) {
                    outCount++;
                } else if (result == 1) { // 안타
                    if (base[2]) score++;
                    base[2] = base[1];
                    base[1] = base[0];
                    base[0] = true;
                } else if (result == 2) { // 2루타
                    if (base[2]) score++;
                    if (base[1]) score++;
                    base[2] = base[0];
                    base[1] = true;
                    base[0] = false;
                } else if (result == 3) { // 3루타
                    for (int j = 0; j < 3; j++) {
                        if (base[j]) score++;
                        base[j] = false;
                    }
                    base[2] = true;
                } else if (result == 4) { // 홈런
                    for (int j = 0; j < 3; j++) {
                        if (base[j]) score++;
                        base[j] = false;
                    }
                    score++; // 타자 점수
                }

                batterIdx = (batterIdx + 1) % 9;
            }
        }

        return score;
    }
}
