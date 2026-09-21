package de.sharpsharp.vendingmachine.core;

import java.util.Locale;

/**
 * An amount of money, counted in whole cents so that there is never a rounding problem.
 * Money can never be negative – the machine never owes anyone anything.
 */
public record Money(int cents) {

    public static final Money ZERO = new Money(0);

    public Money {
        if (cents < 0) {
            throw new IllegalArgumentException("Money cannot be negative: " + cents + " cents");
        }
    }

    public static Money ofCents(int cents) {
        return new Money(cents);
    }

    public Money plus(Money other) {
        return new Money(cents + other.cents);
    }

    public Money minus(Money other) {
        return new Money(cents - other.cents);
    }

    public boolean isLessThan(Money other) {
        return cents < other.cents;
    }

    public boolean isAtLeast(Money other) {
        return cents >= other.cents;
    }

    public boolean isZero() {
        return cents == 0;
    }

    /** German notation, e.g. "1,50 €". */
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%d,%02d €", cents / 100, cents % 100);
    }
}
