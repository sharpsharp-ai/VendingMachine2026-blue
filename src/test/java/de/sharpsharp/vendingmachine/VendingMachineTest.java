package de.sharpsharp.vendingmachine;

import org.junit.Test;

import static de.sharpsharp.vendingmachine.Drink.BEER;
import static de.sharpsharp.vendingmachine.Drink.COLA;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalTime;

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

    assertThat(machine.message(), is("zu wenig Geld"));
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

  @Test
  public void selectBeerBefore16_showsNoBeerMessageAndKeepsCredit() {
    FakeClock clock = new FakeClock();
    clock.set(LocalTime.of(15, 59));
    VendingMachine machine = new VendingMachine(clock);

    machine.insertCoin(100);
    machine.selectDrink(BEER);

    assertThat(machine.message(), is("Kein Bier vor 4"));
    assertThat(machine.credit(), is(100));
    assertThat(machine.outputTray().size(), is(0));
  }

  @Test
  public void selectBeerAt16_dispensesAndDeducts() {
    FakeClock clock = new FakeClock();
    clock.set(LocalTime.of(16, 0));
    VendingMachine machine = new VendingMachine(clock);

    machine.insertCoin(200);
    machine.selectDrink(BEER);

    assertThat(machine.outputTray().size(), is(1));
    assertThat(machine.credit(), is(100));
  }

}