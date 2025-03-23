import java.util.*;

class Solution {
    static Map<Long, Long> parent;

    public long[] solution(long k, long[] room_number) {
        int n = room_number.length;
        long[] result = new long[n];
        parent = new HashMap<>();

        for (int i = 0; i < n; i++) {
            result[i] = find(room_number[i]);
        }

        return result;
    }

    // 경로 압축을 활용한 유니온 파인드
    static long find(long room) {
        if (!parent.containsKey(room)) {
            // 이 방이 비어있으면 그대로 배정하고, 다음 방을 parent로 등록
            parent.put(room, room + 1);
            return room;
        }

        // 이 방이 이미 배정되었으면, 다음 빈 방을 재귀적으로 찾기
        long next = find(parent.get(room));
        parent.put(room, next); // 경로 압축
        return next;
    }
}
