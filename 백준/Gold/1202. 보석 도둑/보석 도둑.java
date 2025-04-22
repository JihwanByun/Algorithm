import java.lang.reflect.Array;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int K = sc.nextInt();

         Gem[] gems = new Gem[N]; //무게,값

        for (int i = 0; i < N; i++) {
            gems[i] = new Gem(sc.nextInt(), sc.nextInt());
        }

        int[] bags = new int[K];
        for(int i = 0 ; i < K ; i++) {
           bags[i] = sc.nextInt();
        }

        Arrays.sort(gems, (a,b) -> {
            if(a.w != b.w) return a.w - b.w;
            else return a.v - b.v;
        });

        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        Arrays.sort(bags);
        int idx = 0;

        long answer = 0;
        for (int i = 0 ; i < K ; i++) {
            while (idx < N && bags[i] >= gems[idx].w) {
                pq.add(gems[idx].v);
                idx++;
            }
            if(!pq.isEmpty())
                answer += pq.poll();
        }

        System.out.println(answer);
    }
}

class Gem {
    int w;
    int v;
    Gem(int w, int v) {
        this.w = w;
        this.v = v;
    }
}
