import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 이 프로그램은 플레이어가 시작 지점에서 종료 지점으로 이동하면서 시야를 통해 전사들을 관리하는 게임 로직을 구현합니다.
 * 장애물과 전사들이 있는 그리드에서 최적의 시야 방향을 선택하여 전사들의 이동을 제어하고, 전사들이 플레이어를 향해 이동하도록 합니다.
 */
public class Main {

    // 상수 정의
    static final int INF = (int)1e9 + 10; // 무한대를 나타내는 상수

    // 방향 배열: 위, 아래, 왼쪽, 오른쪽
    static final int[] DX = {-1, 1, 0, 0};
    static final int[] DY = {0, 0, -1, 1};

    // 전사 위치를 나타내는 클래스
    static class Point {
        int x;
        int y;

        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    /**
     * 두 점 사이의 맨해튼 거리를 계산하는 함수
     * @param a 첫 번째 점의 좌표 (x, y)
     * @param b 두 번째 점의 좌표 (x, y)
     * @return 맨해튼 거리
     */
    static int calculateManhattanDistance(Point a, Point b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    /**
     * BFS를 이용하여 종료 지점(startX, startY)에서 모든 도달 가능한 셀까지의 최단 거리를 계산하는 함수
     * @param startX 시작 지점의 x 좌표 (종료 지점)
     * @param startY 시작 지점의 y 좌표 (종료 지점)
     * @param N 그리드의 크기 (N x N)
     * @param obstacleGrid 장애물 그리드 (1: 장애물, 0: 자유로운 공간)
     * @return 각 셀에서의 거리 그리드
     */
    static int[][] computeDistances(int startX, int startY, int N, int[][] obstacleGrid){
        // 거리 그리드를 초기화: 장애물이 있는 셀은 INF, 그렇지 않으면 -1로 설정
        int[][] distanceGrid = new int[N][N];
        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                distanceGrid[i][j] = obstacleGrid[i][j] == 1 ? INF : -1;
            }
        }

        Queue<Point> queue = new LinkedList<>();
        queue.offer(new Point(startX, startY));
        distanceGrid[startX][startY] = 0; // 시작 지점의 거리는 0

        // BFS 알고리즘 실행
        while(!queue.isEmpty()){
            Point current = queue.poll();
            int currentX = current.x;
            int currentY = current.y;

            // 네 방향으로 이동
            for(int dir=0; dir<4; dir++){
                int nextX = currentX + DX[dir];
                int nextY = currentY + DY[dir];

                // 그리드 경계를 벗어나는지 확인
                if(nextX < 0 || nextY < 0 || nextX >= N || nextY >= N)
                    continue;
                // 이미 방문했거나 장애물이 있는지 확인
                if(distanceGrid[nextX][nextY] != -1)
                    continue;

                // 다음 셀의 거리 업데이트 및 큐에 추가
                distanceGrid[nextX][nextY] = distanceGrid[currentX][currentY] + 1;
                queue.offer(new Point(nextX, nextY));
            }
        }

        return distanceGrid;
    }

