package application.models;

public enum Rarity {

    COMMON,     // 1-es az adatbázisban
    RARE,       // 2
    VERY_RARE,  // 3
    EPIC,       // 4
    LEGENDARY,  // 5
    HEROIC;     // 6

    public static Rarity find(String name) {
        for (Rarity rarity : Rarity.values()) {
            if (rarity.toString().replace("_", " ").equalsIgnoreCase(name)) {
                return rarity;
            }
        }
        return Rarity.RARE;
    }

    public int getDBIndex() {
        return ordinal() + 1;
    }

}
