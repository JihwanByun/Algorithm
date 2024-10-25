import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class Snake{
    ArrayList<Point> arrayList = new ArrayList<>();

    public void init(){
        arrayList.add(new Point(0,0));

    }
}

class Point{
    int x;
    int y;
    public Point(int  x, int y){
        this.x = x;
        this.y = y;
    }
}
public class Main {
    static int[] dr = {0,1,0,-1};
    static int[] dc = {1,0,-1,0};

    public static void main(String[] args) {

        Snake snake =new Snake();
        snake.init();
        Scanner sc =new Scanner(System.in);

        int n = sc.nextInt();
        int appleCnt = sc.nextInt();
        boolean[][] map = new boolean[n][n];

        for(int i = 0 ; i<appleCnt; i++){
            map[sc.nextInt()-1][sc.nextInt()-1] = true;
        }
        int changeDirCnt = sc.nextInt();
        Map<Integer, Character> changeDirByTime = new HashMap<>();

        for(int i = 0; i < changeDirCnt; i++){
            changeDirByTime.put(sc.nextInt(), sc.next().charAt(0));
        }

        int posR = 0;
        int posC = 0;
        int time = 0;
        int dir = 0;
        while (true){
            time++;
            posR += dr[dir];
            posC += dc[dir];
//            System.out.println("r "+ posR + " " + "c "+ posC + " time " + time);
            if(posR < 0 || posR >= n || posC >= n || posC < 0){//벽넘으면 끝
                break;
            }
            snake.arrayList.add(0,new Point(posR,posC));
            if(checkDuplicate(snake.arrayList)) break;
            if(!map[posR][posC]){
                snake.arrayList.remove(snake.arrayList.size()-1);
            } else {
                map[posR][posC] = false;
            }
            if(changeDirByTime.containsKey(time)){
                if(changeDirByTime.get(time) == 'D'){ //왼쪽
                    dir = (dir + 1) % 4;
                } else dir = (dir - 1 + 4) % 4;
            }
        }

        System.out.println(time);
    }

    public static boolean checkDuplicate(ArrayList<Point> arrayList){
        int x = arrayList.get(0).x;
        int y = arrayList.get(0).y;

        for(int i = 1 ; i <= arrayList.size() -1 ;i++){
            if(arrayList.get(i).x == x && arrayList.get(i).y ==y) return true;
        }
        return false;
    }
}