package de.sharpsharp.vendingmachine.adapter.in.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.sharpsharp.vendingmachine.adapter.out.dispenser.SimulatedCanDispenser;
import de.sharpsharp.vendingmachine.adapter.out.inmemory.InMemoryCashBox;
import de.sharpsharp.vendingmachine.core.Inventory;
import de.sharpsharp.vendingmachine.core.VendingMachines;
import de.sharpsharp.vendingmachine.core.port.in.VendingMachineControls;
import de.sharpsharp.vendingmachine.core.port.out.Clock;
import io.javalin.testtools.JavalinTest;
import io.javalin.testtools.Response;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

/**
 * Starts the real web adapter on a free port and talks HTTP to it,
 * with a real machine and in-memory adapters behind it.
 */
public class WebAdapterTest {

    private static final ObjectMapper JSON = new ObjectMapper();

    private WebAdapter webAdapter;

    @Before
    public void setUp() {
        Clock lateAfternoon = () -> LocalTime.of(17, 0);
        VendingMachineControls machine = VendingMachines.create(Inventory.full(), new InMemoryCashBox(5), lateAfternoon, new SimulatedCanDispenser());
        webAdapter = new WebAdapter(machine);
    }

    @Test
    public void servesTheStartPage() {
        JavalinTest.test(webAdapter.javalin(), (server, client) -> {
            Response response = client.get("/");

            assertThat(response.code(), is(200));
            assertThat(response.body().string(), containsString("<title>Getränkeautomat</title>"));
        });
    }

    @Test
    public void reportsTheFreshMachine() {
        JavalinTest.test(webAdapter.javalin(), (server, client) -> {
            JsonNode state = json(client.get("/api/state"));

            assertThat(state.get("credit").asText(), is("0,00 €"));
            assertThat(state.get("message").asText(), is("Bitte Münzen einwerfen"));
            assertThat(state.get("messageCode").asText(), is("INSERT_COINS"));
            assertThat(state.get("slots").size(), is(4));
            assertThat(state.get("slots").get(0).get("position").asInt(), is(1));
            assertThat(state.get("slots").get(3).get("position").asInt(), is(4));
            assertThat(state.get("slots").get(0).get("name").asText(), is("Cola"));
            assertThat(state.get("slots").get(0).get("price").asText(), is("1,00 €"));
            assertThat(state.get("slots").get(0).get("stock").asInt(), is(5));
        });
    }

    @Test
    public void everyActionAnswersWithTheState() {
        JavalinTest.test(webAdapter.javalin(), (server, client) -> {
            for (String action : new String[]{"/api/insert/TWO_EURO", "/api/select/COLA", "/api/cancel", "/api/take-drinks", "/api/take-coins"}) {
                JsonNode state = json(client.post(action));

                assertThat(action, state.get("message").asText(), is("Bitte Münzen einwerfen"));
                assertThat(action, state.get("slots").size(), is(4));
            }
        });
    }

    @Test
    public void rejectsUnknownCoinsAndDrinksAsBadRequest() {
        JavalinTest.test(webAdapter.javalin(), (server, client) -> {
            Response unknownCoin = client.post("/api/insert/FIVE_EURO");
            Response unknownDrink = client.post("/api/select/WATER");

            assertThat(unknownCoin.code(), is(400));
            assertThat(unknownCoin.body().string(), containsString("Unknown coin: FIVE_EURO"));
            assertThat(unknownDrink.code(), is(400));
        });
    }

    private static JsonNode json(Response response) throws Exception {
        assertThat(response.code(), is(200));
        return JSON.readTree(response.body().string());
    }
}
