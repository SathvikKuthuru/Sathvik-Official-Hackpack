import java.util.Scanner;

public class DisjointSetUnion {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt(), m = scan.nextInt();
        DSU set = new DSU(n);
        for(int i = 0; i < m; i++) {
            boolean cycle = set.unite(scan.nextInt()-1, scan.nextInt()-1);
            if(cycle) {
                System.out.println("CYCLE FOUND");
            }
        }
    }

    static class DSU {
        int n;
        int[] parent, size;

        public DSU(int v) {
            n = v;
            parent = new int[n];
            size = new int[n];
            for(int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int findRoot(int curr) {
            if(curr == parent[curr]) return curr;
            return parent[curr] = findRoot(parent[curr]);
        }

        public boolean unite(int a, int b) {
            int rootA = findRoot(a);
            int rootB = findRoot(b);
            if(rootA == rootB) return true;
            if(size[rootA] > size[rootB]) {
                parent[rootB] = rootA;
                size[rootA] += size[rootB];
            }
            else {
                parent[rootA] = rootB;
                size[rootB] += size[rootA];
            }
            return false;
        }

    }
}
