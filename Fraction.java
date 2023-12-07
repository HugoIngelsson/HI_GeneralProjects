public class Fraction {
    private long numerator;
    private long denominator;

    public Fraction(long numerator, long denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction(long numerator) {
        this.numerator = numerator;
        this.denominator = 1L;
    }

    public Fraction addFraction(Fraction other, boolean negative) {
        long numerator = this.numerator*other.denominator;
        if (negative) numerator -= other.numerator*this.denominator;
        else numerator += other.numerator*this.denominator;

        long denominator = this.denominator*other.denominator;

        long gcd = Fraction.gcd(Math.abs(numerator), Math.abs(denominator));
        if (denominator < 0) gcd *= -1;
        numerator /= gcd;
        denominator /= gcd;

        return new Fraction(numerator, denominator);
    }

    public Fraction multiplyFraction(Fraction other) {
        long numerator = this.numerator*other.numerator;
        long denominator = this.denominator*other.denominator;

        long gcd = Fraction.gcd(Math.abs(numerator), Math.abs(denominator));
        if (denominator < 0) gcd *= -1;
        numerator /= gcd;
        denominator /= gcd;

        return new Fraction(numerator, denominator);
    }

    public boolean isZero() {
        return this.numerator == 0 && this.denominator != 0;
    }

    public boolean isOne() {
        return this.numerator / this.denominator == 1 && 
                this.numerator % this.denominator == 0;
    }

    public Fraction clone() {
        return new Fraction(this.numerator, this.denominator);
    }

    public String toString() {
        if (this.denominator == 1) return this.numerator + "";
        if (this.denominator > 1e5 || this.numerator > 1e5) return "" + ((double)this.numerator/this.denominator);

        return this.numerator + "/" + this.denominator;
    }

    public static Fraction inverseFraction(Fraction fraction) {
        return new Fraction(fraction.denominator, fraction.numerator);
    }

    public static long gcd(long a, long b) {
        if (b == 0) return a;
        return gcd(b, a%b);
    }
}
