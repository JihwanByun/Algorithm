import java.util.* ;

class Solution {
    static int answer;
    static boolean[] visited;
    
    public int solution(int n, int[] weak, int[] dist) {
        answer = Integer.MAX_VALUE;
        
        // dist 내림차순 정렬
        
        Integer[] distBox = Arrays.stream(dist).boxed().toArray(Integer[]::new);
        Arrays.sort(distBox, Collections.reverseOrder());
        
        // 원형을 직선으로 펴기
        int[] expandedWeak = new int[weak.length * 2];
        for(int i = 0; i < weak.length; i++) {
            expandedWeak[i] = weak[i];
            expandedWeak[i + weak.length] = weak[i] + n;
        }
        
        // 1명부터 dist.length명까지 시도
        for(int i = 1; i <= dist.length && answer == Integer.MAX_VALUE; i++) {
            visited = new boolean[dist.length];
            int[] selected = new int[i];
            permutation(0, i, selected, distBox, expandedWeak, weak.length);
        }
        
        
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }
    
    private void permutation(int depth, int r, int[] selected, 
                         Integer[] dist, int[] weak, int weakLen) {
        if (depth == r) {
            checkCoverage(selected, weak, weakLen);
            return;
        }   

        for (int i = 0; i < dist.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                selected[depth] = dist[i];
                permutation(depth + 1, r, selected, dist, weak, weakLen);
                visited[i] = false;
            }
        }
    }

    
    private void checkCoverage(int[] selected, int[] weak, int weakLen) {
    for (int start = 0; start < weakLen; start++) {
        int pos = start;
        int friendIdx = 0;

        while (pos < start + weakLen && friendIdx < selected.length) {
            int coverage = weak[pos] + selected[friendIdx];

            // 현재 친구가 커버할 수 있는 모든 지점 탐색
            while (pos < start + weakLen && weak[pos] <= coverage) {
                pos++;
            }

            friendIdx++;
        }

        // 모든 취약점이 커버되었는지 확인
        if (pos >= start + weakLen) {
            answer = Math.min(answer, selected.length);
            return;
        }
    }
}

}