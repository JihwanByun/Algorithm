import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String document = sc.nextLine();
        String word = sc.nextLine();
        
        System.out.println(countOccurrences(document, word));
    }
    
    static int countOccurrences(String document, String word) {
        int count = 0;
        int index = 0;
        
        while ((index = document.indexOf(word, index)) != -1) {
            count++;
            index += word.length(); // 겹치지 않도록 이동
        }
        
        return count;
    }
}