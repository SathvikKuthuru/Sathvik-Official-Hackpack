import java.util.*;

public class MatrixTest {
        /*
        Problem: Find number of ways to get sum N using dice rolls (use only numbers 1 - 6).
         */
         
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
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
            System.out.println(dp[(int) x]);
            return;
        }
        for(int i = 0; i < 6; i++) init[i] = dp[6-i];
        Arrays.fill(M[0], 1);
        for(int i = 1; i < 6; i++) M[i][i-1] = 1;
        long[] res = Matrix.mult(Matrix.pow(M, x-6, MOD), init, MOD);
        System.out.println(res[0]);
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
