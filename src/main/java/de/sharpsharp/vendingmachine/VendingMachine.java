package de.sharpsharp.vendingmachine;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * The machine. Amounts are in cents, messages are plain text. The methods are
 * synchronized because the web page may send several requests at once, while
 * there is one machine and one customer at a time.
 */
public class VendingMachine {

    public static final int CANS_PER_SLOT = 5;

    private final List<Drink> drinksInOutputTray = new ArrayList<>();
    private final List<Integer> coinsInserted = new ArrayList<>();
    private final List<Integer> coinReturnInternal = new ArrayList<>();
    private final Map<Drink, Integer> stock = new EnumMap<>(Drink.class);
    private String message = "Bitte Münzen einwerfen";

    /** The time of day, for rules that depend on it. Never read the system time directly: ask the clock. */
    private final Clock clock;

    public VendingMachine(Clock clock) {
        this.clock = clock;
        for (Drink drink : Drink.values()) {
            stock.put(drink, CANS_PER_SLOT);
        }
    }

    // ---- What a customer can do --------------------------------------------

    public synchronized void insertCoin(int cents) {
        coinsInserted.add(cents);
    }

    public synchronized void selectDrink(Drink drink) {
        int price = drink.price();
        if (currentCredit() < price) {
            message = "Zu wenig Geld";
            return;
        }

        Integer remainingDrinks = stock.get(drink);
        if (remainingDrinks == 0) {
            return;
        }

        deduct(price);
        drinksInOutputTray.add(drink);
        stock.put(drink, remainingDrinks - 1);
    }

    public synchronized void cancel() {
        if (!coinsInserted.isEmpty()) {
            coinReturnInternal.addAll(coinsInserted);
            coinsInserted.clear();
            message = "Bitte Wechselgeld nehmen";
        }
    }

    /** Empties the output tray and returns the cans that were in it. */
    public synchronized List<Drink> takeDrinks() {
        List<Drink> drinks = List.copyOf(drinksInOutputTray);
        drinksInOutputTray.clear();
        return drinks;
    }

    /** Empties the coin return and returns the coins that were in it, in cents. */
    public synchronized List<Integer> takeCoins() {
        List<Integer> coins = List.copyOf(coinReturnInternal);
        coinReturnInternal.clear();
        return coins;
    }

    // ---- What the machine shows ---------------------------------------------

    /** Sum of all coins still in the machine, in cents. */
    public synchronized int credit() {
        return currentCredit();
    }

    public synchronized String message() {
        return message;
    }

    /** True while the display shows a refusal such as "Ausverkauft"; the page then flashes it red. */
    public synchronized boolean refused() {
        return false;
    }

    public synchronized int stock(Drink drink) {
        return stock.get(drink);
    }

    /** The price shown behind the name of the drink, in cents. Null: the machine knows no price yet. */
    public synchronized Integer price(Drink drink) {
        return drink.price();
    }

    /** The cans that dropped out and have not been taken yet. */
    public synchronized List<Drink> outputTray() {
        return drinksInOutputTray;
    }

    /** The coins that came back and have not been taken yet, in cents. */
    public synchronized List<Integer> coinReturn() {
        return coinReturnInternal;
    }

    // ---- Internal helpers ---------------------------------------------------

    private int currentCredit() {
        return coinsInserted.stream().mapToInt(Integer::intValue).sum();
    }

    private void deduct(int amount) {
        while (amount > 0 && !coinsInserted.isEmpty()) {
            int coin = coinsInserted.remove(0);
            if (coin > amount) {
                coinsInserted.add(0, coin - amount);
                amount = 0;
            } else {
                amount -= coin;
            }
        }
    }
}
