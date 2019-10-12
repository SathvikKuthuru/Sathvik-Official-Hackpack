import java.util.*;

public class Dijkstra {
    static ArrayList<Edge>[] adj;
    static long[] dist;
    static int[] prev;
    static int n, m;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        m = scan.nextInt();
        adj = new ArrayList[n];
        dist = new long[n];
        prev = new int[n];
        for(int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for(int i = 0; i < m; i++) {
            int a = scan.nextInt()-1, b = scan.nextInt()-1;
            long w = scan.nextLong();
            adj[a].add(new Edge(b, w));
            adj[b].add(new Edge(a, w));
        }
        shortestPath(0);
        printPath(n-1);
    }

    static void shortestPath(int start) {
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        boolean[] visited = new boolean[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[start] = 0;
        prev[start] = -1;
        queue.add(new Edge(start, 0));
        while(!queue.isEmpty()) {
            Edge curr = queue.poll();
            if(visited[curr.to]) continue;
            visited[curr.to] = true;
            for(Edge e : adj[curr.to]) {
                if(visited[e.to]) continue;
                if(curr.weight + e.weight < dist[e.to]) {
                    dist[e.to] = curr.weight + e.weight;
                    prev[e.to] = curr.to;
                    queue.add(new Edge(e.to, dist[e.to]));
                }
            }
        }
    }

    static void printPath(int target) {
        if(dist[target] == Long.MAX_VALUE) {
            System.out.println("NO PATH FOUND");
            return;
        }
        ArrayDeque<Integer> res = new ArrayDeque<>();
        while(target != -1) {
            res.addLast(target+1);
            target = prev[target];
        }
        while(!res.isEmpty()) System.out.print(res.pollLast() + " ");
    }


    static class Edge implements Comparable<Edge> {
        int to;
        long weight;

        public Edge(int a, long b) {
            to = a;
            weight = b;

        }
        @Override
        public int compareTo(Edge o) {
            return Long.compare(weight, o.weight);
        }
    }
}
