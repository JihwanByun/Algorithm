import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sc =new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        List<Integer> negative = new ArrayList<>();
        List<Integer> positive = new ArrayList<>();

        for(int i = 0; i< N ; i++) {
            int bookP = sc.nextInt();
            if (bookP < 0) {
                negative.add(-bookP);
            } else {
                positive.add(bookP);
            }
        }
        Collections.sort(negative);
        Collections.sort(positive);

        //가장 먼 애를 마지막에 간다.
        if(positive.isEmpty() ){
            System.out.println(move(negative,M));
        } else if( negative.isEmpty()){
            System.out.println(move(positive,M));
        } else {
            int maxFarDir = 0;

            int positiveMax = positive.get(positive.size() -1);
            int negativeMax = negative.get(negative.size()- 1);

            maxFarDir = positiveMax > negativeMax ? 1 : -1;
            if(maxFarDir == 1){
                System.out.println(move(positive,M) + moveAndReturn(negative,M) );
            } else {
                System.out.println(moveAndReturn(positive,M) + move(negative,M) );
            }
        }

    }
    static int moveAndReturn(List<Integer> one, int M) {
        int result = 0;
        int lastIdx = one.size() - 1;
        int i = lastIdx;
        for (; i >= M - 1 ; i -= M) {
            result += one.get(i) * 2;  // 마지막부터 M 간격으로 값을 두 배해서 더함
        }

        if(i  >=  0){
            result += one.get(i) * 2;
        }
          // M번째 요소를 두 배로 더함
        return result;
    }


    static int move(List<Integer> one, int M) {
        int result = 0;
        int lastIdx = one.size() - 1;

        result += one.get(lastIdx);  // 마지막 요소를 더함
        
        int i = lastIdx - M;

        for (; i >= M -1  ; i -= M) {
            result += one.get(i) * 2;  // 마지막에서 M 간격으로 값을 두 배로 해서 더함
        }

        if(i  >=  0){
            result += one.get(i) * 2;
        }  // M번째 요소를 두 배로 더함
        return result;
    }

}