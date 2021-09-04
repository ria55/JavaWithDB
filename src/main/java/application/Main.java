package application;

import application.database.DBEngine;

public class Main {

    public static void main(String[] args) {
        DBEngine engine = new DBEngine();       // should connect

        System.out.println(engine.isConnected());
    }

}
