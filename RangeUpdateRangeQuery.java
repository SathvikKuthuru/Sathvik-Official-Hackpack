import java.util.Scanner;

public class RangeUpdatePointQuery {
    static int n;
    static int[] a;
    static int[] BIT1, BIT2;
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        n = scan.nextInt();
        a = new int[n];
        BIT1 = new int[n + 1];
        BIT2 = new int[n + 1];
        for(int i = 0; i < n; i++) a[i] = scan.nextInt();
        for(int i = 0; i < n; i++) rangeUpdate(i, i, a[i]);
        System.out.println(rangeSum(0, n - 2));
        rangeUpdate(2, n - 1, - 3);
        System.out.println(rangeSum(0, n - 2));
        System.out.println(rangeSum(1, n - 1));
    }

    static void update(int[] BIT, int index, int val) {
        index++;
        while(index <= n) {
            BIT[index] += val;
            index += index & (-index);
        }
    }

    static int internalSum(int[] BIT, int index) {
        index++;
        int ret = 0;
        while(index > 0) {
            ret += BIT[index];
            index -= index & (-index);
        }
        return ret;
    }

    static void rangeUpdate(int l, int r, int val) {
        update(BIT1, l, val);
        update(BIT1, r + 1, -val);
        update(BIT2, l, val * (l - 1));
        update(BIT2, r + 1, -val * r);
    }

    static int sum(int index) {
        return internalSum(BIT1, index) * index - internalSum(BIT2, index);
    }

    static int rangeSum(int l, int r) {
        return l == 0 ? sum(r) : sum(r) - sum(l - 1);
    }

}
