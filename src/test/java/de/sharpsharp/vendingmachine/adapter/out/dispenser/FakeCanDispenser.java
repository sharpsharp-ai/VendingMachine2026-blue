package de.sharpsharp.vendingmachine.adapter.out.dispenser;

import de.sharpsharp.vendingmachine.core.Drink;
import de.sharpsharp.vendingmachine.core.port.out.CanDispenser;

/** Mechanics the tests control: every can drops, unless a test says the next one sticks. */
public final class FakeCanDispenser implements CanDispenser {

    private boolean nextCanSticks = false;

    public void nextCanSticks() {
        nextCanSticks = true;
    }

    @Override
    public Result dispense(Drink drink) {
        if (nextCanSticks) {
            nextCanSticks = false;
            return Result.STUCK;
        }
        return Result.DROPPED;
    }
}
