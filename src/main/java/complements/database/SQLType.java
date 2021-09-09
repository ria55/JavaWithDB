package complements.database;

public enum SQLType {

    // TODO check max length!
    DEFAULT(0),
    INT(11, "byte", "short", "int"),
    INT_UNSIGNED(0, "long"),
    VARCHAR(255, "String"),
    BOOL(0,"boolean");

    private final String[] javaTypes;
    public final int maxLength;

    SQLType(int maxLength, String... strings) {
        this.maxLength = maxLength;
        javaTypes = strings;
    }

    @Override
    public String toString() {
        return name().replace("_", " ");
    }

    public static SQLType convertType(String javaType) {
        for (SQLType t : SQLType.values()) {
            for (String s : t.javaTypes) {
                if (s.equalsIgnoreCase(javaType)) {
                    return t;
                }
            }
        }
        return VARCHAR;
    }

}
