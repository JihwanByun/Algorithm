import java.util.*;

class Solution {
    public int[] solution(String[] operations) {
        
        PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> min = new PriorityQueue<>();
        
        for(String operation : operations) {
            String[] op = operation.split(" ");
            
            if(op[0].equals("I")) {
                int num = Integer.parseInt(op[1]);
                max.add(num);
                min.add(num);
            } else {
                int symbol = Integer.parseInt(op[1]);
                if(symbol < 0 && !min.isEmpty()) {
                    int minValue = min.peek();
                    min.poll();
                    max.remove(minValue);
                } else if(!min.isEmpty()) {
                    int maxValue = max.peek();
                    max.poll();
                    min.remove(maxValue);
                }
            }
        }
        
        int minAns = min.isEmpty() ? 0 : min.poll(); 
        int maxAns = max.isEmpty() ? 0 : max.poll();
        
        int[] answer = {maxAns, minAns};
        return answer;
    }
}