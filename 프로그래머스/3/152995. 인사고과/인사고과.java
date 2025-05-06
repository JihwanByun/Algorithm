import java.util.*;
class Solution {
    public int solution(int[][] scores) {
        // 완호의 점수 저장
        int wanHoS1 = scores[0][0];
        int wanHoS2 = scores[0][1];
        int wanHoTotal = wanHoS1 + wanHoS2;
        
        // 총점 기준으로 내림차순 정렬
        Arrays.sort(scores, (a, b) -> (b[0] + b[1]) - (a[0] + a[1]));
        
        // 완호가 1등인 경우 바로 확인
        if(wanHoS1 == scores[0][0] && wanHoS2 == scores[0][1]) {
            return 1;
        }
        
        int rank = 1;  // 현재 순위
        int sameScoreCount = 1;  // 동점자 수
        
        for(int i = 1; i < scores.length; i++) {
            // 현재 참가자의 총점
            int currentTotal = scores[i][0] + scores[i][1];
            // 이전 참가자의 총점
            int prevTotal = scores[i-1][0] + scores[i-1][1];
            
            // 제외 대상인지 확인
            boolean isExcluded = false;
            for(int j = 0; j < i; j++) {
                if(scores[j][0] > scores[i][0] && scores[j][1] > scores[i][1]) {
                    isExcluded = true;
                    break;
                }
            }
            
            // 제외 대상인 경우 건너뛰기
            if(isExcluded) {
                // 완호가 제외 대상인 경우
                if(wanHoS1 == scores[i][0] && wanHoS2 == scores[i][1]) {
                    return -1;
                }
                continue;
            }
            
            // 동점이 아닌 경우 순위 업데이트
            if(currentTotal != prevTotal) {
                rank += sameScoreCount;
                sameScoreCount = 1;
            } else {
                sameScoreCount++;
            }
            
            // 현재 참가자가 완호인 경우
            if(wanHoS1 == scores[i][0] && wanHoS2 == scores[i][1]) {
                return rank;
            }
        }
        
        // 여기까지 왔다면 완호는 순위가 없는 경우
        return -1;
    }
}