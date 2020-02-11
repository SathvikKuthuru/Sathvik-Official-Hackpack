import java.util.*;

public class BellmanFord {
    static int n, m;
    static long INF = Integer.MAX_VALUE;
    static Edge[] edges;
    
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        m = scan.nextInt();
        edges = new Edge[m];
        for(int i = 0; i < m; i++) edges[i] = new Edge(scan.nextInt()-1, scan.nextInt()-1, scan.nextLong());
        long[] dist = shortestPath(0);
        if(dist == null) System.out.println("Negative Weight Cycle Found");
        else {
            for(int i = 0; i < n; i++) System.out.println(dist[i]);
        }
    }
    
    static long[] shortestPath(int start) {
        long[] dist = new long[n];
        Arrays.fill(dist, INF);
        dist[start] = 0;
        //Relaxation
        for(int i = 0; i < n - 1; i++) {
            for(int j = 0; j < m; j++) {
                dist[edges[j].to] = Math.min(dist[edges[j].to], dist[edges[j].from] + edges[j].weight);
            }
        }
        //Check for negative cycle
        for(int i = 0; i < m; i++) {
            if(dist[edges[i].from] + edges[i].weight < dist[edges[i].to]) return null;
        }
        return dist;
    }
    
    static class Edge {
        int from, to;
        long weight;
        
        public Edge(int a, int b, long w) {
            from = a;
            to = b;
            weight = w;
        }
    }
}
