package de.sharpsharp.vendingmachine;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.runner.RunWith;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * The dependency rules of the architecture, checked on the compiled main classes.
 * <p>
 * The compiler already keeps everyone outside the core away from the rules (VendingMachine is
 * package-private). What the compiler cannot enforce, these rules do: the core must not know
 * anything outside itself and the JDK, and the adapters must not know each other.
 */
@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "de.sharpsharp.vendingmachine", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitekturTest {

    @ArchTest
    public static final ArchRule kern_kennt_nur_sich_selbst_und_das_jdk =
            classes().that().resideInAPackage("..core..")
                    .should().onlyDependOnClassesThat().resideInAnyPackage("..core..", "java..");

    /** Adapters are independent plugs. Only Main knows both sides. */
    @ArchTest
    public static final ArchRule in_adapter_kennen_keine_out_adapter =
            noClasses().that().resideInAPackage("..adapter.in..")
                    .should().dependOnClassesThat().resideInAPackage("..adapter.out..");

    @ArchTest
    public static final ArchRule out_adapter_kennen_keine_in_adapter =
            noClasses().that().resideInAPackage("..adapter.out..")
                    .should().dependOnClassesThat().resideInAPackage("..adapter.in..");
}
