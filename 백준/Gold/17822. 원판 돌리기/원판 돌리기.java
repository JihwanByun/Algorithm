/*
i번째 원판에 적힌 j번째 수의 위치는 (i,j)로 표현 가능

N, M, T (반지름의 크기 N, 원판에 적힌 정수의 개수 M, 회전 수 T)
원판을 T번 회전시킨 후 원판에 적힌 수의 합 출력

양옆이랑 위아래가 인접하다
사실상 2차원 배열과 유사

다음 T개의 줄에는 xi,di,ki 주어짐
xi의 배수인 원판을 di방향으로 ki칸 회전 (T번 동안)
 */


import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static int[][] disc;
    static int N,M,T;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        T = sc.nextInt();

        int ans = 0;
        disc = new int[N][M];
        for(int i = 0 ; i < N;i++)
            for(int j = 0  ; j<M;j++)
                disc[i][j] = sc.nextInt();

        for(int num = 0; num <T; num++){
            rotate(sc.nextInt(),sc.nextInt(),sc.nextInt());
        }
        for(int i = 0 ; i< N; i++){
            for(int j = 0 ; j<M;j++){
                ans += disc[i][j];
            }
        }
        System.out.println(ans);
    }

    static void rotate(int x, int d,int k){

        for(int i = x-1 ;i< N; i+=x){
            for(int num = 0 ; num < k; num ++){ //k번 회전하기
                    move(i,d);
            }
        }
        //회전 후


        //인접한 숫자 같은 거 있으면 지우기 없으면 평균에서 작은 수는 +1 , 큰 수는 -1 하기
        if(isadj_equal()){

        }
        else {
            double sum = 0;
            int cnt = 0;
            for(int i = 0 ; i<N;i++){
                for(int j = 0 ; j<M; j++){
                    if(disc[i][j] >0) {
                        sum += disc[i][j];
                        cnt++;
                    }
                }
            }
            double avg = sum / cnt;
            for(int i = 0 ; i<N;i++){
                for(int j = 0 ;j<M;j++){
                    if(disc[i][j] > 0 && disc[i][j] < avg ){
                        disc[i][j] += 1;
                    } else if (disc[i][j] > 0 && disc[i][j] > avg) {
                        disc[i][j] -= 1;
                    }
                }
            }
        }

    }
    static void move(int row, int dir){
        int[] copy = new int[M];

        if(dir == 0) { //시계 방향

            copy[0] = disc[row][M - 1];

            for(int i = 1; i < M; i++)
                copy[i] = disc[row][i - 1];

            disc[row] = copy.clone();
        }
        else { //반시계 방향
            copy[M-1] = disc[row][0];

            for(int i = 0; i < M-1; i++)
                copy[i] = disc[row][i+1];

            disc[row] = copy.clone();
        }

    }
    private static boolean isadj_equal() {
        //인접한 것 탐색
        int[][] copy = new int[N][M];

        for (int i = 0; i < N; i++)
            copy[i] = disc[i].clone();


        for (int i = 1; i < N - 1; i++) {
            for (int j = 1; j < M - 1; j++) {
                int num = disc[i][j];

                if (num == disc[i][j + 1]) {
                    copy[i][j] = 0;
                    copy[i][j + 1] = 0;
                }
                if (num == disc[i][j - 1]) {
                    copy[i][j - 1] = 0;
                    copy[i][j] = 0;
                }
                if (num == disc[i - 1][j]) {
                    copy[i][j] = 0;
                    copy[i - 1][j] = 0;
                }
                if (num == disc[i + 1][j]) {
                    copy[i][j] = 0;
                    copy[i + 1][j] = 0;
                }
            }
        }

        for (int i = 0; i < N; i++) {
            int num = disc[i][0];
            if (num == disc[i][1]) {
                copy[i][0] = 0;
                copy[i][1] = 0;
            }
            if (num == disc[i][M - 1]) {
                copy[i][0] = 0;
                copy[i][M - 1] = 0;
            }
            if(i != N-1 && num == disc[i+1][0]){
                copy[i][0] = 0;
                copy[i+1][0] = 0;
            }
        }
        for (int i = 0; i < N; i++) {
            int num = disc[i][M-1];
            if (num == disc[i][0]) {
                copy[i][M-1] = 0;
                copy[i][0] = 0;
            }
            if (num == disc[i][M - 2]) {
                copy[i][M-2] = 0;
                copy[i][M-1] = 0;
            }
            if(i != N-1 && num == disc[i+1][M-1]){
                copy[i][M-1] = 0;
                copy[i+1][M-1] = 0;
            }
        }

        for (int j = 0; j < M; j++) {
            int num = disc[0][j];
            if (num == disc[1][j]) {
                copy[0][j] = 0;
                copy[1][j] = 0;
            }
            if(j != M-1 && num == disc[0][j+1]){
                copy[0][j+1] = 0;
                copy[0][j] = 0;
            }
        }
        for (int j = 0; j < M; j++) {
            int num = disc[N-1][j];
            if (num == disc[N-2][j]) {
                copy[N-2][j] = 0;
                copy[N-1][j] = 0;
            }

            if(j != M-1 && num == disc[N-1][j+1]){
                copy[N-1][j+1] = 0;
                copy[N-1][j] = 0;
            }
        }
        boolean flag = false;

//        for(int i = 0 ; i <N;i++)
//            System.out.println(Arrays.toString(copy[i]));
//        System.out.println("얘는 카피본");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (disc[i][j] != copy[i][j]) flag = true;

                disc[i][j] = copy[i][j];
            }
        }

        if (flag)//하나라도 다른게 있으면
            return true;

        return false;
    }

}