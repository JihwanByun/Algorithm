import java.util.*;

class Solution {
    static int[][] dice;
    static int n;
    static boolean[] visited;
    static int max;
    static int[] answer;
    
    public int[] solution(int[][] dice) {
        this.n = dice.length;
        this.dice = dice;
        visited = new boolean[n];
        max = 0;
        
        answer = new int[n/2];
        combination(0,0);
        
        Arrays.sort(answer);
        return answer;
    }
    
    static void combination(int idx, int a) {
        if(n/2 <= a) {
            List<int[]> listA = new ArrayList<>();
            List<int[]> listB = new ArrayList<>();
            
            for(int i = 0; i < n ; i++){
                if(visited[i]) listA.add(dice[i]);
                else listB.add(dice[i]);
            }
            int num = calculate(listA, listB);
            if(max < num) {
                max = num;
                int j = 0;
                for(int i = 0 ; i < n ; i++) {
                    if(visited[i])
                        answer[j++] = i+1;
                }
            }
            return;
        }
        if(idx >= n) return;
        
        visited[idx] = true;
        combination(idx + 1, a+1);
        visited[idx] = false;
        combination(idx + 1, a);
    }
    
    public static int calculate(List<int[]> listA, List<int[]> listB) {
        List<Integer> sumA = new ArrayList<>();
        List<Integer> sumB = new ArrayList<>();
        
        getSum(listA, 0, 0, sumA);
        getSum(listB, 0, 0, sumB);
        
        Collections.sort(sumB);
        
        int win = 0;
        for (int a : sumA) {
            int count = upperBound(sumB, a);
            win += count;
        }
        
        return win;
    }
    
    static void getSum(List<int[]> list, int idx, int sum, List<Integer> sums) {
        if (idx == list.size()) {
            sums.add(sum);
            return;
        }
        
        for (int num : list.get(idx)) {
            getSum(list, idx + 1, sum + num, sums);
        }
    }
    
    static int upperBound(List<Integer> list, int target) {
        int left = 0;
        int right = list.size();
        
        while (left < right) {
            int mid = (left + right) / 2;
            if (list.get(mid) < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
