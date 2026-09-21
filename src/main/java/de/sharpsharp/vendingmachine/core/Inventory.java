package de.sharpsharp.vendingmachine.core;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * The slots with their cans. Part of the core: the machine owns its stock, and nothing
 * outside needs to know it. There is no refilling – a restart fills the slots up again.
 */
public final class Inventory {

    /** Every slot starts with this many cans. */
    public static final int CANS_PER_SLOT = 5;

    private final Map<Drink, Integer> cansPerSlot = new EnumMap<>(Drink.class);

    /** Every slot full – how the machine starts. */
    public static Inventory full() {
        return new Inventory(CANS_PER_SLOT);
    }

    private Inventory(int cansPerSlot) {
        for (Drink drink : Drink.values()) {
            this.cansPerSlot.put(drink, cansPerSlot);
        }
    }

    /** How many cans are left in the slot of this drink. */
    public int stock(Drink drink) {
        return cansPerSlot.get(drink);
    }

    /**
     * Takes one can out of the slot.
     *
     * @throws IllegalStateException if the slot is empty
     */
    public void removeOne(Drink drink) {
        int cans = stock(drink);
        if (cans == 0) {
            throw new IllegalStateException("The slot of " + drink + " is empty");
        }
        cansPerSlot.put(drink, cans - 1);
    }

    /** One slot per drink, in their order on the front, numbered from 1. */
    List<Slot> slots() {
        List<Slot> slots = new ArrayList<>();
        for (Drink drink : Drink.values()) {
            slots.add(new Slot(slots.size() + 1, drink, stock(drink)));
        }
        return List.copyOf(slots);
    }
}
