import java.util.*;
/*
Description:

Cow Land is a special amusement park for cows, where they roam around, eat delicious grass, and visit different cow attractions (the roller cowster is particularly popular).
There are a total of N different attractions (2≤N≤105). Certain pairs of attractions are connected by pathways, N−1 in total, in such a way that there is a unique route
consisting of various pathways between any two attractions. Each attraction i has an integer enjoyment value ei, which can change over the course of a day, since some attractions are
more appealing in the morning and others later in the afternoon.
A cow that travels from attraction i to attraction j gets to experience all the attractions on the route from i to j.
Curiously, the total enjoyment value of this entire route is given by the bitwise
XOR of all the enjoyment values along the route, including those of attractions i and j.
Please help the cows determine the enjoyment values of the routes they plan to use during their next trip to Cow Land.

Input:
The first line of input contains N and a number of queries Q (1≤Q≤105). The next line contains e1…eN (0≤ei≤109).
The next N−1 lines each describe a pathway in terms of two integer attraction IDs a and b (both in the range 1…N).
Finally, the last Q lines each describe either an update to one of the ei values or a query for the enjoyment of a route.
A line of the form "1 i v" indicates that ei should be updated to value v, and a line of the form "2 i j" is a query for the enjoyment of the route connecting attractions i and j

Output:
For each query of the form "2 i j", print on a single line the enjoyment of the route from i to j.

Example:

5 5
1 2 4 8 16
1 2
1 3
3 4
3 5
2 1 5
1 1 16
2 3 5
2 1 5
2 1 3


21
20
4
20
 */


public class Cowland {
    static int n, q;
    static long[] e, xor;
    static long[] tree, lazy;
    static int[] end, begin, depth, euler;
    static int[][] parent;
    static int index = 0;
    static ArrayList<Integer>[] adj;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        q = scan.nextInt();
        e = new long[n];
        xor = new long[n];
        tree = new long[4*n];
        lazy = new long[4*n];
        euler = new int[n];
        begin = new int[n];
        end = new int[n];
        depth = new int[n+1];
        parent = new int[n+1][18];
        for(int i = 0; i < n; i++) e[i] = scan.nextLong();

        for(int i = 0; i <= n; i++) Arrays.fill(parent[i], -1);
        adj = new ArrayList[n];
        for(int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for(int i = 0; i < n-1; i++) {
            int a = scan.nextInt()-1, b = scan.nextInt()-1;
            adj[a].add(b);
            adj[b].add(a);
        }

        go(-1,0,0,1);
        precompute();
        build(1, 0, n-1);

        for(int i = 0; i < q; i++) {
            int a = scan.nextInt(), b = scan.nextInt()-1, c = scan.nextInt()-1;
            if(a==1) {
                long d = c+1;
                update(1,0,n-1,begin[b],end[b],e[b]^d);
                e[b] = d;
            }
            else {
                long bb = get(1,0,n-1,begin[b],begin[b]);
                long cc = get(1,0,n-1,begin[c],begin[c]);
                System.out.println(bb^cc^e[lca(b,c)-1]);
            }
        }
    }

    static void update(int node, int s, int e, int l, int r, long val) {
        if(lazy[node] != 0) {
            if((s-e+1)%2==1) tree[node] ^= lazy[node];
            if(s != e) {
                lazy[2*node] ^= lazy[node];
                lazy[2*node+1] ^= lazy[node];
            }
            lazy[node] = 0;
        }
        if(s > e || s > r || e < l) return;
        if(s >= l && e <= r) {
            if((s-e+1)%2==1) tree[node] ^= val;
            if(s != e) {
                lazy[2*node] ^= val;
                lazy[2*node+1] ^= val;
            }
            return;
        }
        int mid = (s+e)/2;
        update(node*2,s,mid,l,r,val);
        update(node*2+1,mid+1,e,l,r,val);
        tree[node] = tree[2*node]^tree[2*node+1];
    }

    static long get(int node, int s, int e, int l, int r) {
        if(lazy[node] != 0) {
            if((s-e+1)%2==1) tree[node] ^= lazy[node];
            if(s != e) {
                lazy[2*node] ^= lazy[node];
                lazy[2*node+1] ^= lazy[node];
            }
            lazy[node] = 0;
        }
        if(s > e || s > r || e < l) return 0;
        if(s >= l && e <= r) {
            return tree[node];
        }
        int mid = (s+e)/2;
        long a = get(2*node,s,mid,l,r);
        long b = get(2*node+1,mid+1,e,l,r);
        return a^b;
    }

    static void build(int node, int s, int e) {
        if(s > e) return;
        if(s == e) {
            tree[node] = xor[euler[s]];
            return;
        }
        int mid = (s+e)/2;
        build(node*2,s,mid);
        build(node*2+1,mid+1,e);
        tree[node] = tree[2*node]^tree[2*node+1];
    }


    static void go(int p, int curr, long x, int at) {
        begin[curr] = index;
        euler[index] = curr;
        xor[curr] = x^e[curr];
        depth[curr+1] = at;
        parent[curr+1][0] = p+1;
        for(int i : adj[curr]) {
            if(i != p) {
                index++;
                go(curr, i, xor[curr], at+1);
            }
        }
        end[curr] = index;
    }

    static void precompute() {
        for(int i = 1; i < 18; i++) {
            for(int j = 1; j <= n; j++) {
                if(parent[j][i-1] != -1) {
                    parent[j][i] = parent[parent[j][i-1]][i-1];
                }
            }
        }
    }

    static int lca(int a, int b) {
        a++;
        b++;
        if(depth[b] < depth[a]) {
            int temp = a;
            a = b;
            b = temp;
        }
        int diff = depth[b]-depth[a];
        for(int i = 0; i < 18; i++) {
            if(((diff>>i)&1) != 0) {
                b = parent[b][i];
            }
        }
        if(a==b) return a;
        for(int i = 17; i >= 0; i--) {
            if(parent[a][i] != parent[b][i]) {
                a = parent[a][i];
                b = parent[b][i];
            }
        }
        return parent[a][0];
    }
}
