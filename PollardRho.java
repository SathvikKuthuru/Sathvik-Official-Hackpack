import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

//for a number n, stores all prime factors and frequencies in a hashmap
public class pollardrho {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		long n=scan.nextLong();

		f=new HashMap<>();
		r=new Random();
		
		takeFactors(n);
	}
	public static void takeFactors(long n) {
		if(n==1) return;
		if(BigInteger.valueOf(n).isProbablePrime(30)) {
			add(n);
			return;
		}
		long p;
		if(n%2==0) {
			p=2;
		}
		else p=pollardrho(n);
		takeFactors(p);
		takeFactors(n/p);
	}
	public static void add(long p) {
		if(!f.containsKey(p)) {
			f.put(p,1L);
		}
		else f.put(p,f.get(p)+1);
	}
	static HashMap<Long,Long> f;
	
	static Random r;
	
	//returns divisor of n assuming n isn't prime
	public static long pollardrho(long n) {
		long x, y, d;  BigInteger ab, nb;

		nb = BigInteger.valueOf(n);
		do {
			ab = BigInteger.valueOf(Math.abs(r.nextInt(100_000)) + 2);
			x = y = Math.abs(r.nextInt(100_000)) + 2;
			do {
				x = poly(x, ab, nb);  y = polypoly(y, ab, nb);//pseudo-randomness
				d = gcd(n, Math.abs(x - y));
			} while (d == 1);
		} while (d == n);
		return d;
	}
	public static long poly(long x, BigInteger ab, BigInteger nb) {
		BigInteger xb = BigInteger.valueOf(x);
		return xb.multiply(xb).add(ab).mod(nb).longValue();
	}

	public static long polypoly(long x, BigInteger ab, BigInteger nb) {
		BigInteger xb = BigInteger.valueOf(x), p = xb.multiply(xb).add(ab);
		return p.multiply(p).add(ab).mod(nb).longValue();
	}
	public static long gcd(long a, long b) {
		if(b==0) return a;
		return gcd(b,a%b);
	}
}
