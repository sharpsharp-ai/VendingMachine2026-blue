package de.sharpsharp.vendingmachine;

import org.junit.Test;

import static de.sharpsharp.vendingmachine.Drink.COLA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class VendingMachineTest {

  @Test
  public void addColaToOutputTray() {
    VendingMachine machine = new VendingMachine(null);

    machine.selectDrink(COLA);

    assertThat(machine.outputTray().size(), is(1));
  }

  @Test
  public void showPriceOfDrink() {
    VendingMachine machine = new VendingMachine(null);

    assertThat(machine.price(COLA), is(COLA.getPrice()));
  }
}