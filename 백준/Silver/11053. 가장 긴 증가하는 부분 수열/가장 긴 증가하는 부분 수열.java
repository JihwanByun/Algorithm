import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] dp = new int[N];
        dp[0] = 1;
        Arrays.fill(dp, 1);
        int[] num = new int[N];
        int answer = 1;
        for(int i = 0 ; i < N ; i++) {
            num[i] = sc.nextInt();
        }

        for(int i = 1 ; i < N ; i++) {
            for(int j = i - 1 ;  j >= 0 ; j--) {
                if(num[j] < num[i]) {
                    dp[i] = Math.max(dp[j] + 1, dp[i]);
                    answer = Math.max(answer, dp[i]);
                }
            }
        }

        System.out.println(answer);
    }
}
