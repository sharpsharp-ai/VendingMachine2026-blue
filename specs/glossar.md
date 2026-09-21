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

- {drink}: Cola|Orange|Zitrone|Bier

## Vorhandene Schritte

Wiederverwenden, bevor ein neuer Schritt entsteht. Quelle: die Step-Klassen im Paket `de.sharpsharp.vendingmachine`.

- Angenommen der Automat ist frisch gestartet
- Dann liegt eine Dose {drink} im Ausgabefach
- Wenn ich {drink} wähle
