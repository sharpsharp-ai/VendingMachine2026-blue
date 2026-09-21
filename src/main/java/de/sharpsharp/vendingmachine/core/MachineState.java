package de.sharpsharp.vendingmachine.core;

import java.util.List;

/**
 * Everything a housing needs to show the customer: a read-only snapshot of the machine.
 *
 * @param credit     the money paid in and not yet spent
 * @param message    what the display says
 * @param slots      the slots in their order on the front, with the cans left in each
 * @param outputTray the cans waiting to be taken out
 * @param coinReturn the coins waiting to be taken out
 */
public record MachineState(
        Money credit,
        Message message,
        List<Slot> slots,
        List<Drink> outputTray,
        List<Coin> coinReturn) {
}
