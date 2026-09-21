package de.sharpsharp.vendingmachine.smoke;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;

/**
 * Page object of the machine's web page: the steps talk about coins, slots and trays,
 * this class knows the HTML behind them.
 * <p>
 * The page re-renders after every click – as soon as the server has answered, and the server
 * is only as fast as the mechanics behind it. So every expectation here polls until the state
 * shows, every half second, for at most {@link #PATIENCE}. A fast machine costs no waiting at
 * all, a slow one costs exactly as long as it needs, and only a broken one runs into the timeout.
 */
class MachinePage {

    static final Duration PATIENCE = Duration.ofSeconds(10);

    static final By DISPLAY = By.id("display");
    static final By OUTPUT_TRAY = By.id("output-tray");
    static final By COIN_RETURN = By.id("coin-return");
    static final By CANCEL = By.id("cancel");
    static final By COIN_RETURN_CONTENT = By.id("coin-return-content");
    static final By SLOTS = By.cssSelector("button.slot");
    static final By CANS_IN_OUTPUT_TRAY = By.cssSelector("#output-tray-content .can");
    static final By COINS_IN_COIN_RETURN = By.cssSelector("#coin-return-content .chip");

    final WebDriver browser;
    private final WebDriverWait wait;

    MachinePage(WebDriver browser) {
        this.browser = browser;
        this.wait = new WebDriverWait(browser, PATIENCE);
    }

    void open(String url) {
        browser.get(url);
    }

    // ---- Actions ----------------------------------------------------------

    /** Clicks the coin button with this label, e.g. "2 €". */
    void insert(String coinLabel) {
        click(By.xpath("//button[@data-coin and normalize-space(.)='" + coinLabel + "']"));
    }

    /** Clicks the slot with this name, e.g. "Cola". Slots are rebuilt on every render. */
    void select(String drinkName) {
        click(By.xpath("//button[contains(@class,'slot') and span[@class='slot-name' and normalize-space(.)='"
                + drinkName + "']]"));
    }

    void cancel() {
        click(CANCEL);
    }

    void emptyOutputTray() {
        click(OUTPUT_TRAY);
    }

    void emptyCoinReturn() {
        click(COIN_RETURN);
    }

    /** The button must exist and be clickable; the slots only appear with the first state. */
    void click(By locator) {
        wait.until(elementToBeClickable(locator)).click();
    }

    // ---- Expectations: poll until the state shows -------------------------

    void expectDisplay(String text) {
        wait.until(textToBePresentInElementLocated(DISPLAY, text));
    }

    void expectSlots(int count) {
        wait.until(numberOfElementsToBe(SLOTS, count));
    }

    void expectCanInOutputTray(String drinkName) {
        wait.until(presenceOfElementLocated(By.cssSelector("#output-tray-content .can[aria-label='" + drinkName + "']")));
    }

    void expectCoinInCoinReturn(String coinLabel) {
        wait.until(textToBePresentInElementLocated(COIN_RETURN_CONTENT, coinLabel));
    }

    void expectEmptyOutputTray() {
        wait.until(numberOfElementsToBe(CANS_IN_OUTPUT_TRAY, 0));
    }

    void expectEmptyCoinReturn() {
        wait.until(numberOfElementsToBe(COINS_IN_COIN_RETURN, 0));
    }
}
