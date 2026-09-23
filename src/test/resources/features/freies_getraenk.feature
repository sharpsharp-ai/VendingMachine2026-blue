# language: de
Funktionalität: Freies Getränk
  Der erste Schritt: ein Getränk ohne Bezahlung.
  Wer ein Fach wählt, bekommt die Dose.

  Szenario: Ein Getränk wählen
    Angenommen der Automat ist frisch gestartet
    Wenn ich Cola wähle
    Dann liegt eine Dose Cola im Ausgabefach

  Szenario: Preis eines Getränks anzeigen
    Angenommen der Automat ist frisch gestartet
    Dann Preis von Bier ist 200
