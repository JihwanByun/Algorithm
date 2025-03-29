import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int K = sc.nextInt();  // 입력 개수
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < K; i++) {
            int num = sc.nextInt();

            if (num == 0) {
                stack.pop();  // 최근 숫자 제거
            } else {
                stack.push(num);  // 숫자 저장
            }
        }

        // 합 계산
        int sum = 0;
        for (int n : stack) {
            sum += n;
        }

        System.out.println(sum);
    }
}
