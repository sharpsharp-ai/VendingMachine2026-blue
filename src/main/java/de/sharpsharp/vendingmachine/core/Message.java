package de.sharpsharp.vendingmachine.core;

/** What the display of the machine shows. The text is the German wording the customer reads. */
public enum Message {
    INSERT_COINS("Bitte Münzen einwerfen"),
    CHOOSE_DRINK("Bitte wählen"),
    TAKE_DRINK("Bitte entnehmen"),
    SOLD_OUT("Ausverkauft"),
    NOT_ENOUGH_MONEY("Zu wenig Geld"),
    NO_CHANGE("Kein Wechselgeld – bitte passend zahlen"),
    EMPTY_OUTPUT_TRAY("Bitte Ausgabefach leeren"),
    NO_BEER_BEFORE_FOUR("Kein Bier vor 4"),
    MALFUNCTION("Störung – Service: 0800 123 456");

    private final String text;

    Message(String text) {
        this.text = text;
    }

    public String text() {
        return text;
    }
}
