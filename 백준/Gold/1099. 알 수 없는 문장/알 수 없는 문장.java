import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String S = sc.next();
        String[] words = new String[sc.nextInt()];

        for (int i = 0; i < words.length; i++) {
            words[i] = sc.next();
        }
        int[] dp = new int[S.length() + 1];
        for(int i = 0; i <= S.length(); i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        dp[0] = 0;

        for(int i = 1; i <= S.length(); i++) {
            for(String word : words) {
                int len = word.length();
                if (i >= len) {
                    int cost = getCost(S.substring(i - len, i), word);
                    if (cost != -1) {
                        if(dp[i-len] != Integer.MAX_VALUE)
                            dp[i] = Math.min(dp[i], dp[i-len] + cost);
                    }
                }
            }
        }

        System.out.println(dp[S.length()] == Integer.MAX_VALUE ? -1 : dp[S.length()]);
    }
    private static int getCost(String s, String word) {
        if (s.length() != word.length()) return -1;

        // 각 문자의 빈도수 체크
        int[] freqS = new int[26];
        int[] freqWord = new int[26];

        for (char c : s.toCharArray()) {
            freqS[c - 'a']++;
        }
        for (char c : word.toCharArray()) {
            freqWord[c - 'a']++;
        }

        // 빈도수가 다르면 -1 반환
        for (int i = 0; i < 26; i++) {
            if (freqS[i] != freqWord[i]) {
                return -1;
            }
        }

        // 위치가 다른 문자 개수 세기
        int cost = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != s.charAt(i)) {
                cost++;
            }
        }
        return cost;
    }
}