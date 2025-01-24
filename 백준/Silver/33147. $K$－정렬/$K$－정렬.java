import java.util.HashSet;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc =new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();

        int[] num = new int[N];

        for(int i =0 ; i < N ; i++){
            num[i] = sc.nextInt();
        }
        boolean[] visited = new boolean[N];
        int visitCnt = 0;

        for(int k = 0 ; k < N ; k++){
            if(visited[k]) continue;

            int cnt = 0;

            HashSet<Integer> set = new HashSet<>();
            for(int i = k ; ; i = (i+K) % N ){
                if(visited[i]) break;
                visited[i] = true;
                set.add(num[i]);
            }

            for(int i = 0 ; i < N ; i++){
                if(visited[i] && set.contains(i)){
                    cnt++;
                }
            }
            if(cnt != set.size()){
                System.out.println("NO");
                return;
            } else visitCnt += cnt;
        }

        System.out.println(visitCnt == N ? "YES" : "NO");
    }
}