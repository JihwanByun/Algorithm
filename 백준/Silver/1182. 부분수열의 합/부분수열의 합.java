import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int N = sc.nextInt();
        int S = sc.nextInt();

        int[] num = new int[N];

        for(int i = 0 ; i < N ; i++) {
            num[i] = sc.nextInt();
        }

        ArrayList<Integer> list = new ArrayList<>();
        for(int n : num) {
            list.add(n);
            int size = list.size();
            for(int i = 0 ; i < size-1; i++) {
                list.add(list.get(i) + n);
            }
        }
        int answer = 0;
        for(int n : list) {
            if(n == S) {
                answer++;
            }
        }
        System.out.println(answer);
    }
}