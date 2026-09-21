package de.sharpsharp.vendingmachine.adapter.in.web;

import de.sharpsharp.vendingmachine.core.Coin;
import de.sharpsharp.vendingmachine.core.Drink;
import de.sharpsharp.vendingmachine.core.MachineState;
import de.sharpsharp.vendingmachine.core.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 * The machine state as the web page receives it. Everything is already formatted,
 * so the page only has to show it: amounts as "1,50 €", drinks with their German names.
 *
 * @param credit      e.g. "1,50 €"
 * @param message     the text on the display, e.g. "Bitte wählen"
 * @param messageCode the name of the message, e.g. "CHOOSE_DRINK" – lets the page react to specific messages
 * @param slots       one entry per slot, in their order on the front – the page shows exactly these, however many
 * @param outputTray  the cans waiting to be taken
 * @param coinReturn  the coins waiting to be taken
 */
public record StateJson(
        String credit,
        String message,
        String messageCode,
        List<SlotJson> slots,
        List<CanJson> outputTray,
        List<CoinJson> coinReturn) {

    /** @param position the slot number as printed on the front, counting from 1 */
    public record SlotJson(int position, String drink, String name, String price, int stock) {
    }

    public record CanJson(String drink, String name) {
    }

    public record CoinJson(String coin, String label) {
    }

    public static StateJson from(MachineState state) {
        List<SlotJson> slots = new ArrayList<>();
        for (Slot slot : state.slots()) {
            Drink drink = slot.drink();
            slots.add(new SlotJson(slot.position(), drink.name(), drink.displayName(), drink.price().toString(), slot.cans()));
        }
        List<CanJson> outputTray = new ArrayList<>();
        for (Drink drink : state.outputTray()) {
            outputTray.add(new CanJson(drink.name(), drink.displayName()));
        }
        List<CoinJson> coinReturn = new ArrayList<>();
        for (Coin coin : state.coinReturn()) {
            coinReturn.add(new CoinJson(coin.name(), coin.label()));
        }
        return new StateJson(
                state.credit().toString(),
                state.message().text(),
                state.message().name(),
                slots,
                outputTray,
                coinReturn);
    }
}
