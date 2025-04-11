import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int[][] dp = new int[N+1][M+1];

        int[][] profit = new int[N+1][M+1];
        for(int i = 1; i <= N ; i++) {
            sc.nextInt();
            for(int j = 1 ; j <= M ; j++) {
                profit[i][j] = sc.nextInt();
            }
        }
        int[][] track = new int[N+1][M+1];

        for(int money = 1 ; money <= N ; money++) {
            for(int company = 1; company <= M ; company++) {
                dp[money][company] = dp[money][company - 1]; // 🔥 초기화는 여기에서 한 번만!

                for (int i = 1; i <= money; i++) {
                    int candidate = dp[money - i][company - 1] + profit[i][company];
                    if (dp[money][company] < candidate) {
                        dp[money][company] = candidate;
                        track[money][company] = i;
                    }
                }
            }
        }

        int[] array = new int[M+1];
        int result = N;
        for(int i = M ; i > 0 ; i--) {
            int invest = track[result][i];
            array[i] = invest;
            result -= invest;
        }
        System.out.println(dp[N][M]);
        for(int i = 1 ; i <= M ; i++){
            System.out.print(array[i] + " ");
        }
    }
}
