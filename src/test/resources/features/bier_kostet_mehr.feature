#language:de
Funktionalität: Wird vor 16:00 Uhr ein Bier gewählt kommt die Meldung kein Bier vor 4,
  keine Dose fällt und Guthaben bleibt. Nach 16:00 Uhr kann es normal gekauft werden.

Szenario: Bier wird nach 16:00 Uhr gekauft mit genug Guthabn und Dose fällt
  Angenommen der Automat ist frisch gestartet
  Und es ist 16:00 Uhr
  Wenn 2 € wird eingeworfen
  Und ich Bier wähle
  Dann liegt eine Dose Bier im Ausgabefach
  Und Guthaben von 0 € wird angezeigt