import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt();
        Integer[] A = new Integer[N];
        Integer[] B = new Integer[N];

        for (int i = 0; i < N; i++) A[i] = sc.nextInt();
        for (int i = 0; i < N; i++) B[i] = sc.nextInt();

        Arrays.sort(A); // A는 오름차순 정렬

        // B의 인덱스를 보존한 채로, B값이 큰 순서로 인덱스를 정렬
        Integer[] B_index = new Integer[N];
        for (int i = 0; i < N; i++) B_index[i] = i;

        Arrays.sort(B_index, (i, j) -> B[j] - B[i]); // B값이 큰 순

        int[] A_reordered = new int[N];
        for (int i = 0; i < N; i++) {
            A_reordered[B_index[i]] = A[i]; // B가 클수록 A의 작은 수를 대응
        }

        int result = 0;
        for (int i = 0; i < N; i++) {
            result += A_reordered[i] * B[i];
        }

        System.out.println(result);
    }
}
