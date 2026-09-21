package de.sharpsharp.vendingmachine.smoke;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runs the smoke scenario under src/test/resources/smoke with a real browser.
 * <p>
 * The top of the test pyramid: one scenario, the whole stack, a real Chrome – and by far the
 * slowest test in this project. The glue names only this package, so a JUnit run loads no other
 * steps; cucumber.properties serves Cucumber's own runner and lists both step packages.
 * <p>
 * Watch it: {@code mvn test -Dtest=RunSmokeTest -Dsmoke.headed=true}
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:smoke",
        glue = "de.sharpsharp.vendingmachine.smoke",
        plugin = {"pretty", "html:target/smoke-report.html"},
        snippets = CucumberOptions.SnippetType.CAMELCASE)
public class RunSmokeTest {
}
