//Problem that uses Dinic

import java.io.*;
import java.util.*;

public class F {
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
        ArrayList<Edge>[] adj;
        HashMap<Integer, Integer> compress, convert;
        ArrayList<Edge> edges;
        int compressIndex;

        public void solve(int testNumber, FastReader scan, PrintWriter out) {
            int n = scan.nextInt();
            compress = new HashMap<>();
            convert = new HashMap<>();
            compressIndex = 2 * n - 1;
            edges = new ArrayList<>();

            int[][] a = new int[n][n];
            for(int i = 0; i < n; i++) {
                for(int j = 0; j < n; j++) {
                    a[i][j] = scan.nextInt();
                    if(!compress.containsKey(a[i][j])) {
                        compress.put(a[i][j], compressIndex);
                        convert.put(compressIndex, a[i][j]);
                        compressIndex++;
                    }
                }
            }
            adj = new ArrayList[compressIndex + 2];
            for(int i = 0; i < adj.length; i++) adj[i] = new ArrayList<>();
            int currVertex = 0;
            for(int i = n - 1; i >= 0; i--) {
                HashSet<Integer> curr = new HashSet<>();
                for(int j = 0; j < n - i; j++) {
                    curr.add(a[i + j][j]);
                }
                for(int x : curr) addEdge(currVertex, compress.get(x), 1);
                currVertex++;
            }
            for(int i = 1; i < n; i++) {
                HashSet<Integer> curr = new HashSet<>();
                for(int j = 0; j < n - i; j++) {
                    curr.add(a[j][i + j]);
                }
                for(int x : curr) addEdge(currVertex, compress.get(x), 1);
                currVertex++;
            }
            for(int i = 0; i < 2 * n - 1; i++) {
                addEdge(adj.length - 2, i, 1);
            }
            for(int i = 2 * n - 1; i < adj.length - 2; i++) {
                addEdge(i, adj.length - 1, 1);
            }
            long ans = flow(adj.length - 2, adj.length - 1);
            if(ans != 2 * n - 1) {
                out.println("NO");
                return;
            }
            else {
                int[] res = new int[2 * n - 1];
                for(Edge e : edges) {
                    if(e.from < 2 * n - 1 && e.to >= 2 * n - 1 && e.to < adj.length - 2 && e.flow == e.cap) {
                        res[e.from] = convert.get(e.to);
                    }
                }
                out.println("YES");
                for(int i : res) out.printf("%d ", i);
            }
        }

        void addEdge(int a, int b, int cap) {
            Edge ab = new Edge(a, b, cap);
            Edge ba = new Edge(b, a, 0);
            ab.rev = ba;
            ba.rev = ab;
            edges.add(ab);
            edges.add(ba);
            adj[a].add(ab);
            adj[b].add(ba);
        }

        int[] level;

        boolean bfs(int s, int t) {
            int n = adj.length;
            level = new int[n];
            Arrays.fill(level, -1);
            level[s] = 0;
            ArrayDeque<Integer> queue = new ArrayDeque<>();
            queue.addLast(s);

            while(!queue.isEmpty()) {
                int q = queue.pollFirst();
                for(Edge e : adj[q]) {
                    if(e.flow == e.cap || level[e.to] != -1) continue;
                    level[e.to] = level[q] + 1;
                    queue.addLast(e.to);
                }
            }
            return level[t] != -1;
        }

        long dfs(int at, int t, int[] block, long flow) {
            if(at == t) return flow;
            for(; block[at] < adj[at].size(); block[at]++) {
                Edge e = adj[at].get(block[at]);
                long remain = e.cap - e.flow;
                if(remain > 0 && level[e.to] == level[at] + 1) {
                    long bottleneck = dfs(e.to, t, block, Math.min(flow, remain));
                    if(bottleneck > 0) {
                        e.augment(bottleneck);
                        return bottleneck;
                    }
                }
            }
            return 0;
        }


        long flow(int s, int t) {
            long ans = 0;
            int n = adj.length;
            while(bfs(s, t)) {
                int[] curr = new int[n];
                long flow = 0;
                while((flow = dfs(s, t, curr, Long.MAX_VALUE / 10)) > 0) ans += flow;
            }
            return ans;
        }

        static class Edge {
            int from, to;
            Edge rev;
            long cap, flow;

            public Edge(int a, int b, long c) {
                from = a;
                to = b;
                cap = c;
                flow = 0;
            }

            public void augment(long val) {
                flow += val;
                rev.flow -= val;
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
