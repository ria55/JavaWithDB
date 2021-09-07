package application.helpers;

public class EnumHelper {

    public static <T extends Enum<T>> int getDBIndex(Enum<T> enumToUse) {
        return enumToUse.ordinal() + 1;
    }

    public static <T extends Enum<T>> String getDBName(Enum<T> enumToUse, boolean isEnumInDB) {
        String name = enumToUse.name().toLowerCase();

        if (isEnumInDB) {
            return name.replace("_", " ");
        }

        return name;
    }

}
