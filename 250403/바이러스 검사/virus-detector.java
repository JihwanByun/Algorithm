import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Please write your code here.
        Scanner sc =new Scanner(System.in);

        int restCnt = sc.nextInt();

        int[] rests = new int[restCnt];
        for(int i = 0 ; i < restCnt ; i++) {
            rests[i] = sc.nextInt();
        }

        int maxInspectForLeader = sc.nextInt();
        int maxInspectForFollower = sc.nextInt();

        int answer = 0;

        for(int rest : rests) {
            
            int remain = rest - maxInspectForLeader;
            answer++;

            if (remain > 0) {
                answer += (remain + maxInspectForFollower - 1) / maxInspectForFollower;
            }
            
        }

        System.out.println(answer);
    }
}