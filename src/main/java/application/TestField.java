package application;

import application.database.Column;
import application.database.QueryBuilder;
import application.database.Table;
import application.helpers.EnumHelper;
import application.models.Rarity;

public class TestField {

    public static void main(String[] args) {

        testEnumNameHandling();

    }

    private static void testEnumNameHandling() {
        String query1 = new QueryBuilder().select(Table.DRAGON, Column.ID, Column.UNIQUE_NAME, Column.DRAGON_TEXT)
                .where(Column.ID, false)
                .build();
        String query2 = new QueryBuilder().insert(Table.DRAGON, Column.UNIQUE_NAME, Column.DRAGON_TEXT, Column.DESIGN)
                .build();

        String veryRare = EnumHelper.getDBName(Rarity.VERY_RARE, true);
        String heroic = EnumHelper.getDBName(Rarity.HEROIC, true);

        System.out.println(query1);
        System.out.println(query2);
        System.out.println(veryRare);
        System.out.println(heroic);
    }

}
