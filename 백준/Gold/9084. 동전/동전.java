import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();
        for(int test = 1 ; test <= T; test++) {
            int N = sc.nextInt();
            int[] coins = new int[N];
            for(int i = 0 ; i < N; i++) {
                coins[i] = sc.nextInt();
            }
            int M = sc.nextInt(); //만들어야 할 금액

            int[] dp = new int[M+1];
            dp[0]= 1;

            for(int coin : coins) {
                for(int k = coin ; k <= M ; k++) {
                    dp[k] += dp[k - coin];
                }
            }
            System.out.println(dp[M]);
        }
    }
}