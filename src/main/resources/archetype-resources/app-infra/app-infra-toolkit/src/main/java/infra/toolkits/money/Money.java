#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.infra.toolkits.money;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Currency;

public final class Money implements Comparable<Money> {
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;
    private final BigDecimal amount; // normalized to currency fraction digits
    private final Currency currency;

    private Money(BigDecimal amount, Currency currency) {
        Objects.requireNonNull(currency, "currency");
        int scale = fractionDigits(currency);
        this.currency = currency;
        this.amount = normalize(amount, scale);
    }

    public static Money of(BigDecimal amount, Currency currency) {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");
        return new Money(amount, currency);
    }

    public static Money of(String amount, Currency currency) {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(currency, "currency");
        return new Money(new BigDecimal(amount), currency);
    }

    public static Money ofMinor(long minorUnits, Currency currency) {
        Objects.requireNonNull(currency, "currency");
        int scale = fractionDigits(currency);
        BigDecimal divisor = pow10(scale);
        return new Money(BigDecimal.valueOf(minorUnits).divide(divisor, scale, ROUNDING), currency);
    }

    public static Money zero(Currency currency) {
        Objects.requireNonNull(currency, "currency");
        return new Money(BigDecimal.ZERO, currency);
    }

    public BigDecimal amount() { return amount; }
    public Currency currency() { return currency; }

    public Money add(Money other) {
        Objects.requireNonNull(other, "other");
        ensureSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    public Money subtract(Money other) {
        Objects.requireNonNull(other, "other");
        ensureSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    public Money multiply(BigDecimal factor) {
        Objects.requireNonNull(factor, "factor");
        return new Money(this.amount.multiply(factor), this.currency);
    }

    public Money negate() { return new Money(this.amount.negate(), this.currency); }

    public boolean isZero() { return this.amount.signum() == 0; }
    public boolean isPositive() { return this.amount.signum() > 0; }
    public boolean isNegative() { return this.amount.signum() < 0; }

    public long toMinorUnitsExact() {
        int scale = fractionDigits(this.currency);
        BigDecimal multiplier = pow10(scale);
        return this.amount.multiply(multiplier).longValueExact();
    }

    private static BigDecimal normalize(BigDecimal value, int scale) {
        Objects.requireNonNull(value, "value");
        return value.setScale(scale, ROUNDING);
    }

    private static int fractionDigits(Currency currency) {
        int fd = currency.getDefaultFractionDigits();
        return Math.max(fd, 0);
    }

    private void ensureSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch: " + this.currency + " vs " + other.currency);
        }
    }

    private static BigDecimal pow10(int n) {
        if (n <= 0) return BigDecimal.ONE;
        return BigDecimal.TEN.pow(n);
    }

    @Override
    public int compareTo(Money o) {
        Objects.requireNonNull(o, "other");
        ensureSameCurrency(o);
        return this.amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return this.currency.equals(money.currency) && this.amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount.stripTrailingZeros());
    }

    @Override
    public String toString() { return currency.getCurrencyCode() + " " + amount.toPlainString(); }
}