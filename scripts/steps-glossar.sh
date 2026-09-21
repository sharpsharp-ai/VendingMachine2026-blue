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

| Deutsch (Szenarien, Seite) | Englisch (Code) | Bedeutung |
|---|---|---|
| Automat | VendingMachine | der Getränkeautomat |
| Fach | slot | ein Fach mit einem Getränk, nummeriert von 1 |
| Getränk | Drink | Cola, Orange, Zitrone, Bier |
| Dose | can | eine Dose fällt aus dem Fach ins Ausgabefach |
| Ausgabefach | output tray | wo die gefallenen Dosen liegen, bis man sie entnimmt |
| Münze | coin, cents | 50 ct, 1 €, 2 €; im Code als Cent |
| Guthaben | credit | eingeworfenes, noch nicht ausgegebenes Geld, in Cent |
| Kasse | cash box | die Münzen im Automaten |
| Münzrückgabe | coin return | wo ausgezahlte Münzen liegen, bis man sie entnimmt |
| Abbruch | cancel | der rote Knopf: Guthaben auszahlen |
| Meldung | message | der Text im Display |
| Ablehnung | refused | eine Meldung, die einen Wunsch abweist; das Display blinkt rot |

## Parameter in Schritten

HEAD
awk '/@ParameterType\(/ { match($0, /"[^"]+"/); pat = substr($0, RSTART + 1, RLENGTH - 2); next }
     pat != "" && /public .*\(/ { match($0, /[A-Za-z0-9_]+\(/); print "- {" substr($0, RSTART, RLENGTH - 1) "}: " pat; pat = "" }' "$STEPS/ParameterTypes.java"
cat <<'MID'

## Vorhandene Schritte

Wiederverwenden, bevor ein neuer Schritt entsteht. Quelle: die Step-Klassen im Paket `de.sharpsharp.vendingmachine`.

MID
grep -hoE '@(Angenommen|Wenn|Dann|Und)\("[^"]+"\)' "$STEPS"/*.java | sed -E 's/@([A-Za-z]+)\("(.*)"\)/- \1 \2/' | sort -u
} > specs/glossar.md
echo "specs/glossar.md erzeugt: $(grep -c '^- ' specs/glossar.md) Einträge"
