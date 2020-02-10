import java.util.*;

public class SCC {
    static ArrayList<Integer>[] adj;
    static ArrayList<Integer>[] reverse;
    static boolean[] visited;
    static ArrayDeque<Integer> stack = new ArrayDeque<>();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt(), e = scan.nextInt(), ans = 0;
        adj = new ArrayList[n];
        reverse = new ArrayList[n];
        visited = new boolean[n];
        for(int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
            reverse[i] = new ArrayList<>();
        }
        for(int i = 0; i < e; i++) {
            int a = scan.nextInt(), b = scan.nextInt();
            adj[a].add(b);
            reverse[b].add(a);
        }
        for(int i = 0; i < n; i++) {
            if(!visited[i]) {
                fill(i);
            }
        }
        Arrays.fill(visited, false);
        while(!stack.isEmpty()) {
            int p = stack.pollLast();
            if(!visited[p]) {
                ans++;
                dfs(p);
            }
        }
        System.out.println(ans);
    }
    static void fill(int v) {
        visited[v] = true;
        for(int i : adj[v]) if(!visited[i]) fill(i);
        stack.addLast(v);
    }

    static void dfs(int v) {
        visited[v] = true;
        for(int i : reverse[v]) if(!visited[i]) dfs(i);
    }
}
