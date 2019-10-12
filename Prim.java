import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Prim {
    static ArrayList<Edge>[] adj;
    static int n, m;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        m = scan.nextInt();
        adj = new ArrayList[n];
        for(int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for(int i = 0; i < m; i++) {
            int a = scan.nextInt()-1, b = scan.nextInt()-1, w = scan.nextInt();
            adj[a].add(new Edge(b, w));
            adj[b].add(new Edge(a, w));
        }
        int minCost = MST(0);
        System.out.println(minCost);
    }

    static int MST(int start) {
        boolean[] visited = new boolean[n];
        int cost = 0;
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        queue.addAll(adj[start]);
        visited[start] = true;
        while(!queue.isEmpty()) {
            Edge curr = queue.poll();
            if(visited[curr.to]) continue;
            visited[curr.to] = true;
            cost += curr.weight;
            for(Edge e : adj[curr.to]) {
                if(!visited[e.to]) queue.add(e);
            }
        }
        return cost;
    }

    static class Edge implements Comparable<Edge> {
        int to;
        int weight;

        public Edge(int a, int b) {
            to = a;
            weight = b;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(weight, o.weight);
        }
    }

}
