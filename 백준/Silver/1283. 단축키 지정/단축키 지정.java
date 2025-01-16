import java.util.*;

public class Main {
    public static String[] assignShortcuts(String[] options) {
        Set<Character> usedShortcuts = new HashSet<>();
        String[] result = new String[options.length];
        
        for (int i = 0; i < options.length; i++) {
            String option = options[i];
            String[] words = option.split(" ");
            boolean found = false;
            int shortcutPos = -1;
            
            // 1단계: 각 단어의 첫 글자 확인
            for (int j = 0; j < words.length; j++) {
                char firstChar = Character.toLowerCase(words[j].charAt(0));
                if (!usedShortcuts.contains(firstChar)) {
                    usedShortcuts.add(firstChar);
                    // 단축키 위치 계산
                    shortcutPos = 0;
                    for (int k = 0; k < j; k++) {
                        shortcutPos += words[k].length() + 1; // +1은 공백 고려
                    }
                    found = true;
                    break;
                }
            }
            
            // 2단계: 모든 글자를 순서대로 확인
            if (!found) {
                String fullText = String.join(" ", words);
                for (int j = 0; j < fullText.length(); j++) {
                    char c = Character.toLowerCase(fullText.charAt(j));
                    if (Character.isLetter(c) && !usedShortcuts.contains(c)) {
                        usedShortcuts.add(c);
                        shortcutPos = j;
                        found = true;
                        break;
                    }
                }
            }
            
            // 결과 문자열 생성
            if (found) {
                String fullText = String.join(" ", words);
                result[i] = fullText.substring(0, shortcutPos) + 
                           "[" + fullText.charAt(shortcutPos) + "]" + 
                           fullText.substring(shortcutPos + 1);
            } else {
                result[i] = String.join(" ", words);
            }
        }
        
        return result;
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        sc.nextLine(); // 개행문자 처리
        
        String[] options = new String[N];
        for (int i = 0; i < N; i++) {
            options[i] = sc.nextLine();
        }
        
        String[] result = assignShortcuts(options);
        for (String line : result) {
            System.out.println(line);
        }
        
        sc.close();
    }
}