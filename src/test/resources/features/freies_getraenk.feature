# language: de
Funktionalität: Freies Getränk
  Der erste Schritt: ein Getränk ohne Bezahlung.
  Wer ein Fach wählt, bekommt die Dose.

  Szenario: Preis eines Getränks anzeigen
    Angenommen der Automat ist frisch gestartet
    Dann Preis von Bier ist 2 Euro

  Szenario:
    Angenommen der Automat ist frisch gestartet
    Wenn ich 2 Euro einwerfe
    Dann ist das Guthaben 2 Euro
