import java.io.*;
import java.util.*;

public class Main {
  static int N, M;
  static char[][] map;
  static int[] r, b;
  static boolean[][][][] visited;
  static int[][] D = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st = new StringTokenizer(br.readLine());

    N = Integer.parseInt(st.nextToken());
    M = Integer.parseInt(st.nextToken());

    map = new char[N][M];
    visited = new boolean[N][M][N][M];

    r = new int[2];
    b = new int[2];

    for (int i = 0; i < N; i++) {
      String str = br.readLine();
      for (int j = 0; j < M; j++) {
        char c = str.charAt(j);
          if (c == 'R') {
            r[0] = i;
            r[1] = j;
          } else if (c == 'B') {
            b[0] = i;
            b[1] = j;
          }
          map[i][j] = c;
      }
    }
    
    System.out.println(bfs());
  }

  static int bfs() {
    Queue<int[]> queue = new LinkedList<int[]>();
    queue.offer(new int[] { r[0], r[1], b[0], b[1] });

    int cnt = 0;

    while (!queue.isEmpty() && cnt < 10) {
      int len = queue.size();

      for (int i = 0; i < len; i++) {
        int[] now = queue.poll();
        for (int d = 0; d < 4; d++) {
          int[] cur = now.clone();
          if(move(cur, d)) {
            if (map[cur[0]][cur[1]] == 'O') return 1;
            if(visited[cur[0]][cur[1]][cur[2]][cur[3]]) continue;
            visited[cur[0]][cur[1]][cur[2]][cur[3]] = true;
            
            queue.add(new int [] {cur[0], cur[1], cur[2], cur[3]});
          }
        }
      }
      cnt++;
    }
    return 0;
  }

  static boolean move(int [] cur, int d) {
    boolean redFirst = false;
    
    if (d == 0 && cur[0] < cur[2]) redFirst = true;
    if (d == 1 && cur[0] > cur[2]) redFirst = true;
    if (d == 2 && cur[1] < cur[3]) redFirst = true;
    if (d == 3 && cur[1] > cur[3]) redFirst = true;
    
    int nx = cur[0];
    int ny = cur[1];
    
    while (true) {
      nx += D[d][0];
      ny += D[d][1];
      if (map[nx][ny] == '#') break;
      cur[0] = nx;
      cur[1] = ny;
      if (map[nx][ny] == 'O') break;
    }
    
    nx = cur[2];
    ny = cur[3];
    
    while(true) {
      nx += D[d][0];
      ny += D[d][1];
      
      if (map[nx][ny] == '#') break;
      cur[2] = nx;
      cur[3] = ny;
      if (map[nx][ny] == 'O') break;
    }
    
    if (map[cur[2]][cur[3]] == 'O') return false;
    
    if (cur[0] == cur[2] && cur[1] == cur[3]) {
      switch(d) {
      case 0:
        if (redFirst) cur[2]++;
        else cur[0]++;
        break;
      case 1:
        if (redFirst) cur[2]--;
        else cur[0]--;
        break;
      case 2:
        if (redFirst) cur[3]++;
        else cur[1]++;
        break;
      case 3:
        if (redFirst) cur[3]--;
        else cur[1]--;
        break;
      }
    }
    return true;
  }
}