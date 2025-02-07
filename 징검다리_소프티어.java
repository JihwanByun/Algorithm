import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        int[] stones = new int[N];

        for(int i = 0 ; i < N ; i++){
            stones[i] = sc.nextInt();
        }
        
        int[] dp = new int[N];
        Arrays.fill(dp,1);

        int maxLIS = 1;
        
        for(int i = 1; i < N ;i++){
            for(int j = 0 ; j < i ; j++){
                if(stones[j] < stones[i])
                    dp[i] = Math.max(dp[i], dp[j]+1);
            }
            maxLIS = Math.max(dp[i], maxLIS);
        }

        System.out.println(maxLIS);
    }
}
