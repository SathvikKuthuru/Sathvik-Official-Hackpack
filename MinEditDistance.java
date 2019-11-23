import java.util.*;

public class MinEditDistance {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        int m = scan.nextInt(), n = scan.nextInt();
        String a = scan.next(), b = scan.next();
        int[][] dp = new int[m+1][n+1];
        for(int i = 0; i <= m; i++) {
            for(int j = 0; j <= n; j++) {
                if(i == 0) dp[i][j] = j;
                else if(j == 0) dp[i][j] = i;
                else if(a.charAt(i-1) == b.charAt(j-1)) dp[i][j] = dp[i-1][j-1];
                else dp[i][j] = 1 + Math.min(dp[i-1][j], Math.min(dp[i][j-1], dp[i-1][j-1]));
            }
        }
        System.out.println(dp[m][n]);
    }
}
