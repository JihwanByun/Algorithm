import java.util.Scanner;

public class Main {

    // 테트리스 블록의 4가지 모양을 정의 (회전 모양 포함)
    static int[][][] blocks = {
            // I형 블록
            {{1, 1, 1, 1}},  // 가로형
            {{1}, {1}, {1}, {1}},  // 세로형

            // O형 블록 (회전해도 동일)
            {{1, 1}, {1, 1}},

            // T형 블록
            {{0, 1, 0}, {1, 1, 1}},  // 기본형
            {{1, 0}, {1, 1}, {1, 0}},  // 회전형 1
            {{1, 1, 1}, {0, 1, 0}},  // 회전형 2
            {{0, 1}, {1, 1}, {0, 1}},  // 회전형 3

            // L형 블록
            {{1, 0, 0}, {1, 1, 1}},  // 기본형
            {{1, 1}, {1, 0}, {1, 0}},  // 회전형 1
            {{1, 1, 1}, {0, 0, 1}},  // 회전형 2
            {{0, 1}, {0, 1}, {1, 1}},  // 회전형 3

            // Z형 블록
            {{1, 1, 0}, {0, 1, 1}},  // 기본형
            {{0, 1}, {1, 1}, {1, 0}}  // 회전형 1
    };

    // 주어진 블록과 회전을 표 안에 배치할 수 있는지 체크하는 함수
    static boolean canPlaceBlock(int[][] grid, int[][] block, int row, int col) {
        int blockRows = block.length;
        int blockCols = block[0].length;

        if (row + blockRows > grid.length || col + blockCols > grid[0].length) {
            return false; // 블록이 표를 벗어나는지 확인
        }

        return true; // 블록이 완전히 표 안에 배치 가능하면 true
    }

    // 블록 아래에 있는 숫자의 합을 계산하는 함수
    static int calculateSum(int[][] grid, int[][] block, int row, int col) {
        int sum = 0;
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                if (block[i][j] == 1) {
                    sum += grid[row + i][col + j];
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int caseNumber = 1;
        while (true) {
            int N = scanner.nextInt();
            if (N == 0) break; // 입력이 0이면 종료

            int[][] grid = new int[N][N];

            // 표의 값 입력
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    grid[i][j] = scanner.nextInt();
                }
            }

            int maxSum = Integer.MIN_VALUE;

            // 모든 블록에 대해 모든 회전 모양과 배치 가능한 위치를 검사
            for (int[][] block : blocks) {
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < N; j++) {
                        if (canPlaceBlock(grid, block, i, j)) {
                            int sum = calculateSum(grid, block, i, j);
                            maxSum = Math.max(maxSum, sum);
                        }
                    }
                }
            }

            System.out.println(caseNumber + ". " + maxSum);
            caseNumber++;
        }

        scanner.close();
    }
}