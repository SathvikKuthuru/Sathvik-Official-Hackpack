import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class MatrixMath {

    public static void main(String[] args) {
        FastReader scan = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        Task solver = new Task();
        int t = 1;
        while (t --> 0) solver.solve(1, scan, out);
        out.close();
    }

    static class Task {
        /*
        Problem: Find number of ways to get sum N using dice rolls (use only numbers 1 - 6).
         */

        public void solve(int testNumber, FastReader scan, PrintWriter out) {
            long x = scan.nextLong(), MOD = (long) (1e9+7);
            long[] dp = new long[7], init = new long[6];
            long[][] M = new long[6][6];
            dp[0] = 1;
            for(int i = 0; i <= 6; i++) {
                for(int j = 1; j <= 6-i; j++) {
                    dp[i+j] += dp[i];
                }
            }
            if(x <= 6) {
                out.println(dp[(int) x]);
                return;
            }
            for(int i = 0; i < 6; i++) init[i] = dp[6-i];
            Arrays.fill(M[0], 1);
            for(int i = 1; i < 6; i++) M[i][i-1] = 1;
            long[] res = Matrix.mult(Matrix.pow(M, x-6, MOD), init, MOD);
            out.println(res[0]);
        }

        static class Matrix {
            static long[][] mult(long[][] a, long[][] b, long MOD) {
                if(a[0].length != b.length) return null;
                long[][] res = new long[a.length][b[0].length];
                for(int i = 0; i < res.length; i++) {
                    for(int j = 0; j < res[0].length; j++) {
                        for(int k = 0; k < b.length; k++) {
                            res[i][j] = add(res[i][j], mult(a[i][k], b[k][j], MOD), MOD);
                        }
                    }
                }
                return res;
            }

            static long[] mult(long[][] a, long[] b, long MOD) {
                if(a[0].length != b.length) return null;
                long[] res = new long[a.length];
                for(int i = 0; i < res.length; i++) {
                    for(int j = 0; j < b.length; j++) {
                        res[i] = add(res[i], mult(a[i][j], b[j], MOD), MOD);
                    }
                }
                return res;
            }

            static long[][] pow(long[][] a, long b, long MOD) {
                if(b == 1) return a;
                long[][] total = pow(a, b >> 1, MOD);
                total = mult(total, total, MOD);
                if((b & 1) == 1) total = mult(total, a, MOD);
                return total;
            }

            static long add(long a, long b, long MOD) {
                return (a%MOD+b%MOD)%MOD;
            }

            static long mult(long a, long b, long MOD) {
                return ((a%MOD)*(b%MOD))%MOD;
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
