# Getränkeautomat

Startstand für das Training. Die Seite ist fertig, der Automat dahinter tut noch nichts: Jeder Klick
kommt an, ändert aber nichts. Genau ein Szenario ist rot, „Freies Getränk". Alles Weitere entsteht
testgetrieben. Code auf Englisch, Fachsprache, Oberfläche und Szenarien auf Deutsch.

## Starten

Voraussetzung: JDK 17 oder neuer, Maven, Chrome für den Rauchtest.

```bash
mvn test                       # alle Tests: Cucumber-Szenarien, HTTP-Test, Rauchtest im Browser
mvn compile exec:java          # Automat starten, dann http://localhost:7070 öffnen
mvn verify                     # Tests plus Coverage-Bericht in target/site/jacoco/index.html
mvn package                    # Fat-Jar bauen …
java -jar target/getraenkeautomat.jar   # … und starten (PORT=8080 wählt einen anderen Port)
```

Es gibt kein Nachfüllen: Ein Neustart setzt den Automaten zurück.

IntelliJ: Die Run-Konfigurationen liegen im Repo unter `.idea/runConfigurations/`. „Alle Tests" läuft alles,
die Szenarien eingeschlossen, weil `RunCucumberTest` sie als JUnit-Test kapselt. „Alle Szenarien (Cucumber direkt)"
startet nur die Feature-Dateien über Cucumbers eigenen Runner, praktisch für ein einzelnes Szenario aus dem Editor.
„Rauchtest (sichtbar)" lässt den Selenium-Test in einem sichtbaren Chrome laufen.
Ein Rechtsklick auf eine Feature-Datei oder ein Szenario nutzt ebenfalls Cucumbers Runner.

## Stand

Drei Klassen in `src/main/java`:

| Klasse | Was sie tut |
|---|---|
| `VendingMachine` | ignoriert jede Aktion und meldet immer den Zustand nach dem Einschalten. Hier entstehen die Regeln |
| `Drink` | die vier Fächer mit Name und Preis, in ihrer Reihenfolge auf der Front |
| `Main` | startet Javalin, liefert die Seite aus und übersetzt zwischen HTTP, JSON und dem Automaten |

Beträge sind `int` in Cent, Meldungen sind Strings. Die Seite (`src/main/resources/public/`) formatiert
selbst und zeigt so viele Fächer, wie der Zustand liefert.

`src/test/resources/features/freies_getraenk.feature` ist das erste Szenario und rot: Wer ein Fach wählt,
bekommt die Dose. Die Pipeline ist rot, bis es grün ist. Weitere Szenarien gibt es nicht; die Fachregeln
unten sagen, was der Automat einmal können soll.

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

Bei jeder Ablehnung bleibt das Guthaben eingeworfen, und die Seite blinkt rot, wenn `refused` gesetzt ist.

## Tests

| Ebene | Werkzeug | Wo |
|---|---|---|
| Akzeptanz: Szenarien direkt gegen `VendingMachine` | Cucumber, JUnit-4-Runner | `VendingMachineSteps`, `features/` |
| HTTP: echter Javalin auf freiem Port, das JSON, das die Seite bekommt | Javalin Testtools | `WebTest` |
| Rauchtest: echter Chrome lädt die Seite, Automat verdrahtet wie in Produktion | Cucumber, Selenium | `smoke/` |

Vorgehen pro Fachregel: Szenario schreiben, Unit-Test, Code, Refactoring, Commit.

## Rauchtest im Browser (Selenium)

`smoke/automat_im_browser.feature` lädt die Seite in einem echten Chrome und prüft Display und Fächer.
Das Page Object `MachinePage` wartet auf den erwarteten Zustand, alle halbe Sekunde nachsehend,
höchstens 10 Sekunden.

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

Das Maven-Repository wird zwischen den Läufen aufgehoben.

## HTTP-API

Jede Aktion antwortet mit dem neuen Zustand; die Seite rendert ihn komplett neu.

```
GET  /api/state
POST /api/insert/{cents}     50 | 100 | 200
POST /api/select/{drink}     COLA | ORANGE | LEMON | BEER
POST /api/cancel
POST /api/take-drinks        Ausgabefach leeren
POST /api/take-coins         Münzrückgabe leeren
```

Der Zustand: `credit` in Cent, `message`, `refused`, `slots` (Nummer, Getränk, Name, Preis in Cent, Dosen),
`outputTray` (Dosen), `coinReturn` (Münzen in Cent).
