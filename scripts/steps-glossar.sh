#!/bin/bash
# Erzeugt specs/glossar.md: die Fachbegriffe und alle vorhandenen Cucumber-Schritte.
# Aufruf: scripts/steps-glossar.sh   (nach jedem neuen Schritt erneut ausführen)
set -euo pipefail
cd "$(dirname "$0")/.."
STEPS=src/test/java/de/sharpsharp/vendingmachine
{
cat <<'HEAD'
# Glossar

Erzeugt von `scripts/steps-glossar.sh`. Nicht von Hand ändern.

## Fachbegriffe

Was der Kunde sieht oder tut, und wie es im Code heißt. Schritte und Regeln benutzen die deutschen Wörter,
die Schrittdefinitionen rufen die Methode in der letzten Spalte.

| Deutsch (Szenarien, Seite) | Englisch (Code) | Bedeutung | Am Automaten |
|---|---|---|---|
| Automat | VendingMachine | der Getränkeautomat | `VendingMachine` |
| Fach | slot | ein Fach mit einem Getränk, nummeriert von 1 | |
| Getränk | Drink | Cola, Orange, Zitrone, Bier | `Drink` |
| Preis | price | steht am Fach hinter dem Namen, in Cent; null, solange der Automat keinen kennt | `price(drink)` |
| Bestand, Dosen im Fach | stock | wie viele Dosen im Fach liegen | `stock(drink)` |
| Münze einwerfen | insert coin | 50 ct, 1 €, 2 €; im Code als Cent | `insertCoin(cents)` |
| Fach wählen | select drink | der Knopf am Fach | `selectDrink(drink)` |
| Abbruch | cancel | der rote Knopf: Guthaben auszahlen | `cancel()` |
| Guthaben | credit | eingeworfenes, noch nicht ausgegebenes Geld, in Cent; eigene Zeile im Display | `credit()` |
| Meldung | message | der Text im Display unter dem Guthaben, z. B. "Bitte Münzen einwerfen" | `message()` |
| Ablehnung | refused | eine Meldung, die einen Wunsch abweist; das Display blinkt rot | `refused()` |
| Dose | can | eine Dose fällt aus dem Fach ins Ausgabefach | |
| Ausgabefach | output tray | wo die gefallenen Dosen liegen, bis man sie entnimmt | `outputTray()`, `takeDrinks()` |
| Münzrückgabe | coin return | wo ausgezahlte Münzen liegen, bis man sie entnimmt | `coinReturn()`, `takeCoins()` |
| Kasse | cash box | die Münzen im Automaten | |
| Uhrzeit | clock | die Uhr des Automaten; `Main` gibt die Systemzeit, die Schritte eine `FakeClock` | `Clock`, im Test `clock.set(LocalTime.of(15, 59))` |

## Parameter in Schritten

HEAD
awk '/@ParameterType\(/ { match($0, /"[^"]+"/); pat = substr($0, RSTART + 1, RLENGTH - 2); gsub(/\\\\/, "\\", pat); next }
     pat != "" && /public .*\(/ { match($0, /[A-Za-z0-9_]+\(/); print "- {" substr($0, RSTART, RLENGTH - 1) "}: " pat; pat = "" }' "$STEPS/ParameterTypes.java"
cat <<'MID'

## Vorhandene Schritte

Wiederverwenden, bevor ein neuer Schritt entsteht. Quelle: die Step-Klassen im Paket `de.sharpsharp.vendingmachine`.

MID
grep -hoE '@(Angenommen|Wenn|Dann|Und)\("[^"]+"\)' "$STEPS"/*.java | sed -E 's/@([A-Za-z]+)\("(.*)"\)/- \1 \2/' | sort -u
} > specs/glossar.md
echo "specs/glossar.md erzeugt: $(grep -c '^- ' specs/glossar.md) Einträge"
