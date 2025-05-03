import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); // 물건 종류 수
        int M = sc.nextInt(); // 배낭 최대 무게

        // 1) 모든 쪼갠 아이템을 (weight, value) 쌍으로 저장
        List<int[]> items = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int w = sc.nextInt();
            int v = sc.nextInt();
            int c = sc.nextInt();

            // 이진분해: c를 1,2,4,... 단위로 쪼갠 뒤 남은 만큼 묶음
            int k = 1;
            while (c > 0) {
                int take = Math.min(k, c);
                items.add(new int[]{ w * take, v * take });
                c -= take;
                k <<= 1;
            }
        }

        // 2) 0/1 배낭 DP (1차원)
        int[] dp = new int[M + 1];
        for (int[] it : items) {
            int wt = it[0], val = it[1];
            for (int j = M; j >= wt; j--) {
                dp[j] = Math.max(dp[j], dp[j - wt] + val);
            }
        }

        // 3) 결과 출력
        System.out.println(dp[M]);
    }
}
