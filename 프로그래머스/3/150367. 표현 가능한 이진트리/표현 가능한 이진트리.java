import java.util.*;

class Solution {
    public int[] solution(long[] numbers) {
        List<Integer> list = new ArrayList<>();
        
        for (long number : numbers) {
            String binary = Long.toBinaryString(number);

            int len = binary.length();
            int height = 1;

            // 트리의 높이(height) 계산
            while (len > Math.pow(2, height) - 1) {
                height++;
            }

            // 이진수를 완전 이진트리 길이로 맞추기 위해 앞에 '0' 추가
            int fullLength = (int) Math.pow(2, height) - 1;
            while (binary.length() < fullLength) {
                binary = "0" + binary;
            }

            // 루트, 왼쪽, 오른쪽 서브트리 검사
            boolean isValid = search(0, binary.length() - 1, binary);
            list.add(isValid ? 1 : 0);
        }

        // List<Integer>를 int[]로 변환
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    boolean search(int start, int end, String binary) {
        // 루트 노드
        int root = (start + end) / 2;

        // 루트가 0인데 왼쪽 또는 오른쪽 자식이 1인 경우 유효하지 않음
        if (binary.charAt(root) == '0') {
            for (int i = start; i <= end; i++) {
                if (binary.charAt(i) == '1') {
                    return false;
                }
            }
        }

        // 더 이상 분할할 수 없으면 true 반환
        if (start == end) {
            return true;
        }

        // 왼쪽과 오른쪽 서브트리를 재귀적으로 검사
        return search(start, root - 1, binary) && search(root + 1, end, binary);
    }
}
