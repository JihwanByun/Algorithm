import java.util.*;
import java.io.*;

public class Main {
    static int n, m;
    static int[][] board;
    static int score;
    static boolean[][] visited;
    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};
    public static class Group{
        int cnt; //전체 블록 개수
        int rainbowCnt; //무지개색 블록 개수
        int r, c; //기준 블록 행, 열
        List<int[]> blocks = new ArrayList<>();

        Group(int cnt, int rainbowCnt, int r, int c, List<int[]> blocks){
            this.cnt = cnt;
            this.rainbowCnt = rainbowCnt;
            this.r = r;
            this.c = c;
            this.blocks = blocks;
        }
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board = new int[n][n];
        for(int i=0; i<n; i++){
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //-1 검정블럭, 0 무지개 블럭, 1~m 색상

        //인접해 있고, 검은 블록은 포함하지 않는 모든 같은 색 블록들 그룹에 추가 
        List<Group> list = new ArrayList<>();
        getBlockGroup(list);


        //블록 그룹이 존재하는동안 반복
        while(list.size()>0){
            //블록 그룹 정렬. 전체 블록 개수, 무지개 블록 개수, 기준 블록 행, 열 내림차순
            list.sort(new Comparator<Group>(){
                @Override
                public int compare(Group g1, Group g2){
                    if(g1.cnt==g2.cnt){
                        if(g1.rainbowCnt==g2.rainbowCnt){
                            if(g1.r==g2.r){
                                return Integer.compare(g2.c, g1.c);
                            }
                            else return Integer.compare(g2.r, g1.r);
                        }
                        else return Integer.compare(g2.rainbowCnt, g1.rainbowCnt);
                    }
                    else return Integer.compare(g2.cnt, g1.cnt);
                }
            });

            //블록그룹의 모든 블록 제거, b^2 점수 획득
            score += list.get(0).cnt*list.get(0).cnt;
            for(int[] b : list.get(0).blocks){
                board[b[0]][b[1]] = -2; //블록 제거 의미 
            }

            //격자에 중력 작용
            gravity();

            //격자 90도 반시계방향 회전
            rotate();

            //격자에 중력 작용
            gravity();

            //블록 그룹 구하기
            list = new ArrayList<>();
            getBlockGroup(list);
        }

        System.out.println(score);

    }

    public static void rotate(){
        int[][] temp = new int[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                temp[i][j] = board[j][n-1-i];
            }
        }

        board = temp;
    }

    public static void gravity(){
        //행이 큰 방향으로 이동
        for(int i=n-2; i>=0; i--){
            for(int j=0; j<n; j++){
                if(board[i][j]==-1 || board[i][j]==-2) continue; //검은 블록은 이동 안함, 블록 없는 경우

                //범위 벗어나기 전, 다른 블록 만나기 전까지 이동
                int idx = i+1;
                while(idx<n && board[idx][j]==-2){
                    idx++;
                }

                if(idx-1!=i){ //블록이 이동 한 경우
                    board[idx-1][j] = board[i][j];
                    board[i][j] = -2; //블록 이동해서 제거됨.
                }
            }
        }

    }

    public static void getBlockGroup(List<Group> list){
        visited = new boolean[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                if(visited[i][j] || board[i][j]==-1 || board[i][j]==0 || board[i][j]==-2) continue;
                bfs(i, j, list);
            }
        }
    }

    public static void bfs(int r, int c, List<Group> list){
        Queue<int[]> q = new LinkedList<>();
        List<int[]> blocks = new ArrayList<>();
        List<int[]> rainbowBlocks = new ArrayList<>();

        q.add(new int[]{r, c});
        visited[r][c] = true;

        int rainbowCnt = 0; //무지개 블록
        int generalCnt = 1; //일반블록

        int color = board[r][c];

        blocks.add(new int[]{r, c});

        while(!q.isEmpty()){
            int[] p = q.poll();

            for(int i=0; i<4; i++){
                int nr = p[0] + dr[i];
                int nc = p[1] + dc[i];

                //범위 밖이거나 블록이 없거나 검은 블록
                if(nr<0 || nc<0 || nr>=n || nc>=n || board[nr][nc]==-1 || visited[nr][nc] || board[nr][nc]==-2) continue;

                //무지개색이 아닌데 다른 색이면 패스
                if(board[nr][nc]!=0 && board[nr][nc]!=color) continue;

                visited[nr][nc] = true;
                if(board[nr][nc]==0) {
                    rainbowBlocks.add(new int[]{nr, nc});
                    rainbowCnt++;
                }
                else generalCnt++;

                blocks.add(new int[]{nr, nc});
                q.add(new int[]{nr, nc});
            }
        }

        if(blocks.size()<2) {
            //방문 표시 해제
            for(int[] b:blocks){
                visited[b[0]][b[1]] = false;
            }
            return;
        }

        //무지개 블록 방문 원상복구
        for(int[] b:rainbowBlocks){
            visited[b[0]][b[1]] = false;
        }


        //기준 블록 찾기. 일반블록 중 행번호 오름차순, 열번호 오름차순.
        blocks.sort(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if(o1[0]==o2[0]){
                    return Integer.compare(o1[1], o2[1]);
                }
                else return Integer.compare(o1[0], o2[0]);
            }
        });

        int standardR = 0, standardC = 0;
        for(int[] b:blocks){
            if(board[b[0]][b[1]]==0) continue;
            standardR = b[0];
            standardC = b[1];
            break;
        }

        list.add(new Group(rainbowCnt+generalCnt, rainbowCnt, standardR, standardC, blocks));

    }

}