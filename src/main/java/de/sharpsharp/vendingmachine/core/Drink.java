package de.sharpsharp.vendingmachine.core;

/** The four slots of the machine – one drink per slot. */
public enum Drink {
    COLA("Cola", 100, false),
    ORANGE("Orange", 100, false),
    LEMON("Zitrone", 100, false),
    BEER("Bier", 200, true);

    private final String displayName;
    private final Money price;
    private final boolean alcoholic;

    Drink(String displayName, int priceInCents, boolean alcoholic) {
        this.displayName = displayName;
        this.price = Money.ofCents(priceInCents);
        this.alcoholic = alcoholic;
    }

    /** The German name shown on the slot, e.g. "Zitrone". */
    public String displayName() {
        return displayName;
    }

    public Money price() {
        return price;
    }

    /** Alcoholic drinks are only sold in the afternoon. */
    public boolean isAlcoholic() {
        return alcoholic;
    }
}
