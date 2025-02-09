import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();

        Queue<Integer> queue = new LinkedList<>();

        // 1부터 N까지의 카드 초기화
        for (int i = 1; i <= N; i++) {
            queue.add(i);
        }

        // 카드가 한 장 남을 때까지 반복
        while (queue.size() > 1) {
            // 제일 위에 있는 카드 버리기
            queue.poll();

            // 제일 위에 있는 카드를 제일 아래로 옮기기
            int topCard = queue.poll();
            queue.add(topCard);
        }

        // 마지막에 남은 카드 출력
        System.out.println(queue.poll());
    }
}