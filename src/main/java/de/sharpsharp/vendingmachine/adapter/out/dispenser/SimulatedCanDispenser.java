package de.sharpsharp.vendingmachine.adapter.out.dispenser;

import de.sharpsharp.vendingmachine.core.Drink;
import de.sharpsharp.vendingmachine.core.port.out.CanDispenser;

/**
 * Simulated mechanics: every can drops, except every seventh, which gets stuck.
 * Deterministic on purpose, so a demonstration and the golden master can rely on it.
 */
public final class SimulatedCanDispenser implements CanDispenser {

    /** The 7th, 14th, 21st … can gets stuck. */
    public static final int EVERY_NTH_CAN_STICKS = 7;

    private int cansPushed = 0;

    @Override
    public Result dispense(Drink drink) {
        cansPushed++;
        if (cansPushed % EVERY_NTH_CAN_STICKS == 0) {
            return Result.STUCK;
        }
        return Result.DROPPED;
    }
}
