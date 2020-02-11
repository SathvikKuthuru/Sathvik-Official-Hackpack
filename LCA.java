import java.util.*;

public class LCA {
    static int n;
    static ArrayList<Integer>[] adj;
    static int[][] parent;
    static int[] depth;
    static int LOG = 21;
    
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        adj = new ArrayList[n];
        depth = new int[n];
        parent = new int[n][LOG];
        
        for(int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for(int i = 0; i < n-1; i++) {
            int a = scan.nextInt()-1, b = scan.nextInt()-1;
            adj[a].add(b);
            adj[b].add(a);
        }
        dfs(-1, 0, 0);
        precompute();
    }
    
    static void dfs(int prev, int at, int curr) {
        parent[at][0] = prev;
        depth[at] = curr;
        for(int i : adj[at]) {
            if(i != prev) dfs(at, i, curr+1);
        }
    }
    
    static void precompute() {
        for(int i = 1; i < LOG; i++) {
            for(int j = 0; j < n; j++) {
                if(parent[j][i-1] != -1) {
                    parent[j][i] = parent[parent[j][i-1]][i-1];
                }
                else parent[j][i] = -1;
            }
        }
    }
    
    static int lca(int a, int b) {
        if(depth[b] < depth[a]) {
            int temp = b;
            b = a;
            a = temp;
        }
        int diff = depth[b] - depth[a];
        for(int i = 0; i < LOG; i++) {
            if((diff & (1 << i)) > 0) {
                b = parent[b][i];
            } 
        }
        if(a == b) return a;
        for(int i = LOG - 1; i >= 0; i--) {
            if(parent[a][i] != parent[b][i]) {
                a = parent[a][i];
                b = parent[b][i];
            }
        }
        return parent[a][0];
    }
}
