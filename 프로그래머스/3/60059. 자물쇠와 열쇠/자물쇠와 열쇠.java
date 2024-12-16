import java.util.*;

class Solution {
    static int n, m;

    public boolean solution(int[][] key, int[][] lock) {
        n = lock.length;
        m = key.length;

        for (int rot = 0; rot < 4; rot++) {
            if (canUnlock(key, lock)) {
                return true;
            }
            key = rotate(key); // Rotate and update key
        }
        return false;
    }

    boolean canUnlock(int[][] key, int[][] lock) {
        int expandLength = n + 2 * (m - 1); // Correct expand size
        int[][] expandLock = new int[expandLength][expandLength];

        // Copy lock into the center of expandLock
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                expandLock[m - 1 + i][m - 1 + j] = lock[i][j];
            }
        }

        // Try placing the key at every position
        for (int i = 0; i <= expandLength - m; i++) {
            for (int j = 0; j <= expandLength - m; j++) {
                if (checkUnLock(i, j, key, expandLock)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean checkUnLock(int r, int c, int[][] key, int[][] expandLock) {
        // Deep copy expandLock
        int[][] copyExpand = new int[expandLock.length][expandLock[0].length];
        for (int i = 0; i < expandLock.length; i++) {
            copyExpand[i] = expandLock[i].clone();
        }

        // Add key to expandLock
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                copyExpand[r + i][c + j] += key[i][j];
            }
        }

        // Check if lock region is completely filled with 1s
        for (int i = m - 1; i < m - 1 + n; i++) {
            for (int j = m - 1; j < m - 1 + n; j++) {
                if (copyExpand[i][j] != 1) {
                    return false;
                }
            }
        }
        return true;
    }

    int[][] rotate(int[][] key) {
        int[][] copy = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                copy[i][j] = key[m - j - 1][i];
            }
        }
        return copy;
    }
}
