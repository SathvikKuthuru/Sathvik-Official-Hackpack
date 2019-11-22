import java.util.*;

public class Factorial {
    static int max = (int) (1e6), MOD = (int) (1e9+7);
    static long[] fact = new long[max+1], invFact = new long[max+1], naturalInverse = new long[max+1];
 
    public static void main(String args[]) {
        Scanner scan = new Scanner(System.in);
        fact[0] = invFact[0] = invFact[1] = naturalInverse[0] = naturalInverse[1] = 1;
        for(int i = 1; i <= max; i++) {
            fact[i] = (fact[i-1]*i)%MOD;
            if(i == 1) continue;
            naturalInverse[i] = naturalInverse[MOD % i] * (MOD - MOD/i) % MOD;
            invFact[i] = (invFact[i-1]*naturalInverse[i])%MOD;
        }
        int n = scan.nextInt();
        while(n-->0) System.out.println(binomial(scan.nextInt(), scan.nextInt()));
    }
    
    static long binomial(int a, int b) {
        return ((fact[a]*invFact[b])%MOD*invFact[a-b])%MOD;
    }
}
