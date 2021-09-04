package application.database;

public enum Table {

    DRAGON,
    ELEMENT,
    DRAGONS_ELEMENT;

    public String dbName() {
        return name().toLowerCase();
    }

}
