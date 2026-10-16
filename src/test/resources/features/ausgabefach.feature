# language: de
Funktionalität: Kleines Ausgabefach
  Der Automat hat Platz für höchstens 3 Dosen im Ausgabefach.
  Ist es voll, fällt keine weitere Dose und der Automat meldet es.
  Nach dem Leeren kann normal weiter gekauft werden.

  Szenario: Dose fällt ins leere Ausgabefach
    Angenommen der Automat ist frisch gestartet
    Und 1 € wird eingeworfen
    Wenn ich Cola wähle
    Dann liegt eine Dose Cola im Ausgabefach
    Und zeigt der Automat Bitte Münzen einwerfen an

  Szenario: Volles Ausgabefach blockiert weitere Dose
    Angenommen der Automat ist frisch gestartet
    Und 3 € wird eingeworfen
    Und ich Cola wähle
    Und ich Orange wähle
    Und ich Zitrone wähle
    Wenn ich Bier wähle
    Dann zeigt der Automat Bitte Ausgabefach leeren an

  Szenario: Nach dem Leeren normal weiter
    Angenommen der Automat ist frisch gestartet
    Und das Ausgabefach ist mit 3 Dosen gefüllt
    Und das Ausgabefach wird geleert
    Und 1 € wird eingeworfen
    Wenn ich Cola wähle
    Dann liegt eine Dose Cola im Ausgabefach
    Und zeigt der Automat Bitte Münzen einwerfen an
