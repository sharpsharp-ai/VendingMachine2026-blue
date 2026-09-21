package de.sharpsharp.vendingmachine.adapter.out.inmemory;

import de.sharpsharp.vendingmachine.core.Coin;
import de.sharpsharp.vendingmachine.core.port.out.CashBox;

import java.util.EnumMap;
import java.util.Map;

/** The cash box, kept in memory: a restart refills it. */
public final class InMemoryCashBox implements CashBox {

    /** The machine starts with this many coins of every kind, so it can pay out credit on cancel. */
    public static final int COINS_PER_KIND_AT_START = 5;

    private final Map<Coin, Integer> coinsPerKind = new EnumMap<>(Coin.class);

    /** The cash box as the machine starts with it. */
    public static InMemoryCashBox withStartingChange() {
        return new InMemoryCashBox(COINS_PER_KIND_AT_START);
    }

    /** Starts with the same number of coins of every kind. */
    public InMemoryCashBox(int coinsPerKind) {
        for (Coin coin : Coin.values()) {
            this.coinsPerKind.put(coin, coinsPerKind);
        }
    }

    @Override
    public int count(Coin coin) {
        return coinsPerKind.get(coin);
    }

    @Override
    public void add(Coin coin) {
        coinsPerKind.put(coin, count(coin) + 1);
    }

    @Override
    public void remove(Coin coin) {
        int coins = count(coin);
        if (coins == 0) {
            throw new IllegalStateException("No " + coin + " left in the cash box");
        }
        coinsPerKind.put(coin, coins - 1);
    }
}
