import java.util.*;

class Solution {
    public int solution(int[][] triangle) {
        int answer = 0;
        int height = triangle.length;
        
        for(int i = 0 ; i < height - 1 ; i++){
            int width = triangle[i].length;
            for(int j = 0 ; j < width ; j++){
                
                if( j == width - 1 ){
                    triangle[i+1][j+1] += triangle[i][j];
                }
                
                if( j - 1 >= 0){
                    triangle[i+1][j] += Math.max(triangle[i][j-1], triangle[i][j]);
                }
                else triangle[i+1][j] += triangle[i][j];
            }
        }
        
        for(int i= 0 ; i< triangle[height-1].length; i++){
            answer = Math.max(answer, triangle[height-1][i]);
        }
        
        return answer;
    }
}