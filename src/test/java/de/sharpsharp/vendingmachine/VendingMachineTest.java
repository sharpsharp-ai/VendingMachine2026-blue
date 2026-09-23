package de.sharpsharp.vendingmachine;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
public class VendingMachineTest {
  private VendingMachine vendingMachine;

  @Before
  public void setup() {
    FakeClock fakeClock = new FakeClock();
    vendingMachine = new VendingMachine(fakeClock);
  }

  @Test
  public void selectColaFromFilledAutomat_returnCola() {
    vendingMachine.selectDrink(Drink.COLA);
    List<Drink> drinks = vendingMachine.outputTray();
    assertThat(drinks.size(), is(1));
  }

  @Test
  public void getPriceFromBeer_returnPrice() {
    Integer price = vendingMachine.price(Drink.BEER);
    assertThat(price, is(200));
  }
}
