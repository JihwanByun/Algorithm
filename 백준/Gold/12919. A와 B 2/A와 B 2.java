import java.util.Scanner;

public class Main {
    static String S, T;
    static boolean result = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        S = sc.next();
        T = sc.next();

        dfs(T);
        System.out.println(result ? 1 : 0);
    }

    static void dfs(String current) {
        if (current.length() == S.length()) {
            if (current.equals(S)) {
                result = true;
            }
            return;
        }

        if (result) return; // 이미 찾았으면 더 안 봐도 됨

        if (current.endsWith("A")) {
            dfs(current.substring(0, current.length() - 1));
        }

        if (current.startsWith("B")) {
            StringBuilder sb = new StringBuilder(current.substring(1));
            dfs(sb.reverse().toString());
        }
    }
}
