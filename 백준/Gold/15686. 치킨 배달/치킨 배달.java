import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
치킨 거리 가장 가까운 치킨집 사이 거리
각각의 집에 따라 다름
도시의 치킨 거리 = 모든 집의 치킨 거리의 합

도시의 치킨거리가 가장 작은 경우?
최대 M개의 치킨 집 고르기, N X N 크기
0 빈칸, 1 집, 2 치킨집
적어도 한 개의 집 존재,  M<= 치킨집의 개수 <= 13

치킨집 하나씩 선정
M개 고르고 도시의 치킨거리 구하고 최소값과 비교 후 갱신

 */
public class Main {
    private static class chicken{
        int r;
        int c;
        chicken(int r, int c){
            this.r = r;
            this.c = c;
        }
    }
    private static class house{
        int r ;
        int c ;
        int distance;
        house(int r, int c, int distance){
            this.r = r;
            this.c = c;
            this.distance = distance;
        }
    }

    static int N,M;
    static int chicken_cnt,min;
    static int[][] town;
    static List<chicken> chickens;
    static List<house> houseList;
    static boolean[] visited;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt(); //도시 크기
        M = sc.nextInt(); // 최대 고를 수 있는 치킨 집 개수
        town = new int[N][N];
        min = Integer.MAX_VALUE;
        chickens = new ArrayList<>();
        houseList = new ArrayList<>();
        for(int i = 0 ; i<N;i++){
            for(int j = 0 ; j<N; j++){
                town[i][j] = sc.nextInt();
                if(town[i][j] == 2) chickens.add(new chicken(i,j));
                if(town[i][j] == 1) houseList.add(new house(i,j,Integer.MAX_VALUE));
            }
        }
        chicken_cnt  = chickens.size();
        visited = new boolean[chicken_cnt];

        select_chicken(0,0);

        System.out.println(min);
    }
    private static void select_chicken(int idx, int sidx){
        if(sidx >= M){ //치킨집 다 뽑았어, idx에 따라 true로 저장
            int distance = 0;

            for(int i=  0 ;i<chicken_cnt;i++){
                if(visited[i]){
                    calculate_distance(chickens.get(i));
                }
            }
            for(int i = 0 ; i< houseList.size(); i++)
                distance += houseList.get(i).distance;

            if(min > distance)
                min = distance;

            for(int i = 0 ; i <houseList.size();i++)
                houseList.get(i).distance = Integer.MAX_VALUE;

            return;
        }
        if(idx >= chicken_cnt) return; //조사 끝

        visited[idx] = true;
        select_chicken(idx+1,sidx+1);
        visited[idx] = false;
        select_chicken(idx+1,sidx);

    }
    private static void calculate_distance(chicken ch){
        for(int i = 0 ; i<houseList.size();i++){
            house h =  houseList.get(i);
            if(Math.abs(h.r - ch.r) + Math.abs(h.c - ch.c) < h.distance){
                h.distance = Math.abs(h.r - ch.r) + Math.abs(h.c - ch.c);
            }
        }
    }
}