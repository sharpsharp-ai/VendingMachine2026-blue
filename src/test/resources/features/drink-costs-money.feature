# language: de
Funktionalität: Getränke kosten Geld
  Getränke kosten Geld und benötigen ein Guthaben.

  Szenario:
    Angenommen der Automat ist frisch gestartet und hat ein Guthaben von 2 Euro
    Wenn ich Cola wähle
    Dann ist das Guthaben 1 Euro
    Und liegt eine Dose Cola im Ausgabefach

  Szenario:
    Angenommen der Automat ist frisch gestartet
    Wenn ich Cola wähle
    Dann liegt keine Dose im Ausgabefach
    Und der Automat meldet "Zu wenig Geld"
