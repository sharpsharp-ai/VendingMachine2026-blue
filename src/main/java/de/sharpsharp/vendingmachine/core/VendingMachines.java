package de.sharpsharp.vendingmachine.core;

import de.sharpsharp.vendingmachine.core.port.in.VendingMachineControls;
import de.sharpsharp.vendingmachine.core.port.out.CanDispenser;
import de.sharpsharp.vendingmachine.core.port.out.CashBox;
import de.sharpsharp.vendingmachine.core.port.out.Clock;

/**
 * The only way to get a machine from outside the core.
 * <p>
 * The class behind it is package-private, so nobody outside the core can reach the rules
 * directly – neither the web adapter nor a test. Everyone talks to the ports.
 */
public final class VendingMachines {

    private VendingMachines() {
    }

    public static VendingMachineControls create(Inventory inventory, CashBox cashBox, Clock clock, CanDispenser dispenser) {
        return new VendingMachine(inventory, cashBox, clock, dispenser);
    }
}
