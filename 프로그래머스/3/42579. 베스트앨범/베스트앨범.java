import java.util.*;

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        
        Map<String, PriorityQueue<Song>> map = new HashMap<>();
        Map<String, Integer> sumOfPlayPerGenre = new HashMap<>();
        int n = genres.length;
        
        for(int i = 0 ; i < n ; i++){
            sumOfPlayPerGenre.putIfAbsent(genres[i], 0);
            sumOfPlayPerGenre.put(genres[i], sumOfPlayPerGenre.get(genres[i]) + plays[i]);
            
            map.putIfAbsent(genres[i], new PriorityQueue<>(new Comparator<Song>(){
                @Override
                public int compare(Song s1, Song s2){
                    if(s1.play == s2.play){
                        return s1.uniqueNum - s2.uniqueNum;
                    }
                    return s2.play - s1.play;
                }
            }));
            map.get(genres[i]).add(new Song(i, plays[i]));
        }
        
        PriorityQueue<Album> albumQ = new PriorityQueue<>(new Comparator<>(){
            @Override
            public int compare(Album a1, Album a2){
                return a2.sum - a1.sum;
            }
        });
        
        for(Map.Entry<String, Integer> entry : sumOfPlayPerGenre.entrySet()){
                albumQ.add(new Album(entry.getKey(), entry.getValue()));
        }
        
        
        List<Integer> ans = new ArrayList<>();
        
        while(!albumQ.isEmpty()){
            Album a = albumQ.poll();
        
            ans.add(map.get(a.genre).poll().uniqueNum);
            if(map.get(a.genre).isEmpty()) continue;
            ans.add(map.get(a.genre).poll().uniqueNum);
        }
    
        return ans.stream().mapToInt(i -> i).toArray();
    }
}
    

class Album{
    int sum;
    String genre;
    Album(String genre, int sum){
        this.genre = genre;
        this.sum = sum;
    }
}


class Song{
    
    int uniqueNum;
    int play;
    
    Song(int uniqueNum, int play){
        this.play = play;
        this.uniqueNum = uniqueNum;
    }
}