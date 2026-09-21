package de.sharpsharp.vendingmachine.core;

import org.junit.Test;

import java.util.List;

import static de.sharpsharp.vendingmachine.core.Drink.BEER;
import static de.sharpsharp.vendingmachine.core.Drink.COLA;
import static de.sharpsharp.vendingmachine.core.Drink.LEMON;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class InventoryTest {

    private final Inventory inventory = Inventory.full();

    @Test
    public void startsWithEverySlotFull() {
        assertThat(inventory.stock(COLA), is(Inventory.CANS_PER_SLOT));
        assertThat(inventory.stock(LEMON), is(Inventory.CANS_PER_SLOT));
    }

    @Test
    public void removingACanLowersTheStockOfThatSlotOnly() {
        inventory.removeOne(COLA);

        assertThat(inventory.stock(COLA), is(4));
        assertThat(inventory.stock(LEMON), is(5));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotRemoveFromAnEmptySlot() {
        for (int can = 0; can < Inventory.CANS_PER_SLOT; can++) {
            inventory.removeOne(COLA);
        }

        inventory.removeOne(COLA);
    }

    @Test
    public void numbersTheSlotsInTheirOrderOnTheFront() {
        inventory.removeOne(BEER);

        List<Slot> slots = inventory.slots();

        assertThat(slots.size(), is(Drink.values().length));
        assertThat(slots.get(0), is(new Slot(1, COLA, 5)));
        assertThat(slots.get(3), is(new Slot(4, BEER, 4)));
    }
}
