package de.sharpsharp.vendingmachine;

/**
 * The four slots of the machine, one drink per slot, in their order on the front.
 */
public enum Drink {
  COLA("Cola", 100),
  ORANGE("Orange", 100),
  LEMON("Zitrone", 100),
  BEER("Bier", 100);

  private final String displayName;
  private final Integer priceInCents;

  Drink(String displayName, Integer priceInCents) {
    this.displayName = displayName;
    this.priceInCents = priceInCents;
  }

  /**
   * The German name shown on the slot, e.g. "Zitrone".
   */
  public String displayName() {
    return displayName;
  }

  public Integer getPriceInCents() {
    return priceInCents;
  }
}
