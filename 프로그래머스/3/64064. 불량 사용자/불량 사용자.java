import java.util.* ;

class Solution {
    
    static int answer;
    
    public int solution(String[] user_id, String[] banned_id) {
        
        //길이 비교 ->  *일 경우,
        answer = 0;
        int banLen = banned_id.length;
        int[] userIdx = new int[banLen];
        List<List<Integer>> candidate = new ArrayList<>();
        
        for(int i = 0 ; i < banLen ; i++){
            candidate.add(new ArrayList<>());
        }
        
            
        for(int i = 0 ; i< banLen; i++){
            for(int j= 0 ; j < user_id.length; j++){
                
                boolean find = true;
                if(user_id[j].length() == banned_id[i].length()){ //길이가 서로 같을 때
                    for(int k = 0 ; k < banned_id[i].length(); k++){
                        
                        if(banned_id[i].charAt(k) == user_id[j].charAt(k) || banned_id[i].charAt(k) == '*'){
                            continue;                                      
                        } else { find = false; 
                                break;}
                          
                    }
                    if(find){
                        candidate.get(i).add(j);
                    }
                }
            }
        }
        //다 찾았어
                
        
        for(int i = 0 ; i < candidate.size(); i++){
            System.out.println(candidate.get(i).toString());
        }
        
        int idx = 0;
        
        List<Set<Integer>> unique = new ArrayList<>();
        unique.add(new HashSet<>());
        
        dfs(0,candidate,new HashSet<>(), unique);
        
        return answer;
    }
    
    static void dfs(int idx, List<List<Integer>> candidate, Set<Integer> set, List<Set<Integer>> unique){
        
        
        if(set.size() == candidate.size()){
            
            boolean duplicate = true;
            
            
            for(int i = 0 ; i< unique.size() ; i++){
                int sameCnt = 0;
                
                for(int a : unique.get(i)){
                    if(set.contains(a)){
                        sameCnt++;
                    }    
                }
                if(sameCnt == set.size()) return;
            }
            unique.add(new HashSet<>(set));
            
            answer++;
            
            return;
        }
        
        if(idx >= candidate.size()){
            return;
        }
        
        for(int i = 0; i < candidate.get(idx).size(); i++){
           int num =  candidate.get(idx).get(i);
            
            if(!set.contains(num)){
                set.add(num);
                dfs(idx+1, candidate, set, unique);
                set.remove(num);
            }
        }

    }
}