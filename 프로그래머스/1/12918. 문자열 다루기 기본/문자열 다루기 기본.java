class Solution {
    public boolean solution(String s) {
        char[] arr = s.toCharArray();
        
        if(!(arr.length == 4 || arr.length == 6)) return false;
        
        for(char c : arr) {
            if(!Character.isDigit(c)) return false;
        }
        
        return true;
    }
}