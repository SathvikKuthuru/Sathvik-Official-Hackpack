import java.util.*;

public class LIS {
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        TreeSet<Integer> t = new TreeSet<>();
        for(int i = 0; i < n; i++) {
            int a = scan.nextInt();
            Integer next = t.higher(a);
            if(next != null) t.remove(next);
            t.add(a);
        }
        System.out.println(t.size());
    }
}
