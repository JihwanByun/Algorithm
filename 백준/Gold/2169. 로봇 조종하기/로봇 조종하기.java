import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        int[][] map = new int[N][M];

        for(int i = 0 ; i < N; i++){
            for(int j = 0 ; j < M; j++){
                map[i][j] = sc.nextInt();
            }
        }

        int[][] dp = new int[N][M];
        dp[0][0] = map[0][0];

        for(int j = 1 ; j < M ; j++){
            dp[0][j] = dp[0][j-1] + map[0][j];
        }
        /*
        최댓값 갱신하기
         */
        for(int i = 1 ; i < N ; i++){
            int[] left = new int[M];
            int[] right = new int[M];

            left[0] = map[i][0] + dp[i-1][0];
            for(int j = 1 ; j < M ; j++){
                left[j] = Math.max(left[j-1], dp[i-1][j]) + map[i][j];
            }

            right[M-1] = map[i][M-1] + dp[i-1][M-1];
            for(int j = M-2 ; j >=0 ; j--){
                right[j] = Math.max(right[j+1], dp[i-1][j]) + map[i][j];
            }

            for(int j = 0 ; j < M ; j++){
                dp[i][j] = Math.max(left[j], right[j]);
            }
        }
        System.out.println(dp[N-1][M-1]);
    }


    private static class Point {
        int r;
        int c;
    }
}