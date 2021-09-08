package complements.database;

public enum SQLType {

    INT("byte", "short", "int", "long"),
    VARCHAR("String"),
    BOOL("boolean");

    private final String[] javaTypes;

    SQLType(String... strings) {
        javaTypes = strings;
    }

    public static String convertType(String javaType) {
        for (SQLType t : SQLType.values()) {
            for (String s : t.javaTypes) {
                if (s.equalsIgnoreCase(javaType)) {
                    return t.name();
                }
            }
        }
        return VARCHAR.name();
    }

}
