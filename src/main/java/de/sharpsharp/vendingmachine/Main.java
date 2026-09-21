package de.sharpsharp.vendingmachine;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Starts the machine and its web page.
 * <p>
 * Serves the page from {@code src/main/resources/public} and the small HTTP API the page talks to.
 * Every action answers with the new state, so the page simply re-renders it:
 * <pre>
 *   GET  /api/state
 *   POST /api/insert/{cents}    cents = 50 | 100 | 200
 *   POST /api/select/{drink}    drink = COLA | ORANGE | LEMON | BEER
 *   POST /api/cancel
 *   POST /api/take-drinks       empties the output tray
 *   POST /api/take-coins        empties the coin return
 * </pre>
 * The state is plain numbers and names: amounts in cents, drinks by name. Formatting is the page's job.
 */
public final class Main {

    static final int DEFAULT_PORT = 7070;

    private Main() {
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", String.valueOf(DEFAULT_PORT)));
        Javalin web = web(new VendingMachine()).start(port);
        System.out.println("Getränkeautomat läuft auf http://localhost:" + web.port());
    }

    /** The web server for this machine, not started yet. Port 0 at start lets the operating system pick a free one. */
    public static Javalin web(VendingMachine machine) {
        return Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);

            config.routes.get("/api/state", ctx -> ctx.json(state(machine)));
            config.routes.post("/api/insert/{cents}", ctx -> act(ctx, machine, () -> machine.insertCoin(cents(ctx))));
            config.routes.post("/api/select/{drink}", ctx -> act(ctx, machine, () -> machine.selectDrink(drink(ctx))));
            config.routes.post("/api/cancel", ctx -> act(ctx, machine, machine::cancel));
            config.routes.post("/api/take-drinks", ctx -> act(ctx, machine, machine::takeDrinks));
            config.routes.post("/api/take-coins", ctx -> act(ctx, machine, machine::takeCoins));

            // A coin or drink the URL does not know is the caller's mistake, not a server error.
            config.routes.exception(IllegalArgumentException.class, (e, ctx) ->
                    ctx.status(400).json(Map.of("error", e.getMessage())));
        });
    }

    /** Every action works the same way: do it, then answer with the new state. */
    private static void act(Context ctx, VendingMachine machine, Runnable action) {
        action.run();
        ctx.json(state(machine));
    }

    private static int cents(Context ctx) {
        String cents = ctx.pathParam("cents");
        try {
            return Integer.parseInt(cents);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Unknown coin: " + cents);
        }
    }

    private static Drink drink(Context ctx) {
        String drink = ctx.pathParam("drink");
        try {
            return Drink.valueOf(drink);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown drink: " + drink);
        }
    }

    // ---- The state as the page receives it ------------------------------------

    /** @param position the slot number as printed on the front, counting from 1 */
    public record Slot(int position, String drink, String name, int price, int stock) {
    }

    public record Can(String drink, String name) {
    }

    /**
     * @param credit     in cents
     * @param message    the text on the display
     * @param refused    whether that text is a refusal; the page flashes the display red
     * @param slots      one entry per slot, in their order on the front; the page shows exactly these
     * @param outputTray the cans waiting to be taken
     * @param coinReturn the coins waiting to be taken, in cents
     */
    public record State(int credit, String message, boolean refused, List<Slot> slots, List<Can> outputTray, List<Integer> coinReturn) {
    }

    static State state(VendingMachine machine) {
        List<Slot> slots = new ArrayList<>();
        for (Drink drink : Drink.values()) {
            slots.add(new Slot(drink.ordinal() + 1, drink.name(), drink.displayName(), drink.price(), machine.stock(drink)));
        }
        List<Can> outputTray = new ArrayList<>();
        for (Drink drink : machine.outputTray()) {
            outputTray.add(new Can(drink.name(), drink.displayName()));
        }
        return new State(machine.credit(), machine.message(), machine.refused(), slots, outputTray, machine.coinReturn());
    }
}
