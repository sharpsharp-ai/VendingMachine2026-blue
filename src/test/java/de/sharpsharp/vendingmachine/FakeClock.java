package de.sharpsharp.vendingmachine;

import java.time.LocalTime;

/** A clock the tests set by hand. It starts at 17:00, so scenarios that name no time run in the afternoon. */
public class FakeClock implements Clock {

    private LocalTime time = LocalTime.of(17, 0);

    public void set(LocalTime time) {
        this.time = time;
    }

    @Override
    public LocalTime now() {
        return time;
    }
}
