package de.sharpsharp.vendingmachine.adapter.out.dispenser;

import de.sharpsharp.vendingmachine.core.port.out.CanDispenser.Result;
import org.junit.Test;

import static de.sharpsharp.vendingmachine.core.Drink.COLA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

public class SlowCanDispenserTest {

    private final FakeCanDispenser mechanics = new FakeCanDispenser();

    @Test
    public void answersWhatTheWrappedMechanicsAnswer() {
        SlowCanDispenser dispenser = new SlowCanDispenser(mechanics, 0, 0);

        assertThat(dispenser.dispense(COLA), is(Result.DROPPED));
        mechanics.nextCanSticks();
        assertThat(dispenser.dispense(COLA), is(Result.STUCK));
    }

    @Test
    public void everyCanTakesAtLeastTheShortestTime() {
        SlowCanDispenser dispenser = new SlowCanDispenser(mechanics, 40, 40);

        long before = System.nanoTime();
        dispenser.dispense(COLA);
        long elapsedMillis = (System.nanoTime() - before) / 1_000_000;

        // One millisecond of tolerance: the sleep is only as exact as the system timer.
        assertThat(elapsedMillis, greaterThanOrEqualTo(39L));
    }
}
