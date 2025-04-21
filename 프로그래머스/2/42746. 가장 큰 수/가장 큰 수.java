import java.util.*;

class Solution {
    public String solution(int[] numbers) {
        
        String[] arr = new String[numbers.length];
        
        boolean flag = false;
        
        for(int i = 0 ; i < numbers.length; i++) {
            arr[i] = Integer.toString(numbers[i]);
        }
        
        
        Arrays.sort(arr, (a,b) -> {
           return (b + a).compareTo(a+b);
        });
        
        if(Integer.parseInt(arr[0]) == 0) return "0";
        
        StringBuilder sb = new StringBuilder();
        for(String s : arr) {
            sb.append(s);
        }
        
        return sb.toString();
    }
}