# Getränkeautomat

Startstand für das Training: ein Getränkeautomat in Java 17, gebaut nach Ports & Adapters.
Die Oberfläche ist fertig, der Automat dahinter tut noch nichts: Jeder Klick kommt an, ändert aber
nichts. Genau ein Szenario ist rot, „Freies Getränk". Alles Weitere entsteht outside-in und
testgetrieben. Code auf Englisch, Fachsprache, Oberfläche und Szenarien auf Deutsch.

## Starten

Voraussetzung: JDK 17 oder neuer, Maven, Chrome für den Rauchtest.

```bash
mvn test                       # alle Tests: Cucumber-Szenarien, Unit-Tests, Web-Adapter, Rauchtest im Browser
mvn compile exec:java          # Automat starten, dann http://localhost:7070 öffnen
mvn verify                     # Tests plus Coverage-Bericht in target/site/jacoco/index.html
mvn package                    # Fat-Jar bauen …
java -jar target/getraenkeautomat.jar   # … und starten (PORT=8080 wählt einen anderen Port)
```

Es gibt kein Nachfüllen: Ein Neustart setzt Fächer und Kasse zurück.

IntelliJ: Die Run-Konfigurationen liegen im Repo unter `.idea/runConfigurations/`. „Alle Tests" läuft alles,
die Szenarien eingeschlossen, weil `RunCucumberTest` sie als JUnit-Test kapselt. „Alle Szenarien (Cucumber direkt)"
startet nur die Feature-Dateien über Cucumbers eigenen Runner, praktisch für ein einzelnes Szenario aus dem Editor.
„Rauchtest (sichtbar)" lässt den Selenium-Test in einem sichtbaren Chrome laufen.
Ein Rechtsklick auf eine Feature-Datei oder ein Szenario nutzt ebenfalls Cucumbers Runner; der findet seine
Schritte über `cucumber.properties`.

## Stand

- `VendingMachine` ignoriert jede Aktion und meldet immer den Zustand nach dem Einschalten.
- `src/test/resources/features/freies_getraenk.feature` ist das erste Szenario und rot:
  Wer ein Fach wählt, bekommt die Dose. Die Pipeline ist rot, bis es grün ist.
- Weitere Szenarien gibt es nicht. Die Fachregeln unten sagen, was der Automat einmal können soll.

## Fachregeln

| Regel |
|---|
| Start: vier volle Fächer (Cola 1 €, Orange 1 €, Zitrone 1 €, Bier 2 €, je 5 Dosen), Kasse mit 5 Stück jeder Münze (50 ct, 1 €, 2 €) |
| Kauf: Münzen wandern sofort in die Kasse und werden Guthaben; reicht es, fällt die Dose, der Preis wird abgezogen, der Rest bleibt Guthaben |
| „Ausverkauft", wenn das Fach leer ist |
| „Zu wenig Geld", wenn das Guthaben nicht reicht |
| „Kein Wechselgeld – bitte passend zahlen", wenn das Restguthaben nach dem Kauf nicht aus der Kasse auszahlbar wäre |
| „Bitte Ausgabefach leeren" ab 3 Dosen im Fach; nach dem Leeren erscheint die vorherige Meldung wieder |
| „Kein Bier vor 4": Bier erst ab 16:00 Uhr |
| „Störung – Service: 0800 123 456": bleibt eine Dose stecken, wird der Preis nicht abgezogen, die Dose gilt als verbraucht. Die simulierte Mechanik lässt jede 7. Dose stecken |
| Abbruch zahlt das ganze Guthaben aus der Kasse aus, mit den Münzen, die sie hat; Ausgabefach und Münzrückgabe werden per Klick geleert |

Bei jeder Ablehnung bleibt das Guthaben eingeworfen. Die Meldungen stehen wörtlich in `Message`.

## Aufbau

Zwei Welten, eine Richtung: `adapter` darf `core` kennen, `core` kennt nichts außerhalb von sich.
In und out sind aus Sicht des Kerns benannt: in-Ports bietet er an, out-Ports braucht er.

```
de.sharpsharp.vendingmachine
├── Main                     Composition Root: die einzige Klasse, die core und adapter kennt
├── core                     der Automat
│   ├── VendingMachine       package-private: hier entstehen die Regeln
│   ├── VendingMachines      public: create(…) – der einzige Weg zu einer Maschine
│   ├── Inventory, Slot      public: der Bestand gehört dem Kern, kein Port
│   ├── Money, Coin, Drink, Message, MachineState    public: die Sprache der Ports
│   ├── port.in              VendingMachineControls – was ein Kunde tun kann
│   └── port.out             CashBox (Kasse), Clock (Uhr), CanDispenser (Dosenausgabe)
└── adapter                  ein Unterpaket je Stecker
    ├── in.web               WebAdapter (Javalin 7), StateJson; die Seite liegt in src/main/resources/public/
    ├── out.inmemory         InMemoryCashBox
    ├── out.dispenser        SimulatedCanDispenser: jede 7. Dose bleibt stecken; SlowCanDispenser: braucht absichtlich Zeit
    └── out.clock            SystemClock
```

