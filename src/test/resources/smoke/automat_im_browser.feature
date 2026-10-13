# language: de
@browser
Funktionalität: Rauchtest im Browser
  Ein echter Chrome lädt die Seite des Automaten. Läuft dieses eine Szenario durch,
  hängen Seite, Web-Adapter und Automat richtig zusammen. Alles andere prüfen die
  schnellen Szenarien unter features/ direkt am Port.

  Szenario: Der Automat steht bereit
    Angenommen ich stehe vor dem Automaten
    Dann zeigt das Display "Bitte Münzen einwerfen"
    Und der Automat zeigt 4 Fächer
