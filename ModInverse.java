public class ExtendedGCD {
    static int x = 1, y = 1;
    public static void main(String[] args) {
        System.out.println(gcdExtended(35, 15));
        System.out.println(x + " " + y);
        System.out.println(modInverse1(3, 11));
        System.out.println(modInverse2(3, 11));
        System.out.println(modInverse3(3, 11));
    }

    //If m is prime
    static int modInverse3(int a, int m) {
        return pow(a, m - 2, m);
    }

    static int pow(int a, int b, int m) {
        if(b == 0) return 1;
        int ret = pow(a, b / 2, m);
        ret = (ret * ret) % m;
        if(b % 2 == 1) ret = (ret * a) % m;
        return ret;
    }

    //a and m are coprime
    static int modInverse2(int a, int m) {
        int m0 = m;
        int y = 0, x = 1;
        if(m == 1) return 0;
        while(a > 1) {
            int q = a / m;
            int t = m;
            m = a % m;
            a = t;
            t = y;
            y = x - q * y;
            x = t;
        }
        if(x < 0) x += m0;
        return x;
    }

    static int modInverse1(int a, int m) {
        int g = gcdExtended(a, m);
        if(g != 1) return -1;
        int res = (x % m + m) % m;
        return res;
    }

    static int gcdExtended(int a, int b) {
        if(a == 0) {
            x = 0;
            y = 1;
            return b;
        }
        int gcd = gcdExtended(b % a, a);
        int x1 = x, y1 = y;
        x = y1 - (b / a) * x1;
        y = x1;
        return gcd;
    }

}
