class Solution {
    public long solution(int n, int[] times) {
        long left = 1;
        long maxTime = 0;
        for (int time : times) {
            maxTime = Math.max(maxTime, time);
        }
        long right = maxTime * (long) n;

        while (left <= right) {
            long mid = left + (right - left) / 2;
            long cnt = 0;
            for (int time : times) {
                cnt += mid / time;
            }

            if (cnt >= n) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }
}
