import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();
        int L = sc.nextInt();
        int K = sc.nextInt();

        List<int[]> stars = new ArrayList<>();

        for (int i = 0; i < K; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            stars.add(new int[]{x, y});
        }

        int maxCaught = 0;

        // 별들의 x, y 좌표를 기준점으로 사용
        for (int[] star1 : stars) {
            for (int[] star2 : stars) {
                int count = 0;
                int x = star1[0];
                int y = star2[1];

                // 현재 기준점에서 L*L 크기의 트램펄린으로 잡을 수 있는 별의 개수 계산
                for (int[] star : stars) {
                    if (star[0] >= x && star[0] <= x + L &&
                        star[1] >= y && star[1] <= y + L) {
                        count++;
                    }
                }
                maxCaught = Math.max(maxCaught, count);
            }
        }

        System.out.println(K - maxCaught);
    }
}