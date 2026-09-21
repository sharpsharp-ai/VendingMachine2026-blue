package de.sharpsharp.vendingmachine.core;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MoneyTest {

    @Test
    public void formatsInGermanStyle() {
        assertThat(Money.ofCents(0).toString(), is("0,00 €"));
        assertThat(Money.ofCents(50).toString(), is("0,50 €"));
        assertThat(Money.ofCents(150).toString(), is("1,50 €"));
        assertThat(Money.ofCents(1200).toString(), is("12,00 €"));
    }

    @Test
    public void addsAndSubtracts() {
        Money oneFifty = Money.ofCents(100).plus(Money.ofCents(50));

        assertThat(oneFifty, is(Money.ofCents(150)));
        assertThat(oneFifty.minus(Money.ofCents(100)), is(Money.ofCents(50)));
    }

    @Test
    public void compares() {
        assertThat(Money.ofCents(50).isLessThan(Money.ofCents(100)), is(true));
        assertThat(Money.ofCents(100).isLessThan(Money.ofCents(100)), is(false));
        assertThat(Money.ofCents(100).isAtLeast(Money.ofCents(100)), is(true));
        assertThat(Money.ZERO.isZero(), is(true));
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeNegative() {
        Money.ofCents(50).minus(Money.ofCents(100));
    }
}
