# language: de
Funktionalität: Guthaben anzeigen
  Der erste Schritt: Geld wird eingeworfen.
  Das Guthaben wird angezeigt.

  Szenario: Guthaben wird nach einer Münze angezeigt
    Angenommen der Automat ist frisch gestartet
    Wenn 1 € wird eingeworfen
    Dann Guthaben von 1 € wird angezeigt


  Szenario: Guthaben wird zwei Münzen angezeigt
    Angenommen der Automat ist frisch gestartet
    Wenn 1 € wird eingeworfen
    Und 50 ct wird eingeworfen
    Dann Guthaben von 1,50 € wird angezeigt
