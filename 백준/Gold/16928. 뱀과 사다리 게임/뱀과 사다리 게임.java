import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int M = sc.nextInt();

        int[] board = new int[101];

        for(int i = 1; i <= 100; i++){
            board[i] = i;
        }

        for(int i = 0 ; i< N; i++){
            int from = sc.nextInt();
            int to = sc.nextInt();
            board[from] = to;
        }

        for(int i = 0 ; i < M ; i++){
            int from = sc.nextInt();
            int to = sc.nextInt();
            board[from] = to;
        }
        int[] dist = new int [101];
        boolean[] visited =new boolean[101];

        Queue<Integer> q = new LinkedList<>();
        q.add(1);
        visited[1] = true;

        while (!q.isEmpty()){
            int cur = q.poll();
            for(int i = cur+1 ; i <= cur+6 ; i++){
                if(i > 100) continue;

                int next = board[i];
                if(!visited[next]){
                    visited[next] = true;
                    dist[next] = dist[cur] + 1;
                    q.add(next);
                }
            }
        }

        System.out.println(dist[100]);
    }
}