package de.sharpsharp.vendingmachine;

import de.sharpsharp.vendingmachine.adapter.in.web.WebAdapter;
import de.sharpsharp.vendingmachine.adapter.out.clock.SystemClock;
import de.sharpsharp.vendingmachine.adapter.out.dispenser.SimulatedCanDispenser;
import de.sharpsharp.vendingmachine.adapter.out.dispenser.SlowCanDispenser;
import de.sharpsharp.vendingmachine.adapter.out.inmemory.InMemoryCashBox;
import de.sharpsharp.vendingmachine.core.Inventory;
import de.sharpsharp.vendingmachine.core.VendingMachines;
import de.sharpsharp.vendingmachine.core.port.in.VendingMachineControls;

/**
 * Composition root: wires the machine together and starts the web housing.
 * <p>
 * This is the only class that knows both the core and the adapters – which adapter is
 * plugged into which port is decided here and nowhere else.
 * There is no refilling: restarting the application resets slots and cash box.
 * The mechanics let every seventh can get stuck ({@link SimulatedCanDispenser}) and take
 * their time about every can on purpose ({@link SlowCanDispenser}) – once the core uses them.
 */
public final class Main {

    static final int DEFAULT_PORT = 7070;

    private Main() {
    }

    public static void main(String[] args) {
        WebAdapter housing = start(portFromEnvironment());
        System.out.println("Getränkeautomat läuft auf http://localhost:" + housing.port());
    }

    /**
     * Wires the machine with its production adapters and starts the web housing.
     * Port 0 lets the operating system pick a free one; {@link WebAdapter#port()} tells which.
     */
    public static WebAdapter start(int port) {
        VendingMachineControls machine = VendingMachines.create(
                Inventory.full(),
                InMemoryCashBox.withStartingChange(),
                new SystemClock(),
                new SlowCanDispenser(new SimulatedCanDispenser()));

        WebAdapter housing = new WebAdapter(machine);
        housing.start(port);
        return housing;
    }

    /** PORT=8080 picks another port; without it the machine listens on 7070. */
    private static int portFromEnvironment() {
        return Integer.parseInt(System.getenv().getOrDefault("PORT", String.valueOf(DEFAULT_PORT)));
    }
}
