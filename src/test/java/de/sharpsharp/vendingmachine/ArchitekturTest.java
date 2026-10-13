package de.sharpsharp.vendingmachine;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Gate: the business logic knows nothing about the web. Only Main talks to Javalin and JSON,
 * and nothing depends on Main. Runs as part of {@code mvn -q verify}.
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "de.sharpsharp.vendingmachine", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitekturTest {

    @ArchTest
    static final ArchRule onlyMainUsesTheWeb =
            noClasses().that().haveNameNotMatching(".*\\.Main(\\$.*)?")
                    .should().dependOnClassesThat().resideInAnyPackage("io.javalin..", "com.fasterxml.jackson..")
                    .because("Fachlogik kennt keine Web-Technik: nur Main spricht HTTP und JSON");

    @ArchTest
    static final ArchRule nothingDependsOnMain =
            noClasses().that().haveNameNotMatching(".*\\.Main(\\$.*)?")
                    .should().dependOnClassesThat().haveNameMatching(".*\\.Main(\\$.*)?")
                    .because("Main ist nur der Einstieg, keine Klasse darf davon abhängen");
}
