import java.util.*;

class Solution {
    static int n,m;
    public boolean solution(int[][] key, int[][] lock) {
        boolean answer = true;
        
        n = lock.length;
        m = key.length;
        
         int[][] copy = new int [m][m];
        
        for(int i = 0; i < m ; i++){
            copy[i] = key[i].clone();
        }
        for(int rot = 0 ; rot < 4 ; rot++){
            
            if(canUnlock(key, lock)){
                
                return true;
            }
            key = rotate(key);
        }
        
        return false;
    }
    
    boolean canUnlock(int[][] copy, int[][] lock){
        
        int expandLength = n-2 + 2*m;
        
        int[][] expandLock = new int [expandLength][expandLength];
        
        for(int i = 0 ; i < n ; i++){
            for(int j = 0 ; j < n ;j++){
                 expandLock[m-1+i][m-1+j] = lock[i][j];
            }
        }
        
        for(int i = 0 ; i<= expandLength - m; i++){
            for(int j= 0 ; j<= expandLength - m; j++){
                if(checkUnLock(i,j,copy, expandLock)){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    boolean checkUnLock(int r, int c, int[][] copy, int[][] expandLock){
        
        int[][] copyExpand = new int [expandLock.length][expandLock.length];
        
        for (int i = 0; i < expandLock.length; i++) {
            copyExpand[i] = expandLock[i].clone();
        }
        
        for(int i = r; i < r+m ; i++){
            for(int j = c ; j < c+m; j++){
                copyExpand[i][j] += copy[i-r][j-c];
            }
        }
        for(int i = m -1 ; i< m-1+n;i++){
            for(int j = m - 1; j < m-1+n;j++){
                if(copyExpand[i][j] != 1){
                    return false;
                }
            }
        }
        
        
        return true;
    }
    
    int[][] rotate(int[][] key){
    
        int[][] copy = new int[m][m];
        
        for(int i = 0 ; i < m ; i++){
            for(int j = 0 ; j < m ; j++){
                copy[i][j] = key[ m - j -1][i];
            }
        }
        return copy;
    }
}
