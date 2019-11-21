import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
 
public class BITProblem {
 
    public static void main(String[] args) {
        FastReader scan = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        Task solver = new Task();
        int t = 1;
        while (t --> 0) solver.solve(1, scan, out);
        out.close();
    }
 
    static class Task {
 
        public void solve(int testNumber, FastReader scan, PrintWriter out) {
            int n = scan.nextInt();
            int[] x = new int[n];
            for(int i = 0; i < n; i++) x[i] = scan.nextInt();
            BIT bit = new BIT(n);
            for(int i = 0; i < n; i++) {
                int curr = scan.nextInt();
                int low = 0, high = n-1, ans = -1;
                while(low <= high) {
                    int mid = (low+high)>>1;
                    if(bit.getSum(mid) >= curr) {
                        ans = mid;
                        high = mid-1;
                    }
                    else low = mid+1;
                }
                out.print(x[ans] + " ");
                bit.update(ans, -1);
            }
        }
 
        static class BIT {
            int[] update;
 
            public BIT(int n) {
                update = new int[n+1];
                for(int i = 0; i < n; i++) update(i, 1);
            }
 
            int getSum(int index) {
                int sum = 0;
                index++;
                while(index > 0) {
                    sum += update[index];
                    index -= index & (-index);
                }
                return sum;
            }
 
            int getSum(int a, int b) {
                return getSum(b) - getSum(a-1);
            }
 
            void update(int index, int val) {
                index++;
                while(index < update.length) {
                    update[index] += val;
                    index += index & (-index);
                }
            }
        }
 
    }
 
    static void shuffle(int[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
 
    static void shuffle(long[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            long temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
 
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;
 
        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }
 
        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(new File(s)));
        }
 
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int nextInt() {
            return Integer.parseInt(next());
        }
 
        long nextLong() {
            return Long.parseLong(next());
        }
 
        double nextDouble() {
            return Double.parseDouble(next());
        }
 
        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}
