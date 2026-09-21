package de.sharpsharp.vendingmachine.adapter.out.inmemory;

import org.junit.Test;

import static de.sharpsharp.vendingmachine.core.Coin.FIFTY_CENT;
import static de.sharpsharp.vendingmachine.core.Coin.ONE_EURO;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InMemoryCashBoxTest {

    @Test
    public void startsWithTheSameNumberOfEveryCoin() {
        InMemoryCashBox cashBox = new InMemoryCashBox(5);

        assertThat(cashBox.count(FIFTY_CENT), is(5));
        assertThat(cashBox.count(ONE_EURO), is(5));
    }

    @Test
    public void addingAndRemovingChangesTheCountOfThatCoinOnly() {
        InMemoryCashBox cashBox = new InMemoryCashBox(5);

        cashBox.add(ONE_EURO);
        cashBox.remove(FIFTY_CENT);

        assertThat(cashBox.count(ONE_EURO), is(6));
        assertThat(cashBox.count(FIFTY_CENT), is(4));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotRemoveACoinThatIsNotThere() {
        InMemoryCashBox cashBox = new InMemoryCashBox(0);

        cashBox.remove(ONE_EURO);
    }
}
