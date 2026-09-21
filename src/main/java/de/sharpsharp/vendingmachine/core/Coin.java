package de.sharpsharp.vendingmachine.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** The coins the machine accepts, from the smallest to the largest. */
public enum Coin {
    FIFTY_CENT("50 ct", 50),
    ONE_EURO("1 €", 100),
    TWO_EURO("2 €", 200);

    private final String label;
    private final Money value;

    Coin(String label, int cents) {
        this.label = label;
        this.value = Money.ofCents(cents);
    }

    /** What is printed on the coin button, e.g. "50 ct". */
    public String label() {
        return label;
    }

    public Money value() {
        return value;
    }

    /** The coins from the largest down to the smallest – the order for paying out. */
    public static List<Coin> largestFirst() {
        List<Coin> coins = new ArrayList<>(List.of(values()));
        Collections.reverse(coins);
        return coins;
    }
}
