import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        // 빠른 입출력을 위한 BufferedReader와 BufferedWriter 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // 첫 번째 줄 입력: nA와 nB
        StringTokenizer st = new StringTokenizer(br.readLine());
        int nA = Integer.parseInt(st.nextToken());
        int nB = Integer.parseInt(st.nextToken());

        // 집합 A 입력 (배열에 저장)
        st = new StringTokenizer(br.readLine());
        int[] A = new int[nA];
        for (int i = 0; i < nA; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        // 집합 B 입력 (HashSet에 저장하여 빠른 포함 여부 확인)
        st = new StringTokenizer(br.readLine());
        HashSet<Integer> setB = new HashSet<>(nB);
        for (int i = 0; i < nB; i++) {
            setB.add(Integer.parseInt(st.nextToken()));
        }

        // A에는 속하지만 B에는 속하지 않는 원소를 저장할 리스트
        ArrayList<Integer> result = new ArrayList<>();
        for (int num : A) {
            if (!setB.contains(num)) {
                result.add(num);
            }
        }

        // 결과 원소를 오름차순으로 정렬
        Collections.sort(result);

        // 결과 출력
        if (result.isEmpty()) {
            bw.write("0");
        } else {
            bw.write(result.size() + "\n");
            for (int num : result) {
                bw.write(num + " ");
            }
        }
        
        // 출력 마무리
        bw.flush();
        bw.close();
        br.close();
    }
}