package de.sharpsharp.vendingmachine;

/**
 * The four slots of the machine, one drink per slot, in their order on the front.
 */
public enum Drink {
  COLA("Cola", 1),
  ORANGE("Orange", 1),
  LEMON("Zitrone", 1),
  BEER("Bier", 1);

  private final String displayName;
  private final Integer price;

  Drink(String displayName, Integer price) {
    this.displayName = displayName;
    this.price = price;
  }

  /**
   * The German name shown on the slot, e.g. "Zitrone".
   */
  public String displayName() {
    return displayName;
  }

  public Integer getPrice() {
    return price;
  }
}
