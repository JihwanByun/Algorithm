class Solution {
    public int solution(int[] stones, int k) {
        int right = 2000000000;
        int left = 0;
        
        while(left <= right) {
            //System.out.println("left = " + left + " right = " + right);
            int mid = (left + right) / 2;
            
            boolean flag = false;
            int cnt = 0;
            for(int i = 0 ; i < stones.length ; i++) {    
                if(stones[i] < mid) {
                    cnt++;
                    if(cnt >= k) {
                        flag = true;
                        break;
                    }
                } else cnt = 0;
            }
            
            if(!flag)
                left = mid + 1;
            else right = mid - 1;
        }
        
        return right;
    }
}