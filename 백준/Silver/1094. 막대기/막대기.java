import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    static int X,answer;
    static ArrayList<Integer> arrayList;
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        X = sc.nextInt();
        arrayList = new ArrayList<>();
        int sum = 64;
        arrayList.add(64);

        while(X < sum ) {

            int min = arrayList.get(0) / 2;

            if (sum - min >= X) {
                arrayList.remove(0);
                arrayList.add(min);
            } else {
                arrayList.remove(0);
                arrayList.add(min);
                arrayList.add(min);
            }
            sum = 0;
            for (int i = 0; i < arrayList.size(); i++) {
                sum += arrayList.get(i);
            }
            arrayList.sort((o1, o2) -> o1.compareTo(o2));
        }

        arrayList.sort((o1,o2)-> o2.compareTo(o1));
        boolean[] visited = new boolean[arrayList.size()];
        dfs(visited,0,0,0);
        System.out.println(answer);

    }

    public static void dfs(boolean[] visited,int idx ,int sum,int cnt){
        if(sum == X){
            answer = cnt;

            return;
        }

        if(idx >= arrayList.size() || sum > X){
            return;
        }

        visited[idx] = true;
        dfs(visited,idx+1, sum + arrayList.get(idx), cnt+1);
        visited[idx] = false;
        dfs(visited,idx+1, sum , cnt);

    }


}