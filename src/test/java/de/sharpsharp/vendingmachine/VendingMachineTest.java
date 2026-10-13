package de.sharpsharp.vendingmachine;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VendingMachineTest {

  @Test
  public void addColaToOutputTray() {
    VendingMachine machine = new VendingMachine(null);

    machine.selectDrink(Drink.COLA);

    assertEquals(1, machine.outputTray().size());
  }
}