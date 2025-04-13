import java.util.*;

class Solution {
    static Map<Long, Long> room = new HashMap<>();
    
    public long[] solution(long k, long[] room_number) {
        
        int n = room_number.length;
        long[] answer = new long[n];
        int idx = 0;
        for(long num : room_number) {
            answer[idx++] = find(num);
        }
        
        return answer;
    }
    
    static long find(long num) {
        if(!room.containsKey(num)) {
            room.put(num, num+1);
            return num;
        }
        
        long nxt = find(room.get(num));
        room.put(num, nxt);
        return nxt;
    }
}