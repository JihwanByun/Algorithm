import java.util.*;

class Solution {
    public int solution(int n, int[] cores) {
        
        int left = 0;
        int right = 200000000; // 충분히 큰 값 설정 (최대 5 * 10^7 * 40)
        
        while(left <= right) {
            int mid = (left + right) / 2;

            long processed = 0;
            for (int core : cores) {
                processed += mid / core;
            }

            if (processed >= n - cores.length) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        // left: n번째 작업이 막 시작되는 시간
        long processed = 0;
        for (int core : cores) {
            processed += (left - 1) / core;
        }
        processed += cores.length; // 0초에 시작한 작업들

        for (int i = 0; i < cores.length; i++) {
            if (left % cores[i] == 0) {
                processed++;
                if (processed == n) {
                    return i + 1; // 1-based index
                }
            }
        }

        return -1; // 예외 처리
    }
}
