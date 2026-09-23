package de.sharpsharp.vendingmachine;

import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertTrue;

/**
 * Step definitions for the feature files. The scenarios talk to the machine directly, never through the web page or HTTP. Cucumber creates a new instance of this class for every scenario, so every scenario starts with a freshly built machine.
 */
public class VendingMachineSteps {

  public static final int CENT_TO_EURO_MULTIPLICATOR = 100;
  private final FakeClock clock = new FakeClock();
  private final VendingMachine machine = new VendingMachine(clock);

  @Angenommen("der Automat ist frisch gestartet")
  public void theMachineIsFreshlyStarted() {
    // Nothing to do: Cucumber builds this class, and with it the machine, fresh for every scenario.
  }

  @Angenommen("der Automat ist frisch gestartet und hat ein Guthaben von {int} Euro")
  public void theMachineIsFreshlyStarted(Integer euro) {
    machine.insertCoin(euro * CENT_TO_EURO_MULTIPLICATOR);
  }

  @Wenn("ich {drink} wähle")
  public void iSelect(Drink drink) {
    machine.selectDrink(drink);
  }

  @Wenn("ich {int} Euro einwerfe")
  public void insertMoney(Integer euro) {
    machine.insertCoin(euro * CENT_TO_EURO_MULTIPLICATOR);
  }

  @Dann("liegt eine Dose {drink} im Ausgabefach")
  public void oneCanLiesInTheOutputTray(Drink drink) {
    assertThat(machine.outputTray(), contains(drink));
  }

  @Dann("Preis von {drink} ist {int} Euro")
  public void getPriceFromDrink(Drink drink, Integer price) {
    assertThat(machine.price(drink), is(price * CENT_TO_EURO_MULTIPLICATOR));
  }

  @Dann("ist das Guthaben {int} Euro")
  public void getPriceFromGuthaben(Integer price) {
    assertThat(machine.credit(), is(price * CENT_TO_EURO_MULTIPLICATOR));
  }

  @Dann("liegt keine Dose im Ausgabefach")
  public void noDrinkInTray() {
    assertTrue(machine.outputTray().isEmpty());
  }

  @Dann("der Automat meldet {string}")
  public void displayZeigt(String string) {
    assertThat(machine.message(), is(string));
  }
}
