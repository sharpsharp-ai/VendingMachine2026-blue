package de.sharpsharp.vendingmachine;

import org.junit.Test;

import static de.sharpsharp.vendingmachine.Drink.COLA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class VendingMachineTest {

  @Test
  public void addColaToOutputTray() {
    VendingMachine machine = new VendingMachine(null);

    machine.insertCoin(COLA.getPriceInCents());
    machine.selectDrink(COLA);

    assertThat(machine.outputTray().size(), is(1));
  }

  @Test
  public void showPriceOfDrink() {
    VendingMachine machine = new VendingMachine(null);

    assertThat(machine.price(COLA), is(COLA.getPriceInCents()));
  }

  @Test
  public void moneyIsInserted_balanceIsAdjusted() {
    VendingMachine machine = new VendingMachine(null);

    machine.insertCoin(100);

    assertThat(machine.credit(), is(100));
  }

  @Test
  public void selectDrink_noCredit_showsInsufficientFunds() {
    VendingMachine machine = new VendingMachine(null);

    machine.selectDrink(COLA);

    assertThat(machine.message(), is("\"zu wenig Geld\""));
    assertThat(machine.outputTray().size(), is(0));
  }

  @Test
  public void selectDrink_enoughCredit_dispensesAndDeducts() {
    VendingMachine machine = new VendingMachine(null);

    machine.insertCoin(200);
    machine.selectDrink(COLA);

    assertThat(machine.outputTray().size(), is(1));
    assertThat(machine.credit(), is(100));
  }

}