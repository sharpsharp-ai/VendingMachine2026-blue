package de.sharpsharp.vendingmachine;

import java.time.LocalTime;

/** Where the machine gets the time of day. Main hands in the system clock, the tests a clock they set themselves. */
public interface Clock {
    LocalTime now();
}
