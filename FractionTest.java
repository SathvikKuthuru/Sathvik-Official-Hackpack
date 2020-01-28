public class FractionTest {
    public static void main(String[] args) {
        Fraction a = new Fraction(-1, 4);
        Fraction b = new Fraction(5);
        System.out.println(a.add(b));
    }

    static class Fraction {
        long num, den;
        int sign;

        public Fraction(long a, long b) {
            num = Math.abs(a);
            den = Math.abs(b);
            sign = a >= 0 == b >= 0 ? 1 : -1;
        }

        public Fraction(long a) {
            num = Math.abs(a);
            den = 1;
            sign = a >= 0 ? 1 : -1;
        }

        public Fraction copy() {
            return new Fraction(num*sign, den);
        }

        public Fraction reciprocal() {
            return new Fraction(den*sign, num);
        }

        public Fraction add(Fraction o) {
            if(o.den == 0 || den == 0) return null;
            if(sign != o.sign) {
                if(o.sign == -1) {
                    Fraction pos = o.copy().multiply(new Fraction(-1));
                    return subtract(pos);
                }
                else {
                    Fraction pos = this.copy().multiply(new Fraction(-1));
                    return o.subtract(pos);
                }
            }
            long lcm = LCM(den, o.den);
            long a = lcm/den * num, b = lcm/o.den * o.num;
            return new Fraction(a+b, lcm * sign);
        }

        public Fraction multiply(Fraction o) {
            return new Fraction(num * o.num * sign * o.sign, den * o.den);
        }

        public Fraction subtract(Fraction o) {
            if(den == 0 || o.den == 0) return null;
            if(o.sign == -1) {
                Fraction pos = o.copy().multiply(new Fraction(-1));
                return add(pos);
            }
            if(sign == -1) {
                Fraction neg = o.copy().multiply(new Fraction(-1));
                return add(neg);
            }
            long lcm = LCM(den, o.den);
            long a = lcm/den * num, b = lcm/o.den * o.num;
            return new Fraction(a-b, lcm);
        }

        public Fraction divide(Fraction o) {
            return multiply(o.reciprocal());
        }

        public Fraction reduce() {
            if(num == 0 || den == 0) return copy();
            long g = GCD(num, den);
            return new Fraction(num / g * sign, den / g);
        }

        @Override
        public String toString() {
            String res = "";
            if(sign == -1) res += "-";
            res += num + "/" + den;
            return res;
        }

        public static long LCM(long a, long b) {
            return a / GCD(a, b) * b;
        }

        public static long GCD(long a, long b) {
            if(b == 0) return a;
            return GCD(b, a%b);
        }
    }
}