    /**
     * 위쪽 방향으로 시야를 설정하는 함수
     * @param x 현재 플레이어의 x 좌표
     * @param y 현재 플레이어의 y 좌표
     * @param N 그리드의 크기
     * @param isTest 테스트 모드 여부 (true: 테스트, false: 실제 적용)
     * @param warriorCountGrid 각 셀에 있는 전사의 수
     * @param sightMap 현재 시야 상태 (1: 시야 내, 0: 시야 외)
     * @return 시야로 커버된 전사의 수
     */
    static int sightUp(int x, int y, int N, boolean isTest, int[][] warriorCountGrid, int[][] sightMap){
        // 다이아몬드 형태로 위쪽 셀을 시야에 포함시킴
        for(int i = x - 1; i >= 0; i--){
            int left = Math.max(0, y - (x - i));
            int right = Math.min(N - 1, y + (x - i));
            for(int j = left; j <= right; j++){
                sightMap[i][j] = 1; // 시야 설정
            }
        }

        // 장애물 처리: 시야 막힘 여부 확인
        boolean obstructionFound = false;
        for(int i = x - 1; i >= 0; i--){
            if(obstructionFound){
                sightMap[i][y] = 0; // 장애물이 발견된 후에는 시야 제거
            }
            else{
                sightMap[i][y] = 1; // 장애물이 발견되지 않으면 시야 유지
            }

            if(warriorCountGrid[i][y] > 0){
                obstructionFound = true; // 전사가 있는 경우 장애물로 간주
            }
        }

        // 장애물에 따라 시야 조정
        for(int i = x - 1; i >= 1; i--){
            int left = Math.max(0, y - (x - i));
            int right = Math.min(N - 1, y + (x - i));

            // 왼쪽 측면 조정
            for(int j = left; j < y; j++){
                if(sightMap[i][j] == 0 || warriorCountGrid[i][j] > 0){
                    if(j > 0){
                        sightMap[i - 1][j - 1] = 0; // 왼쪽 위 셀의 시야 제거
                    }
                    sightMap[i - 1][j] = 0; // 바로 위 셀의 시야 제거
                }
            }

            // 오른쪽 측면 조정
            for(int j = y + 1; j <= right; j++){
                if(sightMap[i][j] == 0 || warriorCountGrid[i][j] > 0){
                    if(j + 1 < N){
                        sightMap[i - 1][j + 1] = 0; // 오른쪽 위 셀의 시야 제거
                    }
                    sightMap[i - 1][j] = 0; // 바로 위 셀의 시야 제거
                }
            }
        }

        // 시야로 커버된 전사 수 계산
        int coverage = 0;
        for(int i = x - 1; i >= 0; i--){
            int left = Math.max(0, y - (x - i));
            int right = Math.min(N - 1, y + (x - i));
            for(int j = left; j <= right; j++){
                if(sightMap[i][j] == 1){
                    coverage += warriorCountGrid[i][j];
                }
            }
        }

        // 테스트 모드인 경우 시야 맵을 원래대로 되돌림
        if(isTest){
            for(int i = x - 1; i >= 0; i--){
                int left = Math.max(0, y - (x - i));
                int right = Math.min(N - 1, y + (x - i));
                for(int j = left; j <= right; j++){
                    sightMap[i][j] = 0; // 시야 제거
                }
            }
        }

        return coverage; // 커버리지 반환
    }

    /**
     * 아래쪽 방향으로 시야를 설정하는 함수
     * @param x 현재 플레이어의 x 좌표
     * @param y 현재 플레이어의 y 좌표
     * @param N 그리드의 크기
     * @param isTest 테스트 모드 여부 (true: 테스트, false: 실제 적용)
     * @param warriorCountGrid 각 셀에 있는 전사의 수
     * @param sightMap 현재 시야 상태 (1: 시야 내, 0: 시야 외)
     * @return 시야로 커버된 전사의 수
     */
    static int sightDown(int x, int y, int N, boolean isTest, int[][] warriorCountGrid, int[][] sightMap){
        // 다이아몬드 형태로 아래쪽 셀을 시야에 포함시킴
        for(int i = x + 1; i < N; i++){
            int left = Math.max(0, y - (i - x));
            int right = Math.min(N - 1, y + (i - x));
            for(int j = left; j <= right; j++){
                sightMap[i][j] = 1; // 시야 설정
            }
        }

        // 장애물 처리: 시야 막힘 여부 확인
        boolean obstructionFound = false;
        for(int i = x + 1; i < N; i++){
            if(obstructionFound){
                sightMap[i][y] = 0; // 장애물이 발견된 후에는 시야 제거
            }
            else{
                sightMap[i][y] = 1; // 장애물이 발견되지 않으면 시야 유지
            }

            if(warriorCountGrid[i][y] > 0){
                obstructionFound = true; // 전사가 있는 경우 장애물로 간주
            }
        }

        // 장애물에 따라 시야 조정
        for(int i = x + 1; i < N - 1; i++){
            int left = Math.max(0, y - (i - x));
            int right = Math.min(N - 1, y + (i - x));

            // 왼쪽 측면 조정
            for(int j = left; j < y; j++){
                if(sightMap[i][j] == 0 || warriorCountGrid[i][j] > 0){
                    if(j > 0){
                        sightMap[i + 1][j - 1] = 0; // 왼쪽 아래 셀의 시야 제거
                    }
                    sightMap[i + 1][j] = 0; // 바로 아래 셀의 시야 제거
                }
            }

            // 오른쪽 측면 조정
            for(int j = y + 1; j <= right; j++){
                if(sightMap[i][j] == 0 || warriorCountGrid[i][j] > 0){
                    if(j + 1 < N){
                        sightMap[i + 1][j + 1] = 0; // 오른쪽 아래 셀의 시야 제거
                    }
                    sightMap[i + 1][j] = 0; // 바로 아래 셀의 시야 제거
                }
            }
        }

        // 시야로 커버된 전사 수 계산
        int coverage = 0;
        for(int i = x + 1; i < N; i++){
            int left = Math.max(0, y - (i - x));
            int right = Math.min(N - 1, y + (i - x));
            for(int j = left; j <= right; j++){
                if(sightMap[i][j] == 1){
                    coverage += warriorCountGrid[i][j];
                }
            }
        }

        // 테스트 모드인 경우 시야 맵을 원래대로 되돌림
        if(isTest){
            for(int i = x + 1; i < N; i++){
                int left = Math.max(0, y - (i - x));
                int right = Math.min(N - 1, y + (i - x));
                for(int j = left; j <= right; j++){
                    sightMap[i][j] = 0; // 시야 제거
                }
            }
        }

        return coverage; // 커버리지 반환
    }

