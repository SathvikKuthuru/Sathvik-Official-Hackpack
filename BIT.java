import java.util.*;

public class BITtest {
    public static void main(String args[]) {
        BIT b = new BIT(5);
        b.update(0, 2);
        b.update(1, 3);
        System.out.println(b.getSum(2));
    }
    
    static class BIT {
        int[] update;

        public BIT(int n) {
            update = new int[n+1];
        }

        int getSum(int index) {
            int sum = 0;
            index++;
            while(index > 0) {
                sum += update[index];
                index -= index & (-index);
            }
            return sum;
        }

        int getSum(int a, int b) {
            return getSum(b) - getSum(a-1);
        }

        void update(int index, int val) {
            index++;
            while(index < update.length) {
                update[index] += val;
                index += index & (-index);
            }
        }
    }
}
