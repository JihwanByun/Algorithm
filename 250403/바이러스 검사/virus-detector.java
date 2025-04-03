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
            if(rest <= maxInspectForLeader) {
                answer++;
                continue;
            }

            int mod = rest - maxInspectForLeader;
            int follower = mod % maxInspectForFollower > 0 ? mod / maxInspectForFollower + 1 : mod / maxInspectForFollower;

            answer += follower + 1;
        }

        System.out.println(answer);
    }
}