#language:de
Funktionalität: Für Getränke muss der richtige Betrage bezahlt
  werden. Wenn zu wenig Geld kommt eine Fehlermeldung. Dose fällt wenn genug
  Geld und Restguthaben bleibt erhatlen

Szenario: Nicht genug Guthaben und Meldung "Zu wenig Geld wird ausgegeben"
  Angenommen der Automat ist frisch gestartet
  Wenn ich Cola wähle
  Dann zeigt der Automat "zu wenig Geld" an

Szenario: Genug Guthaben und Dose wird ausgeworfen und Restguthaben bleibt
  Angenommen der Automat ist frisch gestartet
  Wenn  2 € wird eingeworfen
  Und ich Cola wähle
  Dann liegt eine Dose Cola im Ausgabefach
  Und Guthaben von 1 € wird angezeigt