    /**
     * 왼쪽 방향으로 시야를 설정하는 함수
     * @param x 현재 플레이어의 x 좌표
     * @param y 현재 플레이어의 y 좌표
     * @param N 그리드의 크기
     * @param isTest 테스트 모드 여부 (true: 테스트, false: 실제 적용)
     * @param warriorCountGrid 각 셀에 있는 전사의 수
     * @param sightMap 현재 시야 상태 (1: 시야 내, 0: 시야 외)
     * @return 시야로 커버된 전사의 수
     */
    static int sightLeft(int x, int y, int N, boolean isTest, int[][] warriorCountGrid, int[][] sightMap){
        // 다이아몬드 형태로 왼쪽 셀을 시야에 포함시킴
        for(int i = y - 1; i >= 0; i--){
            int top = Math.max(0, x - (y - i));
            int bottom = Math.min(N - 1, x + (y - i));
            for(int j = top; j <= bottom; j++){
                sightMap[j][i] = 1; // 시야 설정
            }
        }

        // 장애물 처리: 시야 막힘 여부 확인
        boolean obstructionFound = false;
        for(int i = y - 1; i >= 0; i--){
            if(obstructionFound){
                sightMap[x][i] = 0; // 장애물이 발견된 후에는 시야 제거
            }
            else{
                sightMap[x][i] = 1; // 장애물이 발견되지 않으면 시야 유지
            }

            if(warriorCountGrid[x][i] > 0){
                obstructionFound = true; // 전사가 있는 경우 장애물로 간주
            }
        }

        // 장애물에 따라 시야 조정
        for(int i = y - 1; i > 0; i--){
            int top = Math.max(0, x - (y - i));
            int bottom = Math.min(N - 1, x + (y - i));

            // 상단 측면 조정
            for(int j = top; j < x; j++){
                if(sightMap[j][i] == 0 || warriorCountGrid[j][i] > 0){
                    if(j > 0){
                        sightMap[j - 1][i - 1] = 0; // 왼쪽 위 셀의 시야 제거
                    }
                    sightMap[j][i - 1] = 0; // 바로 왼쪽 셀의 시야 제거
                }
            }

            // 하단 측면 조정
            for(int j = x + 1; j <= bottom; j++){
                if(sightMap[j][i] == 0 || warriorCountGrid[j][i] > 0){
                    if(j + 1 < N){
                        sightMap[j + 1][i - 1] = 0; // 왼쪽 아래 셀의 시야 제거
                    }
                    sightMap[j][i - 1] = 0; // 바로 왼쪽 셀의 시야 제거
                }
            }
        }

        // 시야로 커버된 전사 수 계산
        int coverage = 0;
        for(int i = y - 1; i >= 0; i--){
            int top = Math.max(0, x - (y - i));
            int bottom = Math.min(N - 1, x + (y - i));
            for(int j = top; j <= bottom; j++){
                if(sightMap[j][i] == 1){
                    coverage += warriorCountGrid[j][i];
                }
            }
        }

        // 테스트 모드인 경우 시야 맵을 원래대로 되돌림
        if(isTest){
            for(int i = y - 1; i >= 0; i--){
                int top = Math.max(0, x - (y - i));
                int bottom = Math.min(N - 1, x + (y - i));
                for(int j = top; j <= bottom; j++){
                    sightMap[j][i] = 0; // 시야 제거
                }
            }
        }

        return coverage; // 커버리지 반환
    }

