import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[][] grid = new int[n][n];
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }
        
        // dp1[i][j] : (0,0)에서 (i,j)까지 이동할 때 얻을 수 있는 최대 누적합
        int[][] dp1 = new int[n][n];
        dp1[0][0] = grid[0][0];
        // 첫 번째 행 초기화
        for (int j = 1; j < n; j++) {
            dp1[0][j] = dp1[0][j - 1] + grid[0][j];
        }
        // 첫 번째 열 초기화
        for (int i = 1; i < n; i++) {
            dp1[i][0] = dp1[i - 1][0] + grid[i][0];
        }
        // 나머지 칸 채우기
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < n; j++) {
                dp1[i][j] = grid[i][j] + Math.max(dp1[i - 1][j], dp1[i][j - 1]);
            }
        }
        
        // dp2[i][j] : (i,j)에서 (n-1,n-1)까지 이동할 때 얻을 수 있는 최대 누적합
        int[][] dp2 = new int[n][n];
        dp2[n - 1][n - 1] = grid[n - 1][n - 1];
        // 마지막 열 초기화
        for (int i = n - 2; i >= 0; i--) {
            dp2[i][n - 1] = grid[i][n - 1] + dp2[i + 1][n - 1];
        }
        // 마지막 행 초기화
        for (int j = n - 2; j >= 0; j--) {
            dp2[n - 1][j] = grid[n - 1][j] + dp2[n - 1][j + 1];
        }
        // 나머지 칸 채우기 (역방향 DP)
        for (int i = n - 2; i >= 0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                dp2[i][j] = grid[i][j] + Math.max(dp2[i + 1][j], dp2[i][j + 1]);
            }
        }
        
        // 모든 (i,j)에 대해 dp1[i][j] + dp2[i][j]의 최댓값을 구함
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans = Math.max(ans, dp1[i][j] + dp2[i][j]);
            }
        }
        System.out.println(ans);
    }
}
