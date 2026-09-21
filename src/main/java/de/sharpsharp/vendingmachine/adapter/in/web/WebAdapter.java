package de.sharpsharp.vendingmachine.adapter.in.web;

import de.sharpsharp.vendingmachine.core.Coin;
import de.sharpsharp.vendingmachine.core.Drink;
import de.sharpsharp.vendingmachine.core.port.in.VendingMachineControls;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.staticfiles.Location;

import java.util.Map;

/**
 * In-adapter: the web housing of the machine.
 * <p>
 * Serves the static page from {@code src/main/resources/public} and offers a small
 * HTTP API. Every action returns the new state, so the page simply re-renders it:
 * <pre>
 *   GET  /api/state
 *   POST /api/insert/{coin}     coin  = FIFTY_CENT | ONE_EURO | TWO_EURO
 *   POST /api/select/{drink}    drink = COLA | ORANGE | LEMON | BEER
 *   POST /api/cancel
 *   POST /api/take-drinks       empties the output tray
 *   POST /api/take-coins        empties the coin return
 * </pre>
 */
public final class WebAdapter {

    private final VendingMachineControls machine;
    private final Javalin javalin;

    public WebAdapter(VendingMachineControls machine) {
        this.machine = machine;
        this.javalin = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);

            config.routes.get("/api/state", ctx -> reply(ctx));
            config.routes.post("/api/insert/{coin}", ctx -> act(ctx, () -> machine.insertCoin(fromPath(ctx, "coin", Coin.class))));
            config.routes.post("/api/select/{drink}", ctx -> act(ctx, () -> machine.selectDrink(fromPath(ctx, "drink", Drink.class))));
            config.routes.post("/api/cancel", ctx -> act(ctx, machine::cancel));
            config.routes.post("/api/take-drinks", ctx -> act(ctx, machine::emptyOutputTray));
            config.routes.post("/api/take-coins", ctx -> act(ctx, machine::emptyCoinReturn));

            // An unknown coin or drink in the URL is the caller's mistake, not a server error.
            config.routes.exception(IllegalArgumentException.class, (e, ctx) ->
                    ctx.status(400).json(Map.of("error", e.getMessage())));
        });
    }

    public void start(int port) {
        javalin.start(port);
    }

    public void stop() {
        javalin.stop();
    }

    /** The port the housing listens on; known once started, so port 0 ("any free one") can be looked up. */
    public int port() {
        return javalin.port();
    }

    /** The configured server, e.g. for tests that start it on a random port. */
    public Javalin javalin() {
        return javalin;
    }

    /** Every action works the same way: do it, then send the new state. */
    private void act(Context ctx, Runnable action) {
        action.run();
        reply(ctx);
    }

    private void reply(Context ctx) {
        ctx.json(StateJson.from(machine.state()));
    }

    /** Reads a path parameter such as {coin} and turns it into the enum value of the same name. */
    private static <E extends Enum<E>> E fromPath(Context ctx, String parameter, Class<E> type) {
        String name = ctx.pathParam(parameter);
        try {
            return Enum.valueOf(type, name);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown " + parameter + ": " + name);
        }
    }
}
