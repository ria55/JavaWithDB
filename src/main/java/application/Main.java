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
        DBEngine engine = new DBEngine();       // should connect

        if (engine.isConnected()) {
            Dragon dragon = new Dragon("Test", "Egy random sárkány teszteléshez.", Rarity.VERY_RARE);
            boolean success = engine.addDragonToDB(dragon);
            System.out.println(success);
            System.out.println();
            List<Dragon> dragons = engine.listAllDragons();

            for (Dragon d : dragons) {
                System.out.println(d);
            }
        } else {
            System.out.println("no connection");
        }

        /*
        String query = "SELECT * FROM dragon";
         */

    }

}
