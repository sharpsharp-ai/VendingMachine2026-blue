package de.sharpsharp.vendingmachine.adapter.in.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * Runs every feature file under src/test/resources/features with the JUnit 4 runner.
 * The step definitions live in this package (the "glue").
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        // Only these steps; cucumber.properties serves Cucumber's own runner and lists both step packages
        glue = "de.sharpsharp.vendingmachine.adapter.in.cucumber",
        plugin = {"pretty", "html:target/cucumber-report.html"},
        snippets = CucumberOptions.SnippetType.CAMELCASE)
public class RunCucumberTest {
}