Was der Compiler sichert: Außerhalb von `core` kommt niemand an die Regeln, weil `VendingMachine`
package-private ist. Was `ArchitekturTest` (ArchUnit) sichert: `core` kennt keine Adapter und keine
Technik-Bibliothek, und `adapter.in` und `adapter.out` kennen einander nicht.

Der In-Port ist gehäuseneutral: Statt der Web-Seite könnte ein Gehäuse mit
einem Knopf je Fach, ein Zahlendisplay oder eine Sprachsteuerung davorsitzen –
alle rufen dieselben sechs Methoden.

## Tests

| Ebene | Werkzeug | Wo |
|---|---|---|
| Akzeptanz: Szenarien gegen den In-Port, mit In-Memory-Kasse, stellbarer Uhr und steuerbarer Mechanik | Cucumber, JUnit-4-Runner | `adapter/in/cucumber/` |
| Unit: Geldbeträge, Bestand, Adapter | JUnit 4, Mockito, Hamcrest | `core/`, `adapter/out/` |
| Web-Adapter: echter Javalin auf freiem Port, HTTP und JSON | Javalin Testtools | `adapter/in/web/` |
| Architektur: Abhängigkeitsregeln zwischen core und adapter | ArchUnit, JUnit-4-Runner | `ArchitekturTest` |
| Rauchtest: echter Chrome lädt die Seite, Automat verdrahtet wie in Produktion | Cucumber, Selenium | `smoke/` |

Vorgehen pro Fachregel: Szenario schreiben, Unit-Test, Code, Refactoring, Commit.

## Rauchtest im Browser (Selenium)

`smoke/automat_im_browser.feature` lädt die Seite in einem echten Chrome und prüft Display und Fächer.
Der Automat läuft dabei genau wie in Produktion (`Main.start`). Das Page Object `MachinePage` wartet
auf den erwarteten Zustand, alle halbe Sekunde nachsehend, höchstens 10 Sekunden.

```bash
mvn test -Dtest=RunSmokeTest                       # kopflos, wie in „mvn test"
mvn test -Dtest=RunSmokeTest -Dsmoke.headed=true   # Browser sichtbar
```

Chrome muss installiert sein; den passenden chromedriver lädt Selenium Manager beim ersten Lauf nach
`~/.cache/selenium`. Schlägt ein Schritt fehl, hängt ein Screenshot im Bericht `target/smoke-report.html`.

## Pipeline (GitHub Actions)

`.github/workflows/ci.yml` hat drei Jobs, die parallel laufen:

| Job | Was er tut | Ergebnis |
|---|---|---|
| `build` | `mvn package` ohne Tests | Fat-Jar als Artefakt |
| `test` | alles außer dem Rauchtest, `mvn verify` | Testergebnisse als Check am Commit, JaCoCo und Cucumber-Bericht als Artefakte |
| `rauchtest` | nur `RunSmokeTest` im vorinstallierten Chrome des Runners | Testergebnisse als Check, Cucumber-Bericht mit Screenshot bei Fehlschlag |

So steht in jedem Lauf nebeneinander, was die schnellen Tests kosten und was der eine Browser-Test
kostet. Das Maven-Repository wird zwischen den Läufen aufgehoben.

## HTTP-API

Jede Aktion antwortet mit dem neuen Zustand; die Seite rendert ihn komplett neu.

```
GET  /api/state
POST /api/insert/{coin}      FIFTY_CENT | ONE_EURO | TWO_EURO
POST /api/select/{drink}     COLA | ORANGE | LEMON | BEER
POST /api/cancel
POST /api/take-drinks        Ausgabefach leeren
POST /api/take-coins         Münzrückgabe leeren
```

## Erweitern

- **Neue Regel:** Szenario schreiben, Text in `Message`, Prüfung in `VendingMachine`.
- **Neues Getränk:** Wert in `Drink` ergänzen, Dosen-Design als `<symbol id="can-NAME">` in `index.html`.
  Ohne eigenes Design erscheint die neutrale Dose. Die Seite kennt keine feste Fächerzahl: Sie zeigt
  die Fächer mit Nummer, Name, Preis und Dosen so, wie der Status sie liefert.
- **Neue Münze:** Wert in `Coin` ergänzen, Knopf in `index.html`, Farbe in `style.css`.
- **Echte Hardware:** neuen Adapter für `CashBox`, `Clock` oder `CanDispenser`
  schreiben und in `Main` einstecken. Der Kern bleibt unverändert.
