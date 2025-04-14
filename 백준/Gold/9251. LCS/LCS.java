import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        char[] a = sc.next().toCharArray();
        char[] b = sc.next().toCharArray();

        int[][] dp = new int[a.length + 1][b.length + 1];

        for(int i = 1 ; i <= a.length; i++) {
            for(int j = 1 ; j <= b.length; j++) {
                if(a[i-1] == b[j-1]) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
            }
        }
        System.out.println(dp[a.length][b.length]);
    }
}
