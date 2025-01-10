import java.util.Collections;
import java.util.PriorityQueue;

public class Solution {
    public long solution(int n, int[] works) {
        // 우선순위 큐를 사용하여 작업량이 큰 순서대로 정렬
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        // works 배열의 모든 작업량을 우선순위 큐에 삽입
        for (int work : works) {
            maxHeap.offer(work);
        }

        // n 시간 동안 작업량 감소
        for (int i = 0; i < n; i++) {
            if (maxHeap.isEmpty()) {
                break; // 작업이 모두 끝난 경우
            }
            int maxWork = maxHeap.poll();
            if (maxWork > 0) {
                maxWork--; // 작업량 1 감소
                maxHeap.offer(maxWork);
            }
        }

        // 야근 피로도 계산
        long fatigue = 0;
        while (!maxHeap.isEmpty()) {
            int work = maxHeap.poll();
            fatigue += (long) work * work; // 작업량의 제곱을 더함
        }

        return fatigue;
    }
}