import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] stairs = new int[n+1];
        for(int i = 1 ; i <= n ; i++) {
            stairs[i] = sc.nextInt();
        }


        int[][] dp = new int[n+1][2]; //n번 까지 내려갔을 때 최대 점수

        dp[1][0] = stairs[1];
        if(n == 1) {
            System.out.println(stairs[1]);
            return;
        }
        dp[2][0] = stairs[2];
        dp[2][1] = stairs[2] + stairs[1];

        //한 번에 하나 or 두 칸, 0이면 이전 칸 안밟음, 1이면 이전 칸 밟음
        for(int i = 3 ; i <= n ; i++) {
            dp[i][0] = Math.max(dp[i - 2][0], dp[i - 2][1]) + stairs[i];
            dp[i][1] = dp[i - 1][0] + stairs[i];
        }
        System.out.println(Math.max(dp[n][0],dp[n][1]));
    }
}