    /**
     * 오른쪽 방향으로 시야를 설정하는 함수
     * @param x 현재 플레이어의 x 좌표
     * @param y 현재 플레이어의 y 좌표
     * @param N 그리드의 크기
     * @param isTest 테스트 모드 여부 (true: 테스트, false: 실제 적용)
     * @param warriorCountGrid 각 셀에 있는 전사의 수
     * @param sightMap 현재 시야 상태 (1: 시야 내, 0: 시야 외)
     * @return 시야로 커버된 전사의 수
     */
    static int sightRight(int x, int y, int N, boolean isTest, int[][] warriorCountGrid, int[][] sightMap){
        // 다이아몬드 형태로 오른쪽 셀을 시야에 포함시킴
        for(int i = y + 1; i < N; i++){
            int top = Math.max(0, x - (i - y));
            int bottom = Math.min(N - 1, x + (i - y));
            for(int j = top; j <= bottom; j++){
                sightMap[j][i] = 1; // 시야 설정
            }
        }

        // 장애물 처리: 시야 막힘 여부 확인
        boolean obstructionFound = false;
        for(int i = y + 1; i < N; i++){
            if(obstructionFound){
                sightMap[x][i] = 0; // 장애물이 발견된 후에는 시야 제거
            }
            else{
                sightMap[x][i] = 1; // 장애물이 발견되지 않으면 시야 유지
            }

            if(warriorCountGrid[x][i] > 0){
                obstructionFound = true; // 전사가 있는 경우 장애물로 간주
            }
        }

        // 장애물에 따라 시야 조정
        for(int i = y + 1; i < N - 1; i++){
            int top = Math.max(0, x - (i - y));
            int bottom = Math.min(N - 1, x + (i - y));

            // 상단 측면 조정
            for(int j = top; j < x; j++){
                if(sightMap[j][i] == 0 || warriorCountGrid[j][i] > 0){
                    if(j > 0){
                        sightMap[j - 1][i + 1] = 0; // 오른쪽 위 셀의 시야 제거
                    }
                    sightMap[j][i + 1] = 0; // 바로 오른쪽 셀의 시야 제거
                }
            }

            // 하단 측면 조정
            for(int j = x + 1; j <= bottom; j++){
                if(sightMap[j][i] == 0 || warriorCountGrid[j][i] > 0){
                    if(j + 1 < N){
                        sightMap[j + 1][i + 1] = 0; // 오른쪽 아래 셀의 시야 제거
                    }
                    sightMap[j][i + 1] = 0; // 바로 오른쪽 셀의 시야 제거
                }
            }
        }

        // 시야로 커버된 전사 수 계산
        int coverage = 0;
        for(int i = y + 1; i < N; i++){
            int top = Math.max(0, x - (i - y));
            int bottom = Math.min(N - 1, x + (i - y));
            for(int j = top; j <= bottom; j++){
                if(sightMap[j][i] == 1){
                    coverage += warriorCountGrid[j][i];
                }
            }
        }

        // 테스트 모드인 경우 시야 맵을 원래대로 되돌림
        if(isTest){
            for(int i = y + 1; i < N; i++){
                int top = Math.max(0, x - (i - y));
                int bottom = Math.min(N - 1, x + (i - y));
                for(int j = top; j <= bottom; j++){
                    sightMap[j][i] = 0; // 시야 제거
                }
            }
        }

        return coverage; // 커버리지 반환
    }

