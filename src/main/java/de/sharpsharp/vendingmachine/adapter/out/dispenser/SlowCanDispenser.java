package de.sharpsharp.vendingmachine.adapter.out.dispenser;

import de.sharpsharp.vendingmachine.core.Drink;
import de.sharpsharp.vendingmachine.core.port.out.CanDispenser;

import java.util.Random;

/**
 * Out-adapter: mechanics that take their time. Wraps another dispenser and adds what real
 * mechanics add – pushing a can takes an unpredictable moment.
 * <p>
 * That is on purpose. The web page shows the new state only after the server has answered,
 * and the server answers only after the can has dropped, so the page updates late and
 * irregularly. A browser test has to wait for the state it expects instead of reading the
 * page right after the click.
 */
public final class SlowCanDispenser implements CanDispenser {

    /** Pushing a can takes between these two durations, evenly spread. */
    public static final int MIN_MILLIS = 300;
    public static final int MAX_MILLIS = 2000;

    private final CanDispenser mechanics;
    private final int minMillis;
    private final int maxMillis;
    private final Random random = new Random();

    public SlowCanDispenser(CanDispenser mechanics) {
        this(mechanics, MIN_MILLIS, MAX_MILLIS);
    }

    SlowCanDispenser(CanDispenser mechanics, int minMillis, int maxMillis) {
        this.mechanics = mechanics;
        this.minMillis = minMillis;
        this.maxMillis = maxMillis;
    }

    @Override
    public Result dispense(Drink drink) {
        takeTime();
        return mechanics.dispense(drink);
    }

    private void takeTime() {
        int millis = minMillis + random.nextInt(maxMillis - minMillis + 1);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
