import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());
        int[] expected = new int[N];
        
        for (int i = 0; i < N; i++) {
            expected[i] = Integer.parseInt(br.readLine());
        }
        
        Arrays.sort(expected);  // 예상 등수를 오름차순 정렬
        
        long totalDissatisfaction = 0;
        for (int i = 0; i < N; i++) {
            int actualRank = i + 1;
            totalDissatisfaction += Math.abs(expected[i] - actualRank);
        }
        
        System.out.println(totalDissatisfaction);
    }
}
