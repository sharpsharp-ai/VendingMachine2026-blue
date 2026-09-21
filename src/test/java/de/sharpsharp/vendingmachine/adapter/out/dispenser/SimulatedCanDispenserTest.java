package de.sharpsharp.vendingmachine.adapter.out.dispenser;

import de.sharpsharp.vendingmachine.core.port.out.CanDispenser.Result;
import org.junit.Test;

import static de.sharpsharp.vendingmachine.core.Drink.COLA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimulatedCanDispenserTest {

    private final SimulatedCanDispenser dispenser = new SimulatedCanDispenser();

    @Test
    public void everySeventhCanGetsStuck() {
        for (int can = 1; can <= 14; can++) {
            Result expected = can % 7 == 0 ? Result.STUCK : Result.DROPPED;
            assertThat("can " + can, dispenser.dispense(COLA), is(expected));
        }
    }
}
