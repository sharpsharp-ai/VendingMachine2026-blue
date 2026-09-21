package de.sharpsharp.vendingmachine.adapter.in.cucumber;

import de.sharpsharp.vendingmachine.adapter.out.clock.FakeClock;
import de.sharpsharp.vendingmachine.adapter.out.dispenser.FakeCanDispenser;
import de.sharpsharp.vendingmachine.adapter.out.inmemory.InMemoryCashBox;
import de.sharpsharp.vendingmachine.core.Drink;
import de.sharpsharp.vendingmachine.core.Inventory;
import de.sharpsharp.vendingmachine.core.Money;
import de.sharpsharp.vendingmachine.core.VendingMachines;
import de.sharpsharp.vendingmachine.core.port.in.VendingMachineControls;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

/**
 * Step definitions for the feature files.
 * <p>
 * The acceptance tests talk to the machine only through its in-port
 * ({@link VendingMachineControls}) – never through the web UI or HTTP.
 * The out-ports are an in-memory cash box plus a clock and mechanics the steps
 * can control, and the inventory is the core's own.
 * <p>
 * Cucumber creates a new instance of this class for every scenario,
 * so every scenario starts with a freshly built machine.
 */
public class VendingMachineSteps {

    private final Inventory inventory = Inventory.full();
    private final InMemoryCashBox cashBox = InMemoryCashBox.withStartingChange();
    private final FakeClock clock = new FakeClock();
    private final FakeCanDispenser dispenser = new FakeCanDispenser();
    private final VendingMachineControls machine = VendingMachines.create(inventory, cashBox, clock, dispenser);

    // ---- Angenommen -------------------------------------------------------

    @Angenommen("der Automat ist frisch gestartet")
    public void theMachineIsFreshlyStarted() {
        // The machine is built fresh for every scenario (see the fields above).
        // This step documents that in the feature file and double-checks it.
        assertThat(machine.state().credit(), is(Money.ZERO));
        assertThat(machine.state().outputTray(), is(empty()));
        assertThat(machine.state().coinReturn(), is(empty()));
    }

    // ---- Wenn -------------------------------------------------------------

    @Wenn("ich {drink} wähle")
    public void iSelect(Drink drink) {
        machine.selectDrink(drink);
    }

    // ---- Dann -------------------------------------------------------------

    @Dann("liegt eine Dose {drink} im Ausgabefach")
    public void oneCanLiesInTheOutputTray(Drink drink) {
        assertThat(machine.state().outputTray(), contains(drink));
    }
}
