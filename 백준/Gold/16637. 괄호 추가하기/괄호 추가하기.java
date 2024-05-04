import java.io.*;
import java.util.ArrayList;

/*
괄호 개수 제한 없고
추가도 안해도 된다
중첩된 괄호는 사용할 수 없다. 괄호 안에는 연산자 하나만 들어가야한다.

괄호의 개수를 하나씩 늘려가기, 맨 앞부터 시작
괄호를 하나로 대표할 수 있다면 숫자보단 연산자로 나누는 것이 합리적

핵심 로직,
자료를 분리하기 -> 숫자와 연산자를 같은 배열에 처리시킬 시
항상 index 변화를 살펴야함, 이를 나누어 생각할 경우 효과적인 처리 가능
 */

public class Main {
    static int ans;
    static ArrayList<Character> ops;
    static ArrayList<Integer> nums;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(br.readLine());
        String input = br.readLine();

        ops = new ArrayList<>();
        nums = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            char c = input.charAt(i);
            if (c == '+' || c == '-' || c == '*') {
                ops.add(c);
                continue;
            }
            nums.add(Character.getNumericValue(c));
        }

        ans = Integer.MIN_VALUE;
        DFS(nums.get(0), 0);

        bw.write(ans + "\n");
        bw.flush();
        bw.close();
        br.close();
    }

    // 연산
    public static int calc(char op, int n1, int n2) {
        switch (op) {
            case '+':
                return n1 + n2;
            case '-':
                return n1 - n2;
            case '*':
                return n1 * n2;
        }
        return -1;
    }

    // DFS, 백트래킹 활용.
    public static void DFS(int result, int opIdx) {
        // 주어진 연산자의 개수를 초과하였을 경우.
        if (opIdx >= ops.size()) {
            ans = Math.max(ans, result);
            return;
        }

        // 괄호가 없는 경우
        int res1 = calc(ops.get(opIdx), result, nums.get(opIdx + 1));
        DFS(res1, opIdx + 1);

        // 괄호가 있는 경우
        if (opIdx + 1 < ops.size()) {
            // result의 오른쪽에 있는 값을 연산함.
            int res2 = calc(ops.get(opIdx + 1), nums.get(opIdx + 1), nums.get(opIdx + 2));

            // 현재 result와 방금 구한 괄호 값을 연산한 결과와 괄호 오른쪽에 존재하는 연산자의 인덱스를 넘김.
            DFS(calc(ops.get(opIdx), result, res2), opIdx + 2);
        }
    }


}