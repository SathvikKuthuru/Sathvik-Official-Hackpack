import java.util.*;

public class FloydWarshall {
    static int n, m;
    static long INF = Long.MAX_VALUE/100;
    static long[][] dist;
    
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        m = scan.nextInt();
        long[][] dist = new long[n][n];
        for(int i = 0; i < n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }
        for(int i = 0; i < m; i++) {
            int a = scan.nextInt()-1, b = scan.nextInt()-1;
            long w = scan.nextLong();
            //If graph is bidirectional
            dist[a][b] = dist[b][a] = Math.min(dist[a][b], w);
        }
        for(int k = 0; k < n; k++) {
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
}
