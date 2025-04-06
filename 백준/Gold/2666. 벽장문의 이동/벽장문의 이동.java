import java.util.*;

public class Main {
    static int N, M;
    static int[] order;
    static int[][][] dp;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt(); // 벽장 수
        int open1 = sc.nextInt();
        int open2 = sc.nextInt();

        M = sc.nextInt(); // 사용할 벽장 수
        order = new int[M];
        for (int i = 0; i < M; i++) {
            order[i] = sc.nextInt();
        }

        dp = new int[21][21][M + 1];
        for (int[][] d1 : dp) {
            for (int[] d2 : d1) {
                Arrays.fill(d2, -1);
            }
        }

        int result = dfs(open1, open2, 0);
        System.out.println(result);
    }

    static int dfs(int open1, int open2, int depth) {
        if (depth == M) return 0;

        if (dp[open1][open2][depth] != -1) return dp[open1][open2][depth];

        int target = order[depth];

        // 두 개 중 하나를 target으로 이동
        int move1 = dfs(target, open2, depth + 1) + Math.abs(open1 - target);
        int move2 = dfs(open1, target, depth + 1) + Math.abs(open2 - target);

        return dp[open1][open2][depth] = Math.min(move1, move2);
    }
}