    /**
     * 최적의 시야 방향을 선택하여 시야를 설정하는 함수
     * @param x 현재 플레이어의 x 좌표
     * @param y 현재 플레이어의 y 좌표
     * @param N 그리드의 크기
     * @param warriorCountGrid 각 셀에 있는 전사의 수
     * @param sightMap 현재 시야 상태 (1: 시야 내, 0: 시야 외)
     * @return 최대 커버리지 (시야로 커버된 전사의 수)
     */
    static int chooseBestSight(int x, int y, int N, int[][] warriorCountGrid, int[][] sightMap){
        // 시야 맵을 초기화 (모든 셀을 시야 외로 설정)
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                sightMap[i][j] = 0;
            }
        }

        int maxCoverage = -1;    // 최대 커버리지를 저장할 변수
        int bestDirection = -1;   // 최적의 시야 방향 (0: 위, 1: 아래, 2: 왼쪽, 3: 오른쪽)

        // 모든 시야 방향을 테스트하여 최대 커버리지를 찾음
        for(int dir=0; dir<4; dir++){
            // 테스트를 위해 시야 맵을 초기화
            for(int i=0;i<N;i++){
                for(int j=0;j<N;j++){
                    sightMap[i][j] = 0;
                }
            }

            // 현재 방향으로 시야 설정하고 커버리지 계산
            int coverage = 0;
            if(dir == 0){
                coverage = sightUp(x, y, N, true, warriorCountGrid, sightMap);
            }
            else if(dir == 1){
                coverage = sightDown(x, y, N, true, warriorCountGrid, sightMap);
            }
            else if(dir == 2){
                coverage = sightLeft(x, y, N, true, warriorCountGrid, sightMap);
            }
            else if(dir == 3){
                coverage = sightRight(x, y, N, true, warriorCountGrid, sightMap);
            }

            if(maxCoverage < coverage){
                maxCoverage = coverage;
                bestDirection = dir;
            }
        }

        // 유효한 방향이 선택되었는지 확인
        assert bestDirection != -1 : "최적의 시야 방향을 찾을 수 없습니다.";

        // 최적의 방향으로 실제 시야 설정
        if(bestDirection == 0){
            sightUp(x, y, N, false, warriorCountGrid, sightMap);
        }
        else if(bestDirection == 1){
            sightDown(x, y, N, false, warriorCountGrid, sightMap);
        }
        else if(bestDirection == 2){
            sightLeft(x, y, N, false, warriorCountGrid, sightMap);
        }
        else if(bestDirection == 3){
            sightRight(x, y, N, false, warriorCountGrid, sightMap);
        }

        return maxCoverage;
    }

    /**
     * 플레이어를 향해 전사들을 이동시키는 함수
     * @param playerX 플레이어의 현재 x 좌표
     * @param playerY 플레이어의 현재 y 좌표
     * @param N 그리드의 크기
     * @param M 전사의 수
     * @param warriorPositions 전사들의 현재 위치
     * @param sightMap 현재 시야 상태 (1: 시야 내, 0: 시야 외)
     * @return (총 이동한 전사 수, 플레이어에게 도달한 전사 수)
     */
    static Pair<Integer, Integer> moveWarriors(int playerX, int playerY, int N, int M, Point[] warriorPositions, int[][] sightMap){
        int totalMoved = 0;   // 총 이동한 전사 수
        int totalHits = 0;    // 플레이어에게 도달한 전사 수

        // 전사의 이동 방향: 위, 아래, 왼쪽, 오른쪽
        int[] moveDX = {-1, 1, 0, 0};
        int[] moveDY = {0, 0, -1, 1};

        // 모든 전사에 대해 이동 처리
        for(int i=0; i<M; i++){
            if(warriorPositions[i].x == -1)
                continue; // 이미 잡힌 전사는 건너뜀

            int warriorX = warriorPositions[i].x;
            int warriorY = warriorPositions[i].y;

            // 시야 내에 있는 전사는 이동하지 않음
            if(sightMap[warriorX][warriorY] == 1)
                continue;

            // 현재 플레이어와의 거리 계산
            int currentDistance = calculateManhattanDistance(new Point(playerX, playerY), new Point(warriorX, warriorY));
            boolean hasMoved = false; // 이동 여부 플래그

            // 첫 번째 이동: 거리를 줄이기 위해 이동
            for(int dir=0; dir<4; dir++){
                int nextX = warriorX + moveDX[dir];
                int nextY = warriorY + moveDY[dir];

                // 이동할 위치가 그리드 내에 있고 시야 내에 있지 않은지 확인
                if(nextX < 0 || nextY < 0 || nextX >= N || nextY >= N)
                    continue;
                if(sightMap[nextX][nextY] == 1)
                    continue;

                // 새로운 위치에서의 거리 계산
                int newDistance = calculateManhattanDistance(new Point(playerX, playerY), new Point(nextX, nextY));
                if(newDistance < currentDistance){
                    warriorX = nextX;
                    warriorY = nextY;
                    hasMoved = true;
                    totalMoved++;
                    break; // 첫 번째 이동 후 루프 탈출
                }
            }

            // 두 번째 이동: 추가로 거리를 줄일 수 있는지 확인
            if(hasMoved){
                int newDistance = calculateManhattanDistance(new Point(playerX, playerY), new Point(warriorX, warriorY));
                for(int dir=0; dir<4; dir++){
                    // 반대 방향으로 이동 시도
                    int oppositeDir = (dir + 2) % 4;
                    int nextX = warriorX + moveDX[oppositeDir];
                    int nextY = warriorY + moveDY[oppositeDir];

                    // 이동할 위치가 그리드 내에 있고 시야 내에 있지 않은지 확인
                    if(nextX < 0 || nextY < 0 || nextX >= N || nextY >= N)
                        continue;
                    if(sightMap[nextX][nextY] == 1)
                        continue;

                    // 새로운 위치에서의 거리 계산
                    int furtherDistance = calculateManhattanDistance(new Point(playerX, playerY), new Point(nextX, nextY));
                    if(furtherDistance < newDistance){
                        warriorX = nextX;
                        warriorY = nextY;
                        totalMoved++;
                        break; // 두 번째 이동 후 루프 탈출
                    }
                }
            }

            // 전사의 위치 업데이트
            warriorPositions[i] = new Point(warriorX, warriorY);
        }

        // 플레이어에게 도달한 전사 수 계산
        for(int i=0; i<M; i++){
            if(warriorPositions[i].x == -1)
                continue; // 이미 잡힌 전사는 건너뜀

            if(warriorPositions[i].x == playerX && warriorPositions[i].y == playerY){
                totalHits++;
                warriorPositions[i] = new Point(-1, -1); // 전사를 잡힌 상태로 표시
            }
        }

        return new Pair<>(totalMoved, totalHits);
    }

    /**
     * 현재 전사들의 위치를 기반으로 각 셀에 있는 전사의 수를 업데이트하는 함수
     * @param N 그리드의 크기
     * @param M 전사의 수
     * @param warriorPositions 전사들의 현재 위치
     * @return 각 셀에 있는 전사의 수 그리드
     */
    static int[][] updateWarriorCountGrid(int N, int M, Point[] warriorPositions){
        // 전사 수 그리드를 초기화
        int[][] warriorCountGrid = new int[N][N];
        for(int i=0; i<N; i++){
            Arrays.fill(warriorCountGrid[i], 0);
        }

        // 각 전사의 위치를 확인하여 전사 수 그리드에 반영
        for(int i=0; i<M; i++){
            if(warriorPositions[i].x == -1)
                continue; // 이미 잡힌 전사는 건너뜀
            int x = warriorPositions[i].x;
            int y = warriorPositions[i].y;
            warriorCountGrid[x][y]++;
        }

        return warriorCountGrid;
    }

    /**
     * 메인 함수: 프로그램의 시작점
     * @param args 명령줄 인자
     * @throws IOException 입출력 예외
     */
    public static void main(String[] args) throws IOException{
        // 빠른 입출력을 위해 BufferedReader 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 그리드 크기 N
        int M = Integer.parseInt(st.nextToken()); // 전사 수 M

        // 시작 지점과 종료 지점의 좌표 입력
        st = new StringTokenizer(br.readLine());
        int startX = Integer.parseInt(st.nextToken());
        int startY = Integer.parseInt(st.nextToken());
        int endX = Integer.parseInt(st.nextToken());
        int endY = Integer.parseInt(st.nextToken());

        // 초기 전사 위치 입력
        Point[] warriorPositions = new Point[M];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<M; i++){
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            warriorPositions[i] = new Point(x, y);
        }

        // 장애물 그리드 입력
        int[][] obstacleGrid = new int[N][N];
        for(int i=0; i<N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++){
                obstacleGrid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 시작 지점과 종료 지점이 장애물이 아닌지 확인
        assert obstacleGrid[startX][startY] == 0 : "시작 지점에 장애물이 있습니다.";
        assert obstacleGrid[endX][endY] == 0 : "종료 지점에 장애물이 있습니다.";

        // 종료 지점으로부터 모든 셀까지의 거리를 계산
        int[][] distanceGrid = computeDistances(endX, endY, N, obstacleGrid);

        // 시작 지점이 종료 지점에 도달할 수 없는 경우 종료
        if(distanceGrid[startX][startY] == -1){
            System.out.println("-1");
            return;
        }

        int currentX = startX; // 현재 플레이어의 x 좌표
        int currentY = startY; // 현재 플레이어의 y 좌표

        // 시야 맵 초기화
        int[][] sightMap = new int[N][N];

        // 전사 수 그리드 초기화
        int[][] warriorCountGrid = updateWarriorCountGrid(N, M, warriorPositions);

        // 메인 루프: 플레이어가 종료 지점에 도달할 때까지 반복
        while(true){
            boolean moved = false; // 플레이어가 이동했는지 여부

            // 현재 위치에서 종료 지점으로 향하는 방향으로 한 칸 이동
            for(int dir=0; dir<4; dir++){
                int nextX = currentX + DX[dir];
                int nextY = currentY + DY[dir];
                if(nextX < 0 || nextY < 0 || nextX >= N || nextY >= N)
                    continue; // 그리드 경계를 벗어나면 무시
                if(distanceGrid[nextX][nextY] < distanceGrid[currentX][currentY]){
                    currentX = nextX;
                    currentY = nextY;
                    moved = true;
                    break; // 이동 후 루프 탈출
                }
            }

            // 종료 지점에 도달한 경우 종료
            if(currentX == endX && currentY == endY){
                System.out.println("0");
                break;
            }

            // 현재 위치에 있는 전사들을 잡음 (시야로 인해)
            for(int i=0; i<M; i++){
                if(warriorPositions[i].x == currentX && warriorPositions[i].y == currentY){
                    warriorPositions[i] = new Point(-1, -1); // 전사를 잡힌 상태로 표시
                }
            }

            // 현재 전사들의 위치를 기반으로 전사 수 그리드를 업데이트
            warriorCountGrid = updateWarriorCountGrid(N, M, warriorPositions);

            // 최적의 시야 방향을 선택하고 시야로 커버된 전사의 수를 계산
            int sightCoverage = chooseBestSight(currentX, currentY, N, warriorCountGrid, sightMap);

            // 전사들을 이동시키고 이동한 전사 수와 플레이어에게 도달한 전사 수를 얻음
            Pair<Integer, Integer> warriorResult = moveWarriors(currentX, currentY, N, M, warriorPositions, sightMap);

            // 전사 수 그리드를 업데이트
            warriorCountGrid = updateWarriorCountGrid(N, M, warriorPositions);

            // 결과 출력: 이동한 전사 수, 시야 커버리지, 플레이어에게 도달한 전사 수
            System.out.println(warriorResult.first + " " + sightCoverage + " " + warriorResult.second);
        }
    }

    /**
     * 간단한 쌍(Pair)을 나타내는 클래스
     * @param <F> 첫 번째 요소의 타입
     * @param <S> 두 번째 요소의 타입
     */
    static class Pair<F, S>{
        F first;
        S second;

        Pair(F first, S second){
            this.first = first;
            this.second = second;
        }
    }
}