package de.sharpsharp.vendingmachine.core.port.out;

import java.time.LocalTime;

/** Out-port: the clock of the machine. Tests can turn it to any time of day. */
public interface Clock {

    LocalTime currentTime();
}
