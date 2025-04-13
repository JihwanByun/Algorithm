import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        for(int test = 1 ; test <= N ; test++) {
            int n = sc.nextInt();
            int[] file = new int[n+1];
            int[] sum = new int[n+1];
            int[][] dp = new int[n+1][n+1];
            for(int i = 1 ; i <= n ; i++) {
                file[i] = sc.nextInt();
            }
            for(int i = 1 ; i <= n ; i++) {
                sum[i] = sum[i-1] + file[i];
            }

            for(int i = 1 ; i <= n ; i++) {
                for(int j = i+1 ; j <= n ; j++) {
                    dp[i][j] = Integer.MAX_VALUE;
                }
            }

            for(int len = 2 ; len <= n ; len++) {
                for(int i = 1 ; i <= n - len + 1 ; i++) {
                    int j = i + len - 1;
                    for(int k = i ; k < j ; k++) {
                        dp[i][j] = Math.min(dp[i][j],
                                            dp[i][k] + dp[k+1][j] + sum[j] - sum[i-1]);
                    }
                }
            }
            System.out.println(dp[1][n]);
        }
    }
}
