import java.util.*;

class Solution {
    public String solution(int n, int k, String[] cmd) {
        StringBuilder answer = new StringBuilder();
        int idx = k;
        Stack<Integer> stack =new Stack<>();
        boolean[] canceled = new boolean[n];
        Node[] nodes = new Node[n];
        
        for(int i = 0 ; i< n ;i++)
            nodes[i] = new Node(i);
        
        for(int i = 0 ; i < n ; i++){
            
            if(i==0){
                nodes[i].prev = nodes[n-1];
                nodes[i].next = nodes[1];
                
            } else if(i== n-1){
                nodes[i].prev = nodes[n-2];
                nodes[i].next = nodes[0];
            } else {
                nodes[i].prev = nodes[i-1];
                nodes[i].next = nodes[i+1];
            }
        }
        
        
        for(String c : cmd){
            
            if(c.length() > 1){
                
                if(c.charAt(0) == 'U'){
                    
                    int num = Integer.parseInt(c.substring(2));
                    int moved = 0;
                    
                    while(moved < num){
                        idx = nodes[idx].prev.num;
                        moved++;
                    }
                    
                } else if(c.charAt(0) == 'D'){
                    
                    int num = Integer.parseInt(c.substring(2));
                    int moved = 0;
                    
                    while(moved < num){
                        idx = nodes[idx].next.num;
                        moved++;
                    }
                    
                }
            } else if(c.charAt(0) == 'C'){ //캔슬
                
                int prevNum = nodes[idx].prev.num; 
                int nextNum = nodes[idx].next.num;
                
                
                
                nodes[prevNum].next = nodes[nextNum]; 
                nodes[nextNum].prev = nodes[prevNum]; 
                
                
                
                stack.push(idx);
                canceled[idx] = true;
                
                if(nextNum == 0)
                    idx = prevNum;
                else idx = nextNum;
                
            } else if(c.charAt(0) == 'Z'){ //되돌리기
                
                int last = stack.pop();
                canceled[last] = false;
                
                int prevNum = nodes[last].prev.num; 
                int nextNum = nodes[last].next.num;
                
                nodes[prevNum].next = nodes[last]; 
                nodes[nextNum].prev = nodes[last]; 
            }
            
        }
        
        for(int i = 0 ; i< n ; i++){
           answer.append(canceled[i] ? "X" : "O");
        }
        
        return answer.toString();
    }
    
    
}

class Node{
    int num;
    Node next;
    Node prev;
    
    Node(int num){
        this.num = num;
    }
    
}