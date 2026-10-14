#language:de
Funktionalität: Wird vor 16:00 Uhr ein Bier gewählt kommt die Meldung kein Bier vor 4,
  keine Dose fällt und Guthaben bleibt. Nach 16:00 Uhr kann es normal gekauft werden.

Szenario: Bier wird vor 16:00 Uhr gekauft und es kommt die Meldung kein Bier vor vier und Guthaben bleibt
  Angenommen der Automat ist frisch gestartet
  Und es ist 15:59 Uhr
  Wenn 1 € wird eingeworfen
  Und ich Bier wähle
  Dann zeigt der Automat Kein Bier vor 4 an
  Und Guthaben von 1 € wird angezeigt

Szenario: Bier wird nach 16:00 Uhr gekauft und wird eine Dose Bier ausgeworfen und Guthaben bleibt
  Angenommen der Automat ist frisch gestartet
  Und es ist 16:00 Uhr
  Wenn 2 € wird eingeworfen
  Und ich Bier wähle
  Dann Guthaben von 0 € wird angezeigt
  Und liegt eine Dose Bier im Ausgabefach
