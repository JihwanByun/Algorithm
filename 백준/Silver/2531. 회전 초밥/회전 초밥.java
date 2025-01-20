import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] inputs = br.readLine().split(" ");

        int N = Integer.parseInt(inputs[0]); // 접시 수
        int d = Integer.parseInt(inputs[1]); // 초밥 종류 수
        int k = Integer.parseInt(inputs[2]); // 연속해서 먹는 접시 수
        int c = Integer.parseInt(inputs[3]); // 쿠폰 초밥 번호

        int[] dish = new int[N];
        for (int i = 0; i < N; i++) {
            dish[i] = Integer.parseInt(br.readLine());
        }

        // 초밥 종류별 개수와 초기 슬라이딩 윈도우 설정
        int[] sushiType = new int[d + 1];
        int typeCount = 0, maxCount = 0;

        // 초기 윈도우 설정
        for (int i = 0; i < k; i++) {
            if (sushiType[dish[i]]++ == 0) {
                typeCount++;
            }
        }

        // 초기 윈도우에 쿠폰 초밥 포함 여부 체크
        maxCount = sushiType[c] == 0 ? typeCount + 1 : typeCount;

        // 슬라이딩 윈도우로 최대 초밥 종류 계산
        for (int i = 0; i < N; i++) {
            // 윈도우에서 제거되는 초밥 처리
            int out = dish[i];
            if (--sushiType[out] == 0) {
                typeCount--;
            }

            // 윈도우에 추가되는 초밥 처리
            int in = dish[(i + k) % N];
            if (sushiType[in]++ == 0) {
                typeCount++;
            }

            // 쿠폰 초밥 포함 여부를 고려한 최대값 갱신
            maxCount = Math.max(maxCount, sushiType[c] == 0 ? typeCount + 1 : typeCount);
        }

        System.out.println(maxCount);
    }
}