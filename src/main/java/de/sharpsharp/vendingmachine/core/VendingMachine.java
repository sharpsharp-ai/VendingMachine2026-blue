package de.sharpsharp.vendingmachine.core;

import de.sharpsharp.vendingmachine.core.port.in.VendingMachineControls;
import de.sharpsharp.vendingmachine.core.port.out.CanDispenser;
import de.sharpsharp.vendingmachine.core.port.out.CashBox;
import de.sharpsharp.vendingmachine.core.port.out.Clock;

import java.util.List;

/**
 * The core of the machine: all business rules belong here. Created only through {@link VendingMachines}.
 * <p>
 * It talks to the outside world only through ports: the customer's actions come in
 * through {@link VendingMachineControls}; coins, the time of day and the mechanics come from
 * {@link CashBox}, {@link Clock} and {@link CanDispenser}. The cans are its own affair ({@link Inventory}).
 * <p>
 * So far the machine does nothing: every action is ignored, and the state is the one right
 * after switching it on. The methods are synchronized because there is one machine and one
 * customer at a time, while the web adapter may receive several requests at once.
 */
class VendingMachine implements VendingMachineControls {

    private final Inventory inventory;
    private final CashBox cashBox;
    private final Clock clock;
    private final CanDispenser dispenser;

    VendingMachine(Inventory inventory, CashBox cashBox, Clock clock, CanDispenser dispenser) {
        this.inventory = inventory;
        this.cashBox = cashBox;
        this.clock = clock;
        this.dispenser = dispenser;
    }

    @Override
    public synchronized void insertCoin(Coin coin) {
    }

    @Override
    public synchronized void selectDrink(Drink drink) {
    }

    @Override
    public synchronized void cancel() {
    }

    @Override
    public synchronized List<Drink> emptyOutputTray() {
        return List.of();
    }

    @Override
    public synchronized List<Coin> emptyCoinReturn() {
        return List.of();
    }

    @Override
    public synchronized MachineState state() {
        return new MachineState(Money.ZERO, Message.INSERT_COINS, inventory.slots(), List.of(), List.of());
    }
}
