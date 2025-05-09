class Solution {
    public int solution(int[][] beginning, int[][] target) {
        int n = beginning.length;
        int m = beginning[0].length;
        int min = Integer.MAX_VALUE;

        for (int rowMask = 0; rowMask < (1 << n); rowMask++) {
            int[][] temp = copy(beginning);
            int count = 0;

            // 1. 행 뒤집기
            for (int i = 0; i < n; i++) {
                if ((rowMask & (1 << i)) != 0) {
                    flipRow(temp, i);
                    count++;
                }
            }

            // 2. 열 뒤집기 판단
            boolean[] flipCols = new boolean[m];
            for (int j = 0; j < m; j++) {
                boolean needFlip = false;
                for (int i = 0; i < n; i++) {
                    if (temp[i][j] != target[i][j]) {
                        needFlip = true;
                        break;
                    }
                }

                if (needFlip) {
                    flipCols[j] = true;
                    count++;
                    flipCol(temp, j);
                }
            }

            // 3. 결과 확인
            if (equals(temp, target)) {
                min = Math.min(min, count);
            }
        }

        return (min == Integer.MAX_VALUE) ? -1 : min;
    }

    private void flipRow(int[][] board, int row) {
        for (int j = 0; j < board[0].length; j++) {
            board[row][j] ^= 1;
        }
    }

    private void flipCol(int[][] board, int col) {
        for (int i = 0; i < board.length; i++) {
            board[i][col] ^= 1;
        }
    }

    private boolean equals(int[][] a, int[][] b) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }

    private int[][] copy(int[][] board) {
        int[][] result = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, result[i], 0, board[0].length);
        }
        return result;
    }
}
