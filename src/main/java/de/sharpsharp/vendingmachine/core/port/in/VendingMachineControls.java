package de.sharpsharp.vendingmachine.core.port.in;

import de.sharpsharp.vendingmachine.core.Coin;
import de.sharpsharp.vendingmachine.core.Drink;
import de.sharpsharp.vendingmachine.core.MachineState;

import java.util.List;

/**
 * The in-port: everything a customer can do at the machine.
 * <p>
 * The port knows nothing about its housing. The web page is one housing;
 * a panel with one button per slot, a number pad or a voice control
 * could sit in front of the very same port.
 */
public interface VendingMachineControls {

    /** The customer puts a coin in. It goes into the cash box and counts towards the credit. */
    void insertCoin(Coin coin);

    /**
     * The customer picks a slot. Either the can drops into the output tray and the price
     * comes off the credit – what is left stays for the next can – or the display says
     * why not and the credit stays as it was.
     */
    void selectDrink(Drink drink);

    /** The customer gives up: the whole credit is paid out of the cash box into the coin return. */
    void cancel();

    /** The customer takes all cans out of the output tray. Returns what was taken. */
    List<Drink> emptyOutputTray();

    /** The customer takes all coins out of the coin return. Returns what was taken. */
    List<Coin> emptyCoinReturn();

    /** A read-only snapshot of what the customer sees. */
    MachineState state();
}
