import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class G {

    public static void main(String[] args) {
        FastReader scan = new FastReader();
        PrintWriter out = new PrintWriter(System.out);
        Task solver = new Task();
        int t = 1;
        for(int tt = 1; tt <= t; tt++) solver.solve(tt, scan, out);
        out.close();
    }

    static class Task {
        int n, q;
        int[] parent, depth, heavy, head, pos, tree, val;
        int currPos = 0;
        ArrayList<Integer>[] adj;

        public void solve(int testNumber, FastReader scan, PrintWriter out) {
            n = scan.nextInt();
            adj = new ArrayList[n];
            parent = new int[n];
            depth = new int[n];
            heavy = new int[n];
            head = new int[n];
            pos = new int[n];
            val = new int[n];
            tree = new int[4 * n + 1];

            for(int i = 0; i < n; i++) adj[i] = new ArrayList<>();
            for(int i = 0; i < n - 1; i++) {
                int a = scan.nextInt() - 1, b = scan.nextInt() - 1;
                adj[a].add(b);
                adj[b].add(a);
            }
            q = scan.nextInt();
            dfs(-1, 0);
            decompose(0, 0);
            for(int i = 0; i < q; i++) {
                String queryType = scan.next();
                if(queryType.equals("I")) {
                    int cave = scan.nextInt() - 1, change = scan.nextInt();
                    update(1, 0, n - 1, pos[cave], val[cave] += change);
                }
                else {
                    int u = scan.nextInt() - 1, v = scan.nextInt() - 1;
                    out.println(query(u, v));
                }
            }
        }

        void update(int node, int s, int e, int index, int updateVal) {
            if(e < index || s > index) return;
            if(s == e) {
                tree[node] = updateVal;
                return;
            }
            int mid = (s + e) >> 1;
            update(node * 2, s, mid, index, updateVal);
            update(node * 2 + 1, mid + 1, e, index, updateVal);
            tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
        }

        int internalQuery(int node, int s, int e, int l, int r) {
            if(e < l || s > r) return -1;
            if(s >= l && e <= r) return tree[node];
            int mid = (s + e) >> 1;
            int a = internalQuery(node * 2, s, mid, l, r);
            int b = internalQuery(node * 2 + 1, mid + 1, e, l, r);
            return Math.max(a, b);
        }

        int query(int a, int b) {
            int ret = -1;
            while(head[a] != head[b]) {
                if(depth[head[a]] > depth[head[b]]) {
                    int temp = a;
                    a = b;
                    b = temp;
                }
                ret = Math.max(ret, internalQuery(1, 0, n - 1, pos[head[b]], pos[b]));
                b = parent[head[b]];
            }
            if(depth[a] > depth[b]) {
                int temp = a;
                a = b;
                b = temp;
            }
            ret = Math.max(ret, internalQuery(1, 0, n - 1, pos[a], pos[b]));
            return ret;
        }

        int dfs(int p, int at) {
            parent[at] = p;
            heavy[at] = -1;
            int ret = 1, maxSize = 0;
            for(int i : adj[at]) {
                if(i != p) {
                    depth[i] = depth[at] + 1;
                    int curr = dfs(at, i);
                    ret += curr;
                    if(curr > maxSize) {
                        curr = maxSize;
                        heavy[at] = i;
                    }
                }
            }
            return ret;
        }

        void decompose(int at, int headVertex) {
            head[at] = headVertex;
            pos[at] = currPos++;
            if(heavy[at] != -1) decompose(heavy[at], headVertex);
            for(int i : adj[at]) {
                if(i != parent[at] && i != heavy[at]) {
                    decompose(i, i);
                }
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
