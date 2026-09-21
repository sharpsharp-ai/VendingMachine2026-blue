package de.sharpsharp.vendingmachine.core.port.out;

import de.sharpsharp.vendingmachine.core.Drink;

/** Out-port: the mechanics that push a can out of its slot towards the output tray. */
public interface CanDispenser {

    /** What became of the can. */
    enum Result {
        /** The can fell into the output tray. */
        DROPPED,
        /** The can got stuck on the way: out of the slot, but not in the tray. */
        STUCK
    }

    /** Pushes one can of this drink out of its slot. */
    Result dispense(Drink drink);
}
