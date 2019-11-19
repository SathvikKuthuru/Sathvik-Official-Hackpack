import java.util.*;

public class MyClass {
    public static void main(String args[]) {
        Integer[] a = new Integer[5];
        for(int i = 0; i < 5; i++) a[i] = i;
        DeletionList<Integer> list = new DeletionList<Integer>(a);
        for(int i = 0; i < 5; i++) System.out.println(list.remove(0));
    }
    static class DeletionList<T> {
        T[] arr;
        int[] indexTree;
        boolean[] deleted;

        public DeletionList(T[] in) {
            arr = Arrays.copyOf(in, in.length);
            deleted = new boolean[arr.length];
            indexTree = new int[arr.length * 4];
            build(0, arr.length - 1, 1);
        }

        T remove(int index) {
            return walk(0, arr.length - 1, 1, index + 1, true);
        }
        
        T get(int index) {
            return walk(0, arr.length-1, 1, index + 1, false);
        }

        void build(int s, int e, int node) {
            if (s == e) {
                indexTree[node] = 1;
                return;
            }
            int mid = (s + e) >> 1;
            build(s, mid, node << 1);
            build(mid + 1, e, (node << 1) + 1);
            indexTree[node] = indexTree[node << 1] + indexTree[(node << 1) + 1];
        }

        T walk(int s, int e, int node, int need, boolean r) {
            if (indexTree[node] < need) return null;
            while (true) {
                if(r) indexTree[node]--;
                if (s == e) {
                    if(r) deleted[s] = true;
                    return arr[s];
                }
                int mid = (s + e) >> 1;
                if (indexTree[node << 1] >= need) {
                    e = mid;
                    node <<= 1;
                } else {
                    s = mid + 1;
                    need -= indexTree[node << 1];
                    node = (node << 1) + 1;
                }
            }
        }
        
        @Override
        public String toString() {
            StringBuilder res = new StringBuilder("[");
            for(int i = 0; i < arr.length; i++) {
                if(!deleted[i]) {
                    res.append(arr[i]);
                    res.append(", ");
                }
            }
            if(!res.toString().equals("[")) res.delete(res.length()-2, res.length());
            res.append("]");
            return res.toString();
        }
    }
}
