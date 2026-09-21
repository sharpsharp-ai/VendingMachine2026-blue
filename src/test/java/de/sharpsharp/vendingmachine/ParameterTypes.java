package de.sharpsharp.vendingmachine;

import io.cucumber.java.ParameterType;

/** Translates the German words in the feature files into domain objects: "Zitrone" becomes a Drink. */
public class ParameterTypes {

    @ParameterType("Cola|Orange|Zitrone|Bier")
    public Drink drink(String name) {
        for (Drink drink : Drink.values()) {
            if (drink.displayName().equals(name)) {
                return drink;
            }
        }
        throw new IllegalArgumentException("Unknown drink: " + name);
    }
}
