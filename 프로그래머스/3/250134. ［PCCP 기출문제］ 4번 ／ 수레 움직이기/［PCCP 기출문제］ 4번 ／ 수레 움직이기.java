import java.util.*;

public class Solution {

    // 좌표를 표현하는 클래스 (편의상 사용)
    static class Point {
        int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }
    }

    // BFS에서 사용할 상태 클래스
    static class State {
        int redX, redY;
        int blueX, blueY;
        int redVisited;   // 격자 칸 번호를 bitmask로 기록 (최대 16비트)
        int blueVisited;
        int turns;        // 지금까지 진행한 턴 수

        State(int redX, int redY, int blueX, int blueY, int redVisited, int blueVisited, int turns) {
            this.redX = redX;
            this.redY = redY;
            this.blueX = blueX;
            this.blueY = blueY;
            this.redVisited = redVisited;
            this.blueVisited = blueVisited;
            this.turns = turns;
        }
        
        // 상태를 문자열로 표현하여 중복 방문 체크에 활용
        @Override
        public String toString() {
            return redX + "," + redY + "," + blueX + "," + blueY + "," + redVisited + "," + blueVisited;
        }
    }

    // 방향 배열: 상, 하, 좌, 우
    static int[][] directions = { {-1,0}, {1,0}, {0,-1}, {0,1} };

    public int solution(int[][] maze) {
        int n = maze.length;
        int m = maze[0].length;

        Point redStart = null, blueStart = null, redDest = null, blueDest = null;

        // 시작 및 도착 칸 위치 찾기
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                switch(maze[i][j]) {
                    case 1: redStart = new Point(i, j); break;
                    case 2: blueStart = new Point(i, j); break;
                    case 3: redDest = new Point(i, j); break;
                    case 4: blueDest = new Point(i, j); break;
                    // 0: 빈칸, 5: 벽은 별도 처리
                }
            }
        }
        if(redStart == null || blueStart == null || redDest == null || blueDest == null) {
            return 0;
        }

        // 초기 방문 비트마스크 계산: 격자 칸 번호 = i*m + j
        int redStartBit = 1 << (redStart.x * m + redStart.y);
        int blueStartBit = 1 << (blueStart.x * m + blueStart.y);

        State init = new State(redStart.x, redStart.y, blueStart.x, blueStart.y, redStartBit, blueStartBit, 0);
        Queue<State> queue = new LinkedList<>();
        queue.add(init);

        Set<String> visitedStates = new HashSet<>();
        visitedStates.add(init.toString());

        // BFS 탐색
        while (!queue.isEmpty()) {
            State cur = queue.poll();

            // 두 수레 모두 도착에 도달하면 해결!
            if(cur.redX == redDest.x && cur.redY == redDest.y &&
               cur.blueX == blueDest.x && cur.blueY == blueDest.y) {
                return cur.turns;
            }

            // 이번 턴에서 두 수레는 반드시 이동해야 하므로, 각 수레의 이동 옵션을 구합니다.
            List<int[]> redMoves = new ArrayList<>();
            // 만약 빨간 수레가 이미 도착 칸에 있으면 움직이지 않고 그대로 유지합니다.
            if(cur.redX == redDest.x && cur.redY == redDest.y) {
                redMoves.add(new int[]{cur.redX, cur.redY});
            } else {
                // 4방향 이동 시도
                for (int[] d : directions) {
                    int nx = cur.redX + d[0];
                    int ny = cur.redY + d[1];
                    if (!isValid(nx, ny, n, m, maze)) continue;
                    int posBit = 1 << (nx * m + ny);
                    // 이미 방문했던 칸이면 이동 불가
                    if ((cur.redVisited & posBit) != 0) continue;
                    redMoves.add(new int[]{nx, ny});
                }
            }
            
            List<int[]> blueMoves = new ArrayList<>();
            if(cur.blueX == blueDest.x && cur.blueY == blueDest.y) {
                blueMoves.add(new int[]{cur.blueX, cur.blueY});
            } else {
                for (int[] d : directions) {
                    int nx = cur.blueX + d[0];
                    int ny = cur.blueY + d[1];
                    if (!isValid(nx, ny, n, m, maze)) continue;
                    int posBit = 1 << (nx * m + ny);
                    if ((cur.blueVisited & posBit) != 0) continue;
                    blueMoves.add(new int[]{nx, ny});
                }
            }
            
            // 만약 둘 중 한 수레라도 이동할 수 있는 옵션이 없다면 이 상태는 진행 불가
            if(redMoves.isEmpty() || blueMoves.isEmpty()) continue;
            
            // 두 수레의 이동 옵션을 조합합니다.
            for (int[] rMove : redMoves) {
                for (int[] bMove : blueMoves) {
                    // 동시에 같은 칸으로 이동하면 안 됨.
                    if(rMove[0] == bMove[0] && rMove[1] == bMove[1]) continue;
                    // 두 수레가 서로의 현재 위치로 동시에 이동하는 자리 바꾸기도 안 됨.
                    if(rMove[0] == cur.blueX && rMove[1] == cur.blueY &&
                       bMove[0] == cur.redX && bMove[1] == cur.redY) continue;
                    
                    // 새 방문 기록을 갱신 (이미 도착한 경우에는 그대로 유지)
                    int newRedVisited = cur.redVisited;
                    if(!(cur.redX == redDest.x && cur.redY == redDest.y)) {
                        newRedVisited |= (1 << (rMove[0] * m + rMove[1]));
                    }
                    int newBlueVisited = cur.blueVisited;
                    if(!(cur.blueX == blueDest.x && cur.blueY == blueDest.y)) {
                        newBlueVisited |= (1 << (bMove[0] * m + bMove[1]));
                    }
                    
                    State next = new State(rMove[0], rMove[1], bMove[0], bMove[1], newRedVisited, newBlueVisited, cur.turns + 1);
                    String key = next.toString();
                    if(visitedStates.contains(key)) continue;
                    visitedStates.add(key);
                    queue.add(next);
                }
            }
        }
        // BFS 탐색 완료: 퍼즐을 풀 수 없는 경우
        return 0;
    }
    
    // (x,y)가 격자 내에 있고, 벽이 아닌지 확인
    private boolean isValid(int x, int y, int n, int m, int[][] maze) {
        if(x < 0 || y < 0 || x >= n || y >= m) return false;
        if(maze[x][y] == 5) return false;
        return true;
    }
    
    // 테스트 코드 (원하는 테스트 케이스를 넣어볼 수 있음)
    public static void main(String[] args) {
        Solution sol = new Solution();

        int[][] maze1 = {
            {1, 4},
            {0, 0},
            {2, 3}
        };
        System.out.println(sol.solution(maze1));  // 예상 결과: 3

        int[][] maze2 = {
            {1, 0, 2},
            {0, 0, 0},
            {5, 0, 5},
            {4, 0, 3}
        };
        System.out.println(sol.solution(maze2));  // 예상 결과: 7

        int[][] maze3 = {
            {1, 5},
            {2, 5},
            {4, 5},
            {3, 5}
        };
        System.out.println(sol.solution(maze3));  // 예상 결과: 0

        int[][] maze4 = {
            {4, 1, 2, 3}
        };
        System.out.println(sol.solution(maze4));  // 예상 결과: 0
    }
}
