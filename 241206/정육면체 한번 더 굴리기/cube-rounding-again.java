import java.util.*;

public class Main {

    static int[] dr = {0,0,1,-1}; //동 서 남 북
    static int[] dc = {1,-1,0,0};

    public static void main(String[] args) {
        // 여기에 코드를 작성해주세요.
    Scanner sc =new Scanner(System.in);

    int n = sc.nextInt(); //격자 크기
    int m = sc.nextInt(); //굴리는 횟수

    int[][] grid = new int[n][n];

    for(int i = 0 ; i < n; i++){
        for(int j = 0 ; j< n ; j++){
            grid[i][j] = sc.nextInt();
        }
    }

    int[][] score = updateScore(grid, n);
    Dice dice = new Dice(0,0,1,6,4,3,5,2);
    int dir = 0; //동쪽
    int answer = 0;

    for(int i = 0 ; i< m; i++){
        int nr = dice.r + dr[dir];
        int nc = dice.c + dc[dir];

        if(nr < 0 || nr >= n || nc < 0 || nc >= n) {
            
            dir = changeDir(dir);
            dice.r += dr[dir];
            dice.c += dc[dir]; 

            // answer += score[dice.r][dice.c];
            continue;
        }

        answer += score[nr][nc];
        moveDice(dice, dir);

        if(dice.bottom > grid[nr][nc]){
            //시계 방향 회전
            dir = changeClockDir(dir);
        } else if (dice.bottom < grid[nr][nc]){
            //반시계 방향 회전
            dir = changeReverseClockDir(dir);
        }

    }
    System.out.println(answer);
    }

    static int changeClockDir(int dir){
        if(dir == 0){
            return 2;
        } else if(dir==1){
            return 3;
        } else if(dir==2){
            return 1;
        } else return 0;
    }
    static int changeReverseClockDir(int dir){
        if(dir == 0){
            return 3;
        } else if(dir==1){
            return 2;
        } else if(dir==2){
            return 0;
        } else return 1;
    }

    static void moveDice(Dice dice, int dir){
        if(dir == 0){
            dice.moveRight();
        }else if(dir ==1){
            dice.moveLeft();
        }else if(dir ==2){
            dice.moveDown();
        } else{
            dice.moveUp();
        } 
    }

    static int changeDir(int dir){
        if(dir == 0){ //동일때
            return 1;
        } else if (dir == 1){
           return 0;
        } else if (dir == 2 ){
            return 3;
        }
        return 2;
    
    }

    static int[][] updateScore(int[][] grid, int n ){
        boolean[][] visited = new boolean[n][n];
        int[][] score =new int[n][n];

        for(int i= 0 ; i < n ; i++){
            for(int j = 0; j< n ; j++){
                
                if(visited[i][j]) continue;
                int cnt = 1;
                List<int[]> list =new ArrayList<>();

                Queue<Node> q = new LinkedList<>();
                q.add(new Node(i,j, grid[i][j]));
                list.add(new int[] {i,j});
                visited[i][j] = true;

                while(!q.isEmpty()){
                    
                    Node cur = q.poll();

                    for(int dir = 0 ; dir < 4 ; dir++){
                        int nr = cur.r + dr[dir];
                        int nc = cur.c + dc[dir];

                        if(nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
                        if(visited[nr][nc]) continue;

                        if(cur.value == grid[nr][nc]){
                            q.add(new Node(nr,nc, cur.value));
                            visited[nr][nc] = true;
                            list.add(new int[]{nr,nc});
                        }
                    }
                }

                for(int k= 0 ; k < list.size();k++){
                    score[list.get(k)[0]][list.get(k)[1]] = list.size() * grid[i][j];
                }
            }
        }
        // for(int i = 0 ; i< score.length; i++){
        //     System.out.println(Arrays.toString(score[i]));
        // }

        return score;
    }
}

class Node{
    int r;
    int c;
    int value;
    Node(int r, int c, int value){
        this.r = r;
        this.c = c;
        this.value = value;
    }

}

class Dice{
    int r;
    int c;
    int top;
    int bottom;
    int left;
    int up;
    int down;
    int right;
    
    Dice(int r, int c, int top, int bottom, int left, int right, int up, int down){
        this.r =r;
        this.c =c;
        this.top =top;
        this.bottom =bottom;
        this.left =left;
        this.right =right;
        this.up = up;
        this.down = down;
    }

    public void moveRight(){
        this.c = this.c+1;
        int tmp = this.top;
        this.top = this.left;
        this.left = this.bottom;
        this.bottom = this.right;
        this.right = tmp;
    }

    public void moveLeft(){
        this.c = this.c -1;
        int tmp = this.bottom;
        this.bottom = this.left;
        this.left = this.top;
        this.top = this.right;
        this.right = tmp;
    }

    public void moveUp(){
        int tmp = this.top;
        this.top = this.down;
        this.down = this.bottom;
        this.bottom = this.up;
        this.up = tmp;
        this.r = this.r-1;
    }
    public void moveDown(){
        int tmp = this.top;
        this.top = this.up;
        this.up = this.bottom;
        this.bottom = this.down;
        this.down = tmp;
        this.r = this.r+1;
    }
    

}