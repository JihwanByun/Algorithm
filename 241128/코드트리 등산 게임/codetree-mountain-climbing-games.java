import java.io.*;
import java.util.*;

public class Main {
    static List<List<Integer>> mount = new ArrayList<>();
    static List<Integer> index = new ArrayList<>();

    public static int search(int h) {
        int s = 0, e = mount.size() - 1;
        int idx = mount.size();
        
        while (s <= e) {
            int m = (s + e) / 2;
            if (h <= mount.get(m).get(mount.get(m).size() - 1)) {
                idx = m;
                e = m - 1;
            } else {
                s = m + 1;
            }
        }
        return idx;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int q = Integer.parseInt(br.readLine());

        while (q-- > 0) {
            st = new StringTokenizer(br.readLine());
            int o = Integer.parseInt(st.nextToken());

            if (o == 100) {
                int n = Integer.parseInt(st.nextToken());
                for (int i = 0; i < n; i++) {
                    int h = Integer.parseInt(st.nextToken());
                    int idx = search(h);
                    index.add(idx);
                    if (idx == mount.size()) {
                        mount.add(new ArrayList<>());
                    }
                    mount.get(idx).add(h);
                }
            }

            if (o == 200) {
                int h = Integer.parseInt(st.nextToken());
                int idx = search(h);
                index.add(idx);
                if (idx == mount.size()) {
                    mount.add(new ArrayList<>());
                }
                mount.get(idx).add(h);
            }

            if (o == 300) {
                if (!index.isEmpty()) {
                    int idx = index.get(index.size() - 1);
                    index.remove(index.size() - 1);
                    if (mount.get(idx).size() > 0) {
                        mount.get(idx).remove(mount.get(idx).size() - 1);
                    }
                    if (mount.get(idx).isEmpty()) {
                        mount.remove(idx);
                    }
                }
            }

            if (o == 400) {
                int m_idx = Integer.parseInt(st.nextToken()) - 1;
                if (m_idx >= 0 && m_idx < index.size()) {
                    int result = (index.get(m_idx) + mount.size()) * 1000000 + mount.get(mount.size() - 1).get(0);
                    bw.write(result + "\n");
                } else {
                    bw.write("Invalid index\n");
                }
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }
}