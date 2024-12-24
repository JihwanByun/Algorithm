class Solution {
    public int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];
        int idx = 0;
        
        for (long number: numbers) {
            String binary = Long.toBinaryString(number);
            
            // 포화 이진트리 높이 계산
            int height = 1;
            int length = 1;
            while (length < binary.length()) {
                height++;
                length = (1 << height) - 1;
            }
            
            // 앞에 0 채우기
            binary = "0".repeat(length - binary.length()) + binary;
            answer[idx++] = isValidTree(binary) ? 1 : 0;
        }
        return answer;
    }
    
    private boolean isValidTree(String binary) {
        if (binary.length() <= 1) return true;
        
        int mid = binary.length() / 2;
        char root = binary.charAt(mid);
        
        // 왼쪽, 오른쪽 서브트리
        String left = binary.substring(0, mid);
        String right = binary.substring(mid + 1);
        
        // 루트가 0인데 자식이 1이면 invalid
        if (root == '0') {
            return !left.contains("1") && !right.contains("1");
        }
        
        return isValidTree(left) && isValidTree(right);
    }
}