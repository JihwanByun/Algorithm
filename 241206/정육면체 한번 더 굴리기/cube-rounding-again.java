import java.util.*;

public class Main {
    static int[] dr = {0,0,1,-1}; //동 서 남 북
    static int[] dc = {1,-1,0,0};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        int[][] grid = new int[n][n];

        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                grid[i][j] = sc.nextInt();
            }
        }

        Dice dice = new Dice(0,0,1,6,4,3,5,2);
        int dir = 0; //동쪽
        int answer = 0;

        for(int i = 0; i < m; i++) {
            int nr = dice.r + dr[dir];
            int nc = dice.c + dc[dir];

            if(nr < 0 || nr >= n || nc < 0 || nc >= n) {
                dir = changeDir(dir);
                nr = dice.r + dr[dir];
                nc = dice.c + dc[dir];
            }

            dice.r = nr;
            dice.c = nc;
            moveDice(dice, dir);
            answer += getScore(grid, nr, nc, n);

            if(dice.bottom > grid[nr][nc]) {
                dir = changeClockDir(dir);
            } else if (dice.bottom < grid[nr][nc]) {
                dir = changeReverseClockDir(dir);
            }
        }
        System.out.println(answer);
    }

    static int getScore(int[][] grid, int r, int c, int n) {
        boolean[][] visited = new boolean[n][n];
        int value = grid[r][c];
        int count = 1;
        
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{r, c});
        visited[r][c] = true;
        
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            
            for(int dir = 0; dir < 4; dir++) {
                int nr = cur[0] + dr[dir];
                int nc = cur[1] + dc[dir];
                
                if(nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
                if(visited[nr][nc]) continue;
                
                if(grid[nr][nc] == value) {
                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc});
                    count++;
                }
            }
        }
        
        return count * value;
    }

    static int changeClockDir(int dir) {
        if(dir == 0) return 2;
        else if(dir == 1) return 3;
        else if(dir == 2) return 1;
        else return 0;
    }

    static int changeReverseClockDir(int dir) {
        if(dir == 0) return 3;
        else if(dir == 1) return 2;
        else if(dir == 2) return 0;
        else return 1;
    }

    static void moveDice(Dice dice, int dir) {
        if(dir == 0) dice.moveRight();
        else if(dir == 1) dice.moveLeft();
        else if(dir == 2) dice.moveDown();
        else dice.moveUp();
    }

    static int changeDir(int dir) {
        if(dir == 0) return 1;
        else if(dir == 1) return 0;
        else if(dir == 2) return 3;
        return 2;
    }
}

class Dice {
    int r;
    int c;
    int top;
    int bottom;
    int left;
    int up;
    int down;
    int right;
    
    Dice(int r, int c, int top, int bottom, int left, int right, int up, int down) {
        this.r = r;
        this.c = c;
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
    }

    public void moveRight() {
        int tmp = this.top;
        this.top = this.left;
        this.left = this.bottom;
        this.bottom = this.right;
        this.right = tmp;
    }

    public void moveLeft() {
        int tmp = this.bottom;
        this.bottom = this.left;
        this.left = this.top;
        this.top = this.right;
        this.right = tmp;
    }

    public void moveUp() {
        int tmp = this.top;
        this.top = this.down;
        this.down = this.bottom;
        this.bottom = this.up;
        this.up = tmp;
    }

    public void moveDown() {
        int tmp = this.top;
        this.top = this.up;
        this.up = this.bottom;
        this.bottom = this.down;
        this.down = tmp;
    }
}