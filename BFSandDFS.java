import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class BFSandDFS {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt(), m = scan.nextInt();
        ArrayList<Integer>[] adj = new ArrayList[n];
        boolean[] visited = new boolean[n];
        for(int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for(int i = 0; i < m; i++) {
            int a = scan.nextInt()-1, b = scan.nextInt()-1;
            adj[a].add(b);
            adj[b].add(a);
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        //starting at vertex 0
        queue.add(0);
        while(!queue.isEmpty()) {
            int p = queue.pollFirst(); //do queue.pollLast() for DFS
            for(int i : adj[p]) {
                if(!visited[i]) {
                    visited[i] = true;
                    queue.addLast(i);
                }
            }
        }
        for(int i = 0; i < n; i++) {
            if(visited[i]) System.out.println("Vertex " + i  + " visited");
            else System.out.println("Vertex " + i  + " not visited");
        }
        visited = new boolean[n];
        //Starting at vertex 0
        dfs(0, adj, visited);
        for(int i = 0; i < n; i++) {
            if(visited[i]) System.out.println("Vertex " + i  + " visited");
            else System.out.println("Vertex " + i  + " not visited");
        }
    }

    static void dfs(int currentVertex, ArrayList<Integer>[] adj, boolean[] visited) {
        visited[currentVertex] = true;
        for(int i : adj[currentVertex]) {
            if(!visited[i]) dfs(i, adj, visited);
        }
    }
}
