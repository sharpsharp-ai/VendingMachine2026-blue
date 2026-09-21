package de.sharpsharp.vendingmachine.adapter.out.clock;

import de.sharpsharp.vendingmachine.core.port.out.Clock;

import java.time.LocalTime;

/** A clock the tests can set to any time of day. Starts in the late afternoon, when everything is for sale. */
public final class FakeClock implements Clock {

    private LocalTime time = LocalTime.of(17, 0);

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public LocalTime currentTime() {
        return time;
    }
}
