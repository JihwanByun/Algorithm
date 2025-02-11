import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt(); // 학생 수
        int M = sc.nextInt(); // 최대 블록 개수
        int H = sc.nextInt(); // 목표 높이
        sc.nextLine();

        List<Set<Integer>> blocks = new ArrayList<>();
        blocks.add(new HashSet<>()); // 0번 인덱스는 사용하지 않음

        for (int student = 0; student < N; student++) {
            String[] height = sc.nextLine().split(" ");
            Set<Integer> set = new HashSet<>();
            for (String h : height) {
                set.add(Integer.parseInt(h));
            }
            blocks.add(set);
        }

        int MOD = 10007;
        int[][] dp = new int[N + 1][H + 1];

        // 초기 상태: 아무 학생도 사용하지 않고 높이 0을 만드는 방법은 1가지
        dp[0][0] = 1;

        for (int s = 1; s <= N; s++) {
            Set<Integer> blockSet = blocks.get(s);

            // 이전 학생까지의 경우의 수를 그대로 가져옴
            for (int h = 0; h <= H; h++) {
                dp[s][h] = dp[s - 1][h];
            }

            // 현재 학생이 가진 블록을 사용하여 새롭게 가능한 높이 추가
            for (int block : blockSet) {
                for (int h = H - block; h >= 0; h--) { // 큰 값부터 처리 (중복 사용 방지)
                    dp[s][h + block] = (dp[s][h + block] + dp[s - 1][h]) % MOD;
                }
            }
        }

        System.out.println(dp[N][H]); // 목표 높이를 만들 수 있는 경우의 수 출력
    }
}