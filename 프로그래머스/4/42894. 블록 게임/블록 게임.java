import java.util.*;

class Solution {
    static int[][][] blocks = new int[][][]{
        {{-1,0,0},{-1,-1,-1}},
        {{0,-1},{0,-1},{-1,-1}},
        {{-1,0},{-1,0},{-1,-1}},
        {{0,0,-1},{-1,-1,-1}},
        {{0,-1,0},{-1,-1,-1}}
    };                                  
    static int n;
    static int[] minRowBlock;  
    
    public int solution(int[][] board) {
        int answer = 0;
        n = board.length;
        
        minRowBlock = new int[n];
        updateMinRow(board, minRowBlock);
        
        boolean removed;
        do {
            removed = false;
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    for(int[][] block : blocks) {
                        if(canRemove(board, block, i, j)) {
                            answer++;
                            updateMinRow(board, minRowBlock);
                            removed = true;
                            break;
                        }
                    }
                    // if(removed) break;
                }
                // if(removed) break;
            }
        } while(removed);
        
        return answer;
    }
    
    static boolean canRemove(int[][] board, int[][] block, int row, int col) {
        int blockRow = block.length;
        int blockCol = block[0].length;
        
        // Check if the block fits within the board
        if(row + blockRow > n || col + blockCol > n) return false;
        
        // Find the block number
        int blockNum = -1;
        boolean blockNumFound = false;
        
        for(int i = row; i < row + blockRow; i++) {
            for(int j = col; j < col + blockCol; j++) {
                if(block[i-row][j-col] == -1 && board[i][j] != 0) {
                    if(blockNum == -1) {
                        blockNum = board[i][j];
                        blockNumFound = true;
                    } else if(board[i][j] != blockNum) {
                        return false;
                    }
                }
            }
        }
        
        if(!blockNumFound) return false;
        
        // Check block shape and empty spaces
        int emptyCells = 0;
        int[][] emptyPositions = new int[2][2];
        int k = 0;
        
        for(int i = 0; i < blockRow; i++) {
            for(int j = 0; j < blockCol; j++) {
                int currentRow = row + i;
                int currentCol = col + j;
                
                if(block[i][j] == -1 && board[currentRow][currentCol] != blockNum) 
                    return false;
                
                if(block[i][j] == 0 && board[currentRow][currentCol] == 0) {
                    if(k < 2) {
                        emptyPositions[k][0] = currentCol;
                        k++;
                    }
                }
            }
        }
        
        // Check if empty cells are at the bottom of their respective columns
        if(k == 2) {
            for(int i = 0; i < 2; i++) {
                if(minRowBlock[emptyPositions[i][0]] != blockNum) 
                    return false;
            }
            
            // Remove the block
            for(int i = 0; i < blockRow; i++) {
                for(int j = 0; j < blockCol; j++) {
                    if(block[i][j] == -1) {
                        board[row + i][col + j] = 0;
                    }
                }
            }
            
            return true;
        }
        
        return false;
    }
                                      
    static void updateMinRow(int[][] board, int[] minRowBlock) {
        Arrays.fill(minRowBlock, 0);
        
        for(int j = 0; j < n; j++) {
            for(int i = 0; i < n; i++) {
                if(board[i][j] != 0) {
                    minRowBlock[j] = board[i][j];
                    break;
                }
            }
        }
    }                                   
}