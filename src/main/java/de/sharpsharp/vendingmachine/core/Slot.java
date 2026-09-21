package de.sharpsharp.vendingmachine.core;

/**
 * One slot on the front of the machine: where it is, what it holds, how many cans are left.
 *
 * @param position the slot number as printed on the front, counting from 1
 * @param drink    the drink in this slot; it brings name and price along
 * @param cans     how many cans are left
 */
public record Slot(int position, Drink drink, int cans) {
}
