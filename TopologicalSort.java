import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class TopoLogicalSort {
    static int n, m;
    static ArrayList<Integer>[] adj;
    static ArrayDeque<Integer> order = new ArrayDeque<>();

    public static void main(String[] args) {
        topoSort();
        for(int i : order) System.out.print(i + " ");
    }

    //If false there is a cycle
    static boolean topoSort() {
        int[] deg = new int[n];
        for(int i = 0; i < n; i++) {
            for(int j : adj[i]) deg[j]++;
        }
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        for(int i = 0; i < n; i++) {
            if(deg[i] == 0) queue.addLast(i);
        }
        while(!queue.isEmpty()) {
            int q = queue.pollFirst();
            order.addLast(q);
            for(int i : adj[q]) {
                if(--deg[i] == 0) queue.addLast(i);
            }
        }
        return order.size() == n;
    }
}
