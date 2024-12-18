class Solution {
    public int solution(int[][] points, int[][] routes) {
        
        int[][][] map = new int[101][101][20001];
        
        for (int[] route : routes) {
            int time = 0;
            int curR = points[route[0] - 1][0];
            int curC = points[route[0] - 1][1];

            // 시작점 마킹
            map[curR][curC][time] += 1;

            for(int i = 1 ; i < route.length; i++){
                // 경로의 각 포인트를 순회
                int nextR = points[route[i] - 1][0];
                int nextC = points[route[i] - 1][1];

                // 행 이동
                while (curR != nextR ) {
                    curR += (curR < nextR) ? 1 : -1;
                    time++;
                    map[curR][curC][time] += 1;
                }

                // 열 이동
                while (curC != nextC ) {
                    curC += (curC < nextC) ? 1 : -1;
                    time++;
                    map[curR][curC][time] += 1;
                }
            }
        }

        // 겹치는 지점 카운트
        int answer = 0;
        
        for (int i = 1; i <= 100; i++) {
            for (int j = 1; j <= 100; j++) {
                for (int time = 0; time <= 20000; time++) {
                    if (map[i][j][time] > 1) {
                        answer++;
                    }
                }
            }
        }

        return answer;
    }
}
