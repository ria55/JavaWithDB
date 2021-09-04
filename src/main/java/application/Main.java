package application;

import application.database.DBEngine;
import application.models.Dragon;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        DBEngine engine = new DBEngine();       // should connect

        if (engine.isConnected()) {
            Dragon dragon = engine.findDragonByName("my dragon");
            System.out.println(dragon);
        } else {
            System.out.println("no connection");
        }
    }

}
