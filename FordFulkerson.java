import java.io.*;
import java.util.*;

public class A {
    //Solution by Sathvik Kuthuru
    public static void main(String[] args) {
        FastReader scan = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        Task solver = new Task();
        int t = 1;
        for(int tt = 1; tt <= t; tt++) solver.solve(tt, scan, out);
        out.close();
    }

    static class Task {
        int n, m;
        ArrayList<Edge>[] adj;

        public void solve(int testNumber, FastReader scan, PrintWriter out) {
            n = scan.nextInt();
            m = scan.nextInt();
            adj = new ArrayList[n];
            for(int i = 0; i < n; i++) adj[i] = new ArrayList<>();
            for(int i = 0; i < m; i++) {
                int a = scan.nextInt(), b = scan.nextInt(), c = scan.nextInt();
                Edge ab = new Edge(a, b, c);
                Edge ba = new Edge(b, a, 0);
                ab.rev = ba;
                ba.rev = ab;
                adj[a].add(ab);
                adj[b].add(ba);
            }
            out.println(fordFulkerson(0, 5));
        }

        boolean bfs(int s, int t, Edge[] parent) {
            boolean[] visited = new boolean[n];
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            visited[s] = true;
            parent[s] = null;
            queue.addLast(0);
            while(!queue.isEmpty()) {
                int q = queue.pollFirst();
                for(Edge e : adj[q]) {
                    if(!visited[e.to] && e.flow > 0) {
                        parent[e.to] = e;
                        visited[e.to] = true;
                        queue.addLast(e.to);
                    }
                }
            }
            return visited[t];
        }

        long fordFulkerson(int s, int t) {
            Edge[] parent = new Edge[n];
            long maxFlow = 0;
            while(bfs(s, t, parent)) {
                long pathFlow = Integer.MAX_VALUE;
                int at = t;
                while(at != s) {
                    pathFlow = Math.min(pathFlow, parent[at].flow);
                    at = parent[at].from;
                }
                at = t;
                while(at != s) {
                    parent[at].flow -= pathFlow;
                    parent[at].rev.flow += pathFlow;
                    at = parent[at].from;
                }
                maxFlow += pathFlow;
            }
            return maxFlow;
        }

        static class Edge {
            int from, to;
            Edge rev;
            long flow;

            public Edge(int a, int b, long c) {
                from = a;
                to = b;
                flow = c;
            }
        }
    }

    static void shuffle(int[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    static void shuffle(long[] a) {
        Random get = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = get.nextInt(a.length);
            long temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public FastReader(String s) throws FileNotFoundException {
            br = new BufferedReader(new FileReader(new File(s)));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

}
