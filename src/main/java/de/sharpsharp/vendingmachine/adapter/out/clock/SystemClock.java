package de.sharpsharp.vendingmachine.adapter.out.clock;

import de.sharpsharp.vendingmachine.core.port.out.Clock;

import java.time.LocalTime;

/** The real clock: the local time of the computer the machine runs on. */
public final class SystemClock implements Clock {

    @Override
    public LocalTime currentTime() {
        return LocalTime.now();
    }
}
