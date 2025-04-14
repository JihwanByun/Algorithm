import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc =new Scanner(System.in);
        int n  = sc.nextInt();
        int[] dp = new int [n+1];
        if(n == 1) {
            System.out.println(0);
            return;
        }
        if(n <= 3){
            System.out.println(1);
            return;
        }
        dp[2] = 1;
        dp[3] = 1;
        
        for(int i = 4 ; i <= n; i++) {
            if(i % 3 == 0 && i % 2 == 0) {
                int min = Math.min(dp[i/3] + 1, dp[i/2] + 1);
                dp[i] = Math.min(dp[i-1] + 1, min);
            }
            else if(i % 3 == 0) {
                dp[i] = Math.min(dp[i/3] + 1, dp[i-1] + 1);
            } else if(i % 2 == 0){
                dp[i] = Math.min(dp[i/2] + 1, dp[i-1] + 1);
            } else dp[i] = dp[i-1] + 1;
        }
        System.out.println(dp[n]);
    }
}
