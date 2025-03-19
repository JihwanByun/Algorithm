import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        int N = sc.nextInt(); // 참가자 수
        int kim = sc.nextInt(); // 김지민의 번호
        int lim = sc.nextInt(); // 임한수의 번호
        
        int round = 0;
        
        // 두 사람이 같은 번호가 될 때까지 반복
        while (kim != lim) {
            kim = (kim + 1) / 2; // 다음 라운드의 번호
            lim = (lim + 1) / 2; // 다음 라운드의 번호
            round++;
        }
        
        System.out.println(round);
    }
}
