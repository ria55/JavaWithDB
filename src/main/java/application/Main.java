package application;

import application.database.Column;
import application.database.DBEngine;
import application.database.QueryBuilder;
import application.database.Table;
import application.models.Dragon;
import application.models.Rarity;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        /*DBEngine engine = new DBEngine();       // should connect

        if (engine.isConnected()) {
            Dragon dragon = new Dragon("Marcipán", "Cuki sárkány, edd meg!", Rarity.HEROIC);
            boolean success = engine.addDragonToDB(dragon);
            System.out.println(success);
            System.out.println();
            List<Dragon> dragons = engine.listAllDragons();

            for (Dragon d : dragons) {
                System.out.println(d);
            }
        } else {
            System.out.println("no connection");
        }*/

        String query1 = new QueryBuilder().select(Table.DRAGON).where(Column.UNIQUE_NAME, true).build();
        String query2 = new QueryBuilder().select(Table.DRAGON, Column.ID).where(Column.ID, false).build();
        String query3 = new QueryBuilder().insert(Table.DRAGON).build();
        String query4 = new QueryBuilder().insert(Table.DRAGON, Column.UNIQUE_NAME, Column.DRAGON_TEXT).build();

        System.out.println(query1);
        System.out.println(query2);
        System.out.println(query3);
        System.out.println(query4);

    }

}
