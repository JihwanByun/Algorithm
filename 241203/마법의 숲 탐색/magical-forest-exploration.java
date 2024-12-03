import java.util.*;

public class Main {
    static int[] dr = {-1,0,1,0}; //북동남서
    static int[] dc = { 0,1,0,-1};

    static int[][] forest;
    static Golem[] golems;
    static int R,C;

    public static void main(String[] args) {

        Scanner sc =new Scanner(System.in);
        R = sc.nextInt();
        C = sc.nextInt();
        forest = new int[R+3][C];

        int k = sc.nextInt();
        int answer = 0;
        golems = new Golem[1020];

        for(int i = 0 ; i < k; i++){
            int startC = sc.nextInt();
            int exitDir = sc.nextInt();
            Golem golem = new Golem(1,startC-1, exitDir, i+1);
            golems[i+1] = golem;

            int sum = simulate(startC-1, i+1);
            if(sum == 0 ) continue;
//
//            for(int j = 0; j < forest.length; j++)
//                System.out.println(j+ "행은: " + Arrays.toString(forest[j]));
            //갈 수 있는 제일 아래인 칸 찾기
            
            answer += sum -2  ;
        }
        System.out.println(answer);
    }

    static int simulate(int startC, int golemNum){


        int answer = move(golemNum);
        if(golems[golemNum].block[0][0] <= 2) {
            reset();
            return 0;
        }
        return answer;
    }

    //방향 설정해서 이동하기
    static boolean moveDown(Golem golem){
        int empty = 0;

        for(int i = 1 ; i < 4; i++){
            if(golem.block[i][0] + 1 >= R + 3 || golem.block[i][1] >= C ) continue;

            if(forest[golem.block[i][0]+1][golem.block[i][1]] == 0){
                empty++;
            }
        }
        if(empty == 3){

            for(int i = 0; i < 4 ; i++){
                golem.block[i][0] += 1;
            }

            golem.r += 1;
            return true;
        }

        return false;
    }

    static boolean moveLeft(Golem golem){

        int empty = 0;
        //왼쪽 이동 후 dir 바꾸고 아래쪽 이동
        for(int i = 0 ; i < 4 ; i++){
            if(i == 1) continue;

            if(golem.block[i][1] - 1 < 0) return false;
            if(forest[golem.block[i][0]][golem.block[i][1] - 1] == 0){
                empty++;
            }
        }
        if(empty == 3){
            for(int i = 0 ; i < 4 ;i++){
                golem.block[i][1] -= 1;
            }
            golem.c -= 1;
        }

        if(!moveDown(golem)) {
            if(empty == 3){
                for(int i = 0 ; i < 4 ;i++){
                    golem.block[i][1] += 1;
                }
                golem.c += 1;
            }
            return false;
        }
        //출구 반시계로 이동
        golem.exitDir = (4 + golem.exitDir-1) % 4;

        return true;
    }

    static boolean moveRight(Golem golem){

        //오른쪽 이동 후 아래쪽 이동
        int empty = 0;
        //왼쪽 이동 후 dir 바꾸고 아래쪽 이동
        for(int i = 0 ; i < 4 ; i++){

            if(i == 2) continue;
            if(golem.block[i][1] + 1 >= C) return false;
            if(forest[golem.block[i][0]][golem.block[i][1] + 1] == 0){
                empty++;
            }
        }
        if(empty == 3){
            for(int i = 0 ; i < 4 ;i++){
                golem.block[i][1] += 1;
            }
            golem.c += 1;
        }

        if(!moveDown(golem)) {
            if(empty == 3){
                for(int i = 0 ; i < 4 ;i++){
                    golem.block[i][1] -= 1;
                }
                golem.c -= 1;
            }
            return false;
        }
        //출구 반시계로 이동
        golem.exitDir = (4 + golem.exitDir+1) % 4;


        return true;
    }

    //이동하기
    static int move(int golemNum){

        Golem golem = golems[golemNum];
        //남쪽 탐색
        boolean canMove = true;

        while(canMove){
            //아래로 이동
            if(moveDown(golem)) continue;
            //서쪽 + 아래 탐색
            if(moveLeft(golem)) continue;
            //동쪽 + 아래 탐색
            if(moveRight(golem)) continue;
            //더 이상 이동하지 못한다면
            canMove = false;
        }

        //forest 업데이트하기
        update(golem);

        return findMaxR(golem, new boolean[R+3][C]);
    }

    static int[] exit (Golem golem){

        if(golem.exitDir == 0){
            golem.exit = golem.block[0];
        } else if(golem.exitDir == 1){
            golem.exit = golem.block[1];
        } else if(golem.exitDir == 2){
            golem.exit = golem.block[2];
        } else if(golem.exitDir == 3){
            golem.exit = golem.block[3];
        }
        return golem.exit;
    }

    public static int findMaxR(Golem golem, boolean[][] visited){

        int max = golem.block[2][0];
        int[] exit = exit(golem);

        for(int i = 0 ; i< 4 ;i++){
            int movedR = golem.exit[0] + dr[i];
            int movedC = golem.exit[1] + dc[i];

            if(movedR >= R + 3 || movedR < 0 || movedC >= C || movedC < 0
                    || forest[movedR][movedC] == 0 || forest[movedR][movedC] == golem.num) continue;
            if(visited[movedR][movedC]) continue;
            int golemContactNum = forest[movedR][movedC];

            visited[movedR][movedC] = true;
            max = Math.max(max, findMaxR(golems[golemContactNum],visited));
            // if (golems[golemContactNum].exit[0] == movedR && golems[golemContactNum].exit[1] == movedC)
        }

        return max;
    }


    static void update(Golem golem){
        //이동이 끝났을 때만 위치 고정
        forest[golem.r][golem.c] = golem.num;

        for(int i = 0 ; i < 4; i++){
            if(golem.block[i][0] >=  R + 3 || golem.block[i][1] >= C || golem.block[i][0] < 0 || golem.block[i][1] < 0 ) continue;

            forest[golem.block[i][0]][golem.block[i][1]] = golem.num;
        }
    }

    static void reset(){
        forest = new int[R+3][C];
    }

}


class Golem {

    int[] exit = new int[2];
    int num;
    int exitDir;
    int r;
    int c;
    int[][] block = new int[4][2];
    Golem(int r,int c, int exitDir, int num){
        for(int i = 0 ; i < 4; i++){ //북동남서
            this.block[i][0] = r + Main.dr[i];
            this.block[i][1] = c + Main.dc[i];
        }
        this.num = num;

        if(exitDir == 0){
            this.exit = block[0];
        } else if(exitDir == 1){
            this.exit = block[1];
        } else if(exitDir == 2){
            this.exit = block[2];
        } else if(exitDir == 3){
            this.exit = block[3];
        }
        this.exitDir =exitDir;
        this.r = r;
        this.c = c;
    }
}