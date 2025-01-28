import java.util.Scanner;

public class Main {
    static final int MOD = 1000000007;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int L = sc.nextInt(); 
        int R = sc.nextInt(); 

        // dp[n][l][r]: n개의 빌딩을 사용하여 왼쪽에서 l개, 오른쪽에서 r개가 보이도록 하는 경우의 수
        long[][][] dp = new long[101][101][101];

        // 기저 사례: 빌딩이 1개일 때는 양쪽에서 모두 보임
        dp[1][1][1] = 1;

        // n: 사용한 빌딩의 수
        for (int n = 1; n < N; n++) {
            // l: 왼쪽에서 보이는 빌딩의 수
            for (int l = 1; l <= N; l++) {
                // r: 오른쪽에서 보이는 빌딩의 수
                for (int r = 1; r <= N; r++) {
                    if (dp[n][l][r] == 0) continue;

                    // 1. 가장 작은 빌딩을 맨 앞에 놓는 경우
                    dp[n + 1][l + 1][r] = (dp[n + 1][l + 1][r] + dp[n][l][r]) % MOD;

                    // 2. 가장 작은 빌딩을 맨 뒤에 놓는 경우
                    dp[n + 1][l][r + 1] = (dp[n + 1][l][r + 1] + dp[n][l][r]) % MOD;

                    // 3. 가장 작은 빌딩을 중간에 놓는 경우
                    // (n-1)개의 위치 중 하나를 선택
                    dp[n + 1][l][r] = (dp[n + 1][l][r] + (dp[n][l][r] * (n - 1)) % MOD) % MOD;
                }
            }
        }

        System.out.println(dp[N][L][R]);
    }
}