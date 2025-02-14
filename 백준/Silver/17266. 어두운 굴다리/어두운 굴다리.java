import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 입력 받기
        int N = sc.nextInt(); // 굴다리 길이
        int M = sc.nextInt(); // 가로등 개수
        int[] lamps = new int[M];

        for (int i = 0; i < M; i++) {
            lamps[i] = sc.nextInt();
        }

        // 이분 탐색을 위한 변수 설정
        int left = 1, right = N, answer = N;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (canIlluminate(N, lamps, mid)) {
                answer = mid; // 가능한 최소 높이 갱신
                right = mid - 1; // 더 작은 높이도 가능한지 확인
            } else {
                left = mid + 1;
            }
        }

        System.out.println(answer);
    }

    // 특정 높이로 모든 길을 밝힐 수 있는지 확인하는 함수
    private static boolean canIlluminate(int N, int[] lamps, int H) {
        int covered = 0; // 현재까지 비춰진 최대 위치

        for (int lamp : lamps) {
            if (covered < lamp - H) {
                return false; // 가로등이 닿지 않는 어두운 구간이 발생
            }
            covered = lamp + H; // 가로등이 비출 수 있는 범위 갱신
            if (covered >= N) {
                return true; // 굴다리 끝까지 비출 수 있으면 성공
            }
        }

        return covered >= N; // 마지막까지 비출 수 있는지 최종 확인
    }
}