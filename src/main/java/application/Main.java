package application;

import application.database.DBEngine;
import application.models.Dragon;
import application.models.Rarity;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        DBEngine engine = new DBEngine();       // should connect

        if (engine.isConnected()) {
            List<Dragon> dragons = engine.listAllDragons();

            for (Dragon dragon : dragons) {
                System.out.println(dragon);
            }

            /*Dragon dragon = new Dragon("Paff", "A bűvös sárkány", Rarity.HEROIC);
            boolean success = engine.addDragonToDB(dragon);
            System.out.println(success);*/
        } else {
            System.out.println("no connection");
        }
    }

}
