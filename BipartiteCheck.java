import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class BipartiteCheck {
    static ArrayList<Integer>[] adj;
    static int n, m;
    public static void main(String[] args) {
        boolean isBipartite = bipartite();
        if(isBipartite) System.out.println("This graph is bipartite");
    }

    static boolean bipartite() {
        int[] color = new int[n];
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        Arrays.fill(color, -1);
        color[0] = 1;
        queue.addLast(0);
        while(!queue.isEmpty()) {
            int q = queue.pollFirst();
            for(int i : adj[q]) {
                if(color[i] == -1) {
                    color[i] = color[q] ^ 1;
                    queue.addLast(i);
                }
                else if(color[i] == color[q]) return false;
            }
        }
        return true;
    }
}
