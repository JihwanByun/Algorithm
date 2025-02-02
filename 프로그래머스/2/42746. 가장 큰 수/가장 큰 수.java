import java.util.*;

class Solution {
    public String solution(int[] numbers) {
        // 숫자를 문자열로 변환
        String[] strNumbers = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            strNumbers[i] = String.valueOf(numbers[i]);
        }
        
        // 두 수를 이어 붙였을 때 더 큰 수가 되도록 정렬
        Arrays.sort(strNumbers, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return (b + a).compareTo(a + b);
            }
        });
        
        // 모든 숫자가 0인 경우 처리
        if (strNumbers[0].equals("0")) {
            return "0";
        }
        
        // 정렬된 문자열을 이어 붙여 결과 생성
        StringBuilder result = new StringBuilder();
        for (String str : strNumbers) {
            result.append(str);
        }
        
        return result.toString();
    }
}