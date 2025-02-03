class Solution {
    public int solution(int[] a) {
        int n = a.length;
        if (n <= 2) return n;

        int answer = 0;

        int leftMin = Integer.MAX_VALUE;
        int rightMin = Integer.MAX_VALUE;

        // 왼쪽 최소값을 유지하면서 오른쪽까지 확인
        int[] rightMins = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            rightMin = Math.min(rightMin, a[i]);
            rightMins[i] = rightMin;
        }

        // 가운데 원소들 확인
        for (int i = 0; i < n; i++) {
            if (i > 0) leftMin = Math.min(leftMin, a[i - 1]);
            if (a[i] <= leftMin || a[i] <= rightMins[i]) {
                answer++;
            }
        }

        return answer;
    }
}
