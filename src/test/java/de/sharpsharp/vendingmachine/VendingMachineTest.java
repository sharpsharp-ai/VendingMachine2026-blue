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
  public void getPriceFromBeer_returnPrice() {
    Integer price = vendingMachine.price(Drink.BEER);
    assertThat(price, is(200));
  }

  @Test
  public void freshMachineHasZeroCredit() {
    assertThat(vendingMachine.credit(), is(0));
  }

  @Test
  public void insert100CentsIncreasesCreditBy100() {
    vendingMachine.insertCoin(100);
    assertThat(vendingMachine.credit(), is(100));
  }

  @Test
  public void insert2EuroIncreasesCreditTo200Cents() {
    vendingMachine.insertCoin(200);
    assertThat(vendingMachine.credit(), is(200));
  }

  @Test
  public void insertMultipleCoinsSumsCredit() {
    vendingMachine.insertCoin(100);
    vendingMachine.insertCoin(200);
    vendingMachine.insertCoin(50);
    assertThat(vendingMachine.credit(), is(350));
  }

  @Test
  public void selectColaWithEnoughCreditDeductsPrice() {
    vendingMachine.insertCoin(200);
    vendingMachine.selectDrink(Drink.COLA);
    assertThat(vendingMachine.credit(), is(100));
  }

  @Test
  public void selectColaWithNoCreditShowsTooLittleMoney() {
    vendingMachine.selectDrink(Drink.COLA);
    assertThat(vendingMachine.message(), is("Zu wenig Geld"));
  }
}
