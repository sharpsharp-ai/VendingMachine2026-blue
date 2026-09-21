package de.sharpsharp.vendingmachine.core.port.out;

import de.sharpsharp.vendingmachine.core.Coin;

/** Out-port: the cash box that keeps the coins, sorted by kind. */
public interface CashBox {

    /** How many coins of this kind are in the box. */
    int count(Coin coin);

    void add(Coin coin);

    /**
     * Takes one coin of this kind out of the box.
     *
     * @throws IllegalStateException if there is none left
     */
    void remove(Coin coin);
}
