import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
        // 모든 보석의 종류 확인
        Set<String> gemTypes = new HashSet<>(Arrays.asList(gems));
        int typeCount = gemTypes.size();
        
        // 구간의 시작과 끝, 최소 길이 초기화
        int start = 0, minStart = 0, minLength = Integer.MAX_VALUE;
        
        // 현재 윈도우의 보석 종류를 추적할 맵
        Map<String, Integer> currentGems = new HashMap<>();
        
        // 투 포인터 알고리즘
        for (int end = 0; end < gems.length; end++) {
            // 현재 보석 추가
            currentGems.put(gems[end], currentGems.getOrDefault(gems[end], 0) + 1);
            
            // 현재 윈도우에 모든 보석 종류가 포함되었을 때
            while (currentGems.size() == typeCount) {
                // 현재 구간 길이 계산
                if (end - start + 1 < minLength) {
                    minLength = end - start + 1;
                    minStart = start;
                }
                
                // 시작 지점 보석 제거
                currentGems.put(gems[start], currentGems.get(gems[start]) - 1);
                if (currentGems.get(gems[start]) == 0) {
                    currentGems.remove(gems[start]);
                }
                start++;
            }
        }
        
        // 1-based 인덱스로 변환
        return new int[]{minStart + 1, minStart + minLength};
    }
}