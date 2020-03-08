import java.util.Scanner;

public class BinomialDP {
    static int n, k, MOD;
    static int[][] choose;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        k = scan.nextInt();
        MOD = scan.nextInt();
        init();
        System.out.println(choose[scan.nextInt()][scan.nextInt()]);
    }

    static void init() {
        choose = new int[n + 1][k + 1];
        for(int i = 0; i <= n; i++) {
            for(int j = 0; j <= Math.min(i, k); j++) {
                if(j == 0 || j == i) choose[i][j] = 1;
                else choose[i][j] = (choose[i - 1][j - 1] + choose[i - 1][j]) % MOD;
            }
        }
    }
}
