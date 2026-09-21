package de.sharpsharp.vendingmachine.smoke;

import de.sharpsharp.vendingmachine.Main;
import de.sharpsharp.vendingmachine.VendingMachine;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.de.Angenommen;
import io.cucumber.java.de.Dann;
import io.cucumber.java.de.Wenn;
import io.javalin.Javalin;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Step definitions of the browser smoke test.
 * <p>
 * Nothing is replaced: the machine sits in its web server exactly as in production ({@link Main#web}),
 * a real Chrome loads its page and clicks like a customer. The steps speak the language of
 * the page – coins, slots, trays; the {@link MachinePage} knows the HTML behind it.
 * <p>
 * The hooks are bound to the tag {@code @browser}, so they stay quiet when Cucumber's own
 * runner loads these steps next to the acceptance steps (see cucumber.properties).
 * {@code -Dsmoke.headed=true} shows the browser window (default: headless).
 */
public class WebSmokeSteps {

    private static Javalin web;
    private static String url;

    private WebDriver browser;
    private MachinePage page;

    /** Starts the machine with the first browser scenario; @BeforeAll cannot be bound to a tag. */
    @Before("@browser")
    public void startTheMachineAndTheBrowser() {
        if (web == null) {
            web = Main.web(new VendingMachine()).start(0);
            url = "http://localhost:" + web.port() + "/";
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1000,900");
        // Chrome refuses to run as root without a sandbox, and CI containers run as root; their /dev/shm is tiny.
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        if (!Boolean.getBoolean("smoke.headed")) {
            options.addArguments("--headless=new");
        }
        browser = new ChromeDriver(options);
        page = new MachinePage(browser);
    }

    @After("@browser")
    public void closeTheBrowser(Scenario scenario) {
        if (browser == null) {
            return; // Chrome did not even start; the exception already says why
        }
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) browser).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Bildschirm beim Fehlschlag");
        }
        browser.quit();
    }

    @AfterAll
    public static void stopTheMachine() {
        if (web != null) {
            web.stop();
        }
    }

    // ---- Angenommen / Wenn ------------------------------------------------

    @Angenommen("ich stehe vor dem Automaten")
    public void iStandInFrontOfTheMachine() {
        page.open(url);
    }

    @Wenn("ich die Münze {string} einwerfe")
    public void iInsert(String coin) {
        page.insert(coin);
    }

    @Wenn("ich das Fach {string} drücke")
    public void iSelect(String drink) {
        page.select(drink);
    }

    @Wenn("ich auf Abbruch drücke")
    public void iPressCancel() {
        page.cancel();
    }

    @Wenn("ich auf das Ausgabefach klicke")
    public void iClickTheOutputTray() {
        page.emptyOutputTray();
    }

    @Wenn("ich auf die Münzrückgabe klicke")
    public void iClickTheCoinReturn() {
        page.emptyCoinReturn();
    }

    // ---- Dann -------------------------------------------------------------

    @Dann("zeigt das Display {string}")
    @Dann("das Display zeigt {string}")
    public void theDisplayShows(String text) {
        page.expectDisplay(text);
    }

    @Dann("der Automat zeigt {int} Fächer")
    public void theMachineShowsSlots(int slots) {
        page.expectSlots(slots);
    }

    @Dann("im Ausgabefach liegt eine {string}")
    public void aCanLiesInTheOutputTray(String drink) {
        page.expectCanInOutputTray(drink);
    }

    @Dann("in der Münzrückgabe liegt {string}")
    @Dann("liegt {string} in der Münzrückgabe")
    public void aCoinLiesInTheCoinReturn(String coin) {
        page.expectCoinInCoinReturn(coin);
    }

    @Dann("liegt nichts mehr im Ausgabefach")
    public void nothingIsLeftInTheOutputTray() {
        page.expectEmptyOutputTray();
    }

    @Dann("liegt nichts mehr in der Münzrückgabe")
    public void nothingIsLeftInTheCoinReturn() {
        page.expectEmptyCoinReturn();
    }
}
