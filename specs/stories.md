# Stories

Die Karten des Kurses, in dieser Reihenfolge zu bauen. Jede Story bekommt `specs/<nr>-<kurzname>/spec.md`
und eine Feature-Datei unter `src/test/resources/features/`.

## 1 Alles umsonst

Als reisende Person mit wenig Geld und Koffeinunverträglichkeit
möchte ich alle Getränke umsonst bekommen,
damit ich meinen Durst stillen kann.

- Egal welchen Knopf ich drücke: Solange eine Dose im Fach ist, fällt sie ohne Bezahlung ins Ausgabefach.

## 2 Preis anzeigen

Als Kunde
möchte ich sehen, was mein Getränk kostet,
damit ich entscheiden kann, ob ich es mir leisten will.

- An jedem Fach steht der Preis hinter dem Namen, z. B. „Cola 1,00 €".

## 3 Guthaben anzeigen

Als Kunde
möchte ich sehen, wie viel Geld ich schon eingeworfen habe,
damit ich nicht zu viel in den Automaten werfe.

- Das eingeworfene Geld wird als „Guthaben: x,xx €" angezeigt.
- Der Automat nimmt 50 ct, 1 € und 2 €; die Münzen addieren sich.

## 4 Getränke kosten Geld

Als geschäftstüchtiger Automatenbetreiber
möchte ich, dass die Leute für ihre Getränke bezahlen,
damit ich endlich Umsatz mache.

- Reicht das Guthaben nicht, fällt keine Dose und der Automat meldet „Zu wenig Geld".
- Reicht das Guthaben, fällt die Dose und der Preis wird abgezogen.
- Das Restguthaben bleibt und wird angezeigt.

## 5 Wechselgeld

Als ehrlicher Automatenbetreiber
möchte ich Wechselgeld herausgeben, wenn jemand Abbruch drückt,
damit ich nicht eines Tages vor Gericht lande.

- Nach Abbruch liegt das gesamte Guthaben als Münzen in der Münzrückgabe.
- Das Guthaben zeigt 0,00 €.
- Ein Klick auf die Münzrückgabe leert sie.

## 6 Bier kostet mehr

Als cleverer Automatenbetreiber
möchte ich Bier für 2,00 € verkaufen,
damit ich an durstigen Studenten mehr verdiene.

- Bier fällt nur bei einem Guthaben von mindestens 2,00 €.

## 7 Kein Bier vor 4

Als verantwortungsvoller Automatenbetreiber
möchte ich vor 16:00 Uhr kein Bier verkaufen,
damit im Büro erst nach Feierabend getrunken wird.

- Wird vor 16:00 Uhr Bier gewählt, fällt keine Dose; der Automat meldet „Kein Bier vor 4" und sagt es laut.
- Ab 16:00 Uhr fällt Bier wie gewohnt.
- Das Guthaben bleibt.

## 8 Störung

Als Kunde vor einem kaputten Automaten
möchte ich eine Servicenummer sehen,
damit ich mein Geld zurückbekomme.

- Bleibt eine Dose stecken, obwohl sie fallen müsste, meldet der Automat „Störung – Service: 0800 123 456".
- Der Preis wird nicht abgezogen.

## 9 Ausverkauft

Als fürsorglicher Automatenbetreiber
möchte ich sehen, wenn ein Getränk ausverkauft ist,
damit ich schnell nachfüllen kann.

- Wird der Knopf eines leeren Fachs gedrückt, meldet der Automat „Ausverkauft".

## 10 Kleines Ausgabefach

Als Automatenhersteller
möchte ich kleine Automaten mit Platz für höchstens 3 Dosen im Ausgabefach bauen,
damit ich günstiger produzieren kann.

- Ist das Ausgabefach voll, fällt keine weitere Dose.
- Der Automat meldet „Bitte Ausgabefach leeren".
- Nach dem Leeren (Klick auf das Fach) erscheint die vorherige Meldung wieder.

## 11 Kein Wechselgeld

Als ehrlicher Automatenbetreiber
möchte ich keinen Kauf zulassen, dessen Restguthaben ich nicht auszahlen kann,
damit niemand um sein Geld gebracht wird.

- Die Kasse startet mit 5 Stück jeder Münze.
- Reicht der Münzvorrat nicht, um das Restguthaben nach dem Kauf auszuzahlen, fällt keine Dose und der Automat meldet „Kein Wechselgeld – bitte passend zahlen".
- Das Guthaben bleibt.

## Zum Schätzen und Diskutieren

- **Andere Knopfreihenfolge:** Cola-Knopf auf Platz 3, Apfelsaft auf Platz 1. Wer Cola drückt, bekommt Cola; wer Apfelsaft drückt, bekommt Apfelsaft.
- **Rabatt:** Drei gleiche Softdrinks zum Preis von zwei; nur bei derselben Sorte; kein Rabatt auf Bier.
- **Bezahlen mit Chipkarte:** Das Guthaben auf der Karte sinkt um den Preis; der Automat erfasst die Kartenzahlungen des Betreibers.
