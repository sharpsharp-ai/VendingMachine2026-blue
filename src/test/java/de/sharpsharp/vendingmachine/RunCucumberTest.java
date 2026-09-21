package de.sharpsharp.vendingmachine;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runs every feature file under src/test/resources/features with the JUnit 4 runner.
 * The step definitions live in this package (the "glue"); the browser hooks in the
 * smoke subpackage only fire for scenarios tagged @browser.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "de.sharpsharp.vendingmachine",
        plugin = {"pretty", "html:target/cucumber-report.html"},
        snippets = CucumberOptions.SnippetType.CAMELCASE)
public class RunCucumberTest {
}
