package de.sharpsharp.vendingmachine;

/** The four slots of the machine, one drink per slot, in their order on the front. */
public enum Drink {
    COLA("Cola"),
    ORANGE("Orange"),
    LEMON("Zitrone"),
    BEER("Bier");

    private final String displayName;

    Drink(String displayName) {
        this.displayName = displayName;
    }

    /** The German name shown on the slot, e.g. "Zitrone". */
    public String displayName() {
        return displayName;
    }
}
