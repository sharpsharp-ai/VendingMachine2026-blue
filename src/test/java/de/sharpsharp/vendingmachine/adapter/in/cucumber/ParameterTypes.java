package de.sharpsharp.vendingmachine.adapter.in.cucumber;

import de.sharpsharp.vendingmachine.core.Coin;
import de.sharpsharp.vendingmachine.core.Drink;
import de.sharpsharp.vendingmachine.core.Money;
import io.cucumber.java.ParameterType;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Translates the German words in the feature files into domain objects:
 * "50 ct" becomes a Coin, "Zitrone" a Drink, "1,50 €" a Money, "9:30" a LocalTime.
 */
public class ParameterTypes {

    @ParameterType("50 ct|1 €|2 €")
    public Coin coin(String label) {
        return coinWithLabel(label);
    }

    /** One or more coins: "1 €", "50 ct und 1 €", "50 ct, 50 ct und 1 €". */
    @ParameterType("(?:50 ct|1 €|2 €)(?:(?:, | und )(?:50 ct|1 €|2 €))*")
    public List<Coin> coins(String text) {
        List<Coin> coins = new ArrayList<>();
        for (String label : text.split(", | und ")) {
            coins.add(coinWithLabel(label));
        }
        return coins;
    }

    @ParameterType("Cola|Orange|Zitrone|Bier")
    public Drink drink(String name) {
        return drinkNamed(name);
    }

    @ParameterType("\\d+,\\d{2} €")
    public Money money(String text) {
        return moneyFrom(text);
    }

    @ParameterType("\\d{1,2}:\\d{2}")
    public LocalTime time(String text) {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern("H:mm"));
    }

    static Coin coinWithLabel(String label) {
        for (Coin coin : Coin.values()) {
            if (coin.label().equals(label)) {
                return coin;
            }
        }
        throw new IllegalArgumentException("Unknown coin: " + label);
    }

    static Drink drinkNamed(String name) {
        for (Drink drink : Drink.values()) {
            if (drink.displayName().equals(name)) {
                return drink;
            }
        }
        throw new IllegalArgumentException("Unknown drink: " + name);
    }

    /** "1,50 €" -> 150 cents */
    static Money moneyFrom(String text) {
        String[] euroAndCent = text.replace(" €", "").split(",");
        int euros = Integer.parseInt(euroAndCent[0]);
        int cents = Integer.parseInt(euroAndCent[1]);
        return Money.ofCents(euros * 100 + cents);
    }
}
