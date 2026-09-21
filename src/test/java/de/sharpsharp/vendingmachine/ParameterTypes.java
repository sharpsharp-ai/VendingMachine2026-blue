package de.sharpsharp.vendingmachine;

import io.cucumber.java.ParameterType;

/** Translates the German words in the feature files into domain objects: "Zitrone" becomes a Drink, "1,50 €" becomes 150. */
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

    /** An amount as printed on the machine, in cents: "1,50 €" is 150, "2 €" is 200, "50 ct" is 50. */
    @ParameterType("\\d+,\\d{2} €|\\d+ €|\\d+ ct")
    public int betrag(String text) {
        String[] numberAndUnit = text.split(" ");
        if (numberAndUnit[1].equals("ct")) {
            return Integer.parseInt(numberAndUnit[0]);
        }
        String[] eurosAndCents = numberAndUnit[0].split(",");
        int cents = Integer.parseInt(eurosAndCents[0]) * 100;
        if (eurosAndCents.length == 2) {
            cents += Integer.parseInt(eurosAndCents[1]);
        }
        return cents;
    }
}
