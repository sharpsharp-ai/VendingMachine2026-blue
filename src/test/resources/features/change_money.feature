# language: de
Funktionalität: Wechselgeld wird zurückgegeben
  Bei Abbruch wird Restgeld ausgegeben

  Szenario:
    Angenommen der Automat ist frisch gestartet und hat ein Guthaben von 2 Euro
    Wenn ich am Automat auf Abbruch gedrückt wird
    Dann ist das Guthaben 0 Euro
    Und in der Münzrückgabe sind 2 Euro