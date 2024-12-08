import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // 입력받기
        int L = scanner.nextInt();
        int R = scanner.nextInt();

        // 초기 조건
        int minEights = 0;

        // 자릿수 문자열로 변환
        String lStr = Integer.toString(L);
        String rStr = Integer.toString(R);

        // 자릿수가 다르면 8의 최소 개수는 무조건 0
        if (lStr.length() != rStr.length()) {
            System.out.println(0);
            return;
        }


        int i = 0;
        while( i < lStr.length() && lStr.charAt(i) == rStr.charAt(i)){
            if(lStr.charAt(i) == '8'){
                minEights++;
            }
            i++;
        }
        
        // 결과 출력
        System.out.println(minEights);
    }
}