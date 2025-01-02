import java.util.*;

class Solution {
    public int solution(int[] stones, int k) {
        int right = Arrays.stream(stones).max().getAsInt() * 2; // 가능한 최대 값
        int left = 0; // 가능한 최소 값

        while (left < right) {
            int mid = (left + right) / 2;
            if (canCross(mid, k, stones)) {
                left = mid + 1; // 더 많은 사람이 건널 수 있는지 확인
            } else {
                right = mid; // 적은 인원으로 시도
            }
        }

        return left - 1; // 최댓값 반환
    }

    static boolean canCross(int mid, int k, int[] stones) {
        int skip = 0; // 연속된 건널 수 없는 돌의 개수
        for (int stone : stones) {
            if (stone < mid) { // 돌이 mid보다 작으면 건널 수 없음
                skip++;
                if (skip >= k) {
                    return false; // 연속된 건널 수 없는 돌의 개수가 k 이상
                }
            } else {
                skip = 0; // 건널 수 있으면 초기화
            }
        }
        return true; // 모두 건널 수 있음
    }
}
