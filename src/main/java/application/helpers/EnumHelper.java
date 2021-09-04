package application.helpers;

public class EnumHelper {

    public static <T extends Enum<T>> int getDBIndex(Enum<T> enumToUse) {
        return enumToUse.ordinal() + 1;
    }

    public static <T extends Enum<T>> String getDBName(Enum<T> enumToUse) {
        return enumToUse.name().toLowerCase();
    }

}
