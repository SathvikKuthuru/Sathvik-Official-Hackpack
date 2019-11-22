import java.math.BigInteger;
public class PermutationGenerator {
    private int[] a;
    private BigInteger numLeft;
    private BigInteger total;
    public PermutationGenerator (int n) {
        if (n < 1) {
            throw new IllegalArgumentException ("Min 1");
        }
        a = new int[n];
        total = getFactorial (n);
        reset ();
    }
    public void reset () {
        for (int i = 0; i < a.length; i++) {
            a[i] = i;
        }
        numLeft = new BigInteger (total.toString ());
    }
    public BigInteger getNumLeft () {
        return numLeft;
    }
    public BigInteger getTotal () {
        return total;
    }
    public boolean hasMore () {
        return numLeft.compareTo (BigInteger.ZERO) == 1;
    }
    private static BigInteger getFactorial (int n) {
        BigInteger fact = BigInteger.ONE;
        for (int i = n; i > 1; i--) {
            fact = fact.multiply (new BigInteger (Integer.toString (i)));
        }
        return fact;
    }
    public int[] getNext () {
        if (numLeft.equals (total)) {
            numLeft = numLeft.subtract (BigInteger.ONE);
            return a;
        }
        int temp;
        int j = a.length - 2;
        while (a[j] > a[j+1]) {
            j--;
        }
        int k = a.length - 1;
        while (a[j] > a[k]) 
            k--;
        temp = a[k];
        a[k] = a[j];
        a[j] = temp;
        int r = a.length - 1;
        int s = j + 1;
        while (r > s) {
            temp = a[s];
            a[s] = a[r];
            a[r] = temp;
            r--;
            s++;
        }
        numLeft = numLeft.subtract (BigInteger.ONE);
        return a;
    }
}
