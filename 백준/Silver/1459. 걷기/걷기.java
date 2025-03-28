import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        long X = sc.nextLong();
        long Y = sc.nextLong();
        long W = sc.nextLong();
        long S = sc.nextLong();

        long minXY = Math.min(X, Y);
        long diff = Math.abs(X - Y);
        long result = 0;

        // 대각선이 직선 2번보다 더 쌀 때
        if (S < 2 * W) {
            result += minXY * S;

            // 남은 거리도 S가 W보다 싸면 지그재그 대각선으로 커버 가능
            if (S < W) {
                // 남은 거리 짝수면 대각선으로 모두 커버 가능
                if (diff % 2 == 0) {
                    result += diff * S;
                } else {
                    // 홀수면 마지막 1칸은 직선으로 커버
                    result += (diff - 1) * S + W;
                }
            } else {
                result += diff * W;
            }

        } else {
            // 대각선보다 직선 2번이 싸면 전부 직선으로 이동
            result = (X + Y) * W;
        }

        System.out.println(result);
    }
}
