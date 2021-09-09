package application.services;

import complements.database.ColumnName;
import complements.database.TableName;
import application.helpers.Transformer;
import complements.PropertiesHandler;
import complements.database.QueryBuilder;
import complements.logger.LogHandler;
import application.models.Dragon;
import application.models.Element;
import application.models.Rarity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBEngine {

    private static final LogHandler LOG = new LogHandler(DBEngine.class, "application_logs.txt");

    private final PropertiesHandler properties;
    private final Connection connection;

    public DBEngine() {
        properties = PropertiesHandler.getInstance();
        connection = connect();
    }

    public boolean isConnected() {
        return (connection != null);
    }

    private String getURL() {
        String mysqlURL = properties.getProperty("mysql-url") + properties.getProperty("db-name");
        String props = properties.getProperty("mysql-props");

        if (props != null) {
            mysqlURL += "?" + props;
        }

        return mysqlURL;
    }

    private Connection connect() {
        String url = getURL();

        try {
            Properties properties = new Properties();
            properties.put("user", this.properties.getProperty("db-user"));
            properties.put("password", this.properties.getProperty("db-password"));

            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            LOG.error("connect()", e.getMessage());
        } catch (NullPointerException e) {
            LOG.error("connect()", "Environment variables not found.");
        }

        return null;
    }

    public Dragon findDragonByName(String searchName) {
        //String query = "SELECT * FROM dragon WHERE unique_name = ?";
        String query = new QueryBuilder()
                .select(TableName.DRAGON)
                .where(ColumnName.UNIQUE_NAME, false)
                .build();

        Dragon result = null;

        try {
            // Statement statement = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, searchName);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                // getXXX("column_name_in_DB")
                long id = resultSet.getLong("id");        // resultSet.getLong(1);
                String name = resultSet.getString("unique_name");
                String text = resultSet.getString("dragon_text");
                String rarityFromDB = resultSet.getString("rarity").toUpperCase();
                Rarity rarity = Rarity.valueOf(rarityFromDB);
                // Rarity rarity = Rarity.find(rarityFromDB);

                result = new Dragon(id, name, text, rarity);
            }

        } catch (SQLException e) {
            LOG.error("findDragonByName(String searchName)", e.getMessage());
        }
        return result;
    }

    public List<Dragon> listAllDragons() {
        //String query = "SELECT * FROM dragon";
        String query = new QueryBuilder()
                .select(TableName.DRAGON)
                .build();

        List<Dragon> dragons = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                // getXXX("column_name_in_DB")
                long id = resultSet.getLong("id");        // resultSet.getLong(1);
                String name = resultSet.getString("unique_name");
                String text = resultSet.getString("dragon_text");
                String rarityFromDB = resultSet.getString("rarity").toUpperCase();
                // Rarity rarity = Rarity.valueOf(rarityFromDB);
                Rarity rarity = Rarity.find(rarityFromDB);

                Dragon dragon = new Dragon(id, name, text, rarity);
                dragon.setElements(findDragonsElement(id));

                dragons.add(dragon);
            }

        } catch (SQLException e) {
            LOG.error("listAllDragons()", e.getMessage());
        }

        return dragons;
    }

    public Element findElementByName(String name) {
        //String query = "SELECT * FROM " + DBHelper.TABLE_ELEMENT + " WHERE element_name = ?";
        String query = new QueryBuilder()
                .select(TableName.ELEMENT)
                .where(ColumnName.ELEMENT_NAME, false)
                .build();

        Element element = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String elementName = resultSet.getString("element_name");

                element = new Element(elementName);
            }
        } catch (SQLException e) {
            LOG.error("findElementByName(String name)", e.getMessage());
        }

        return element;
    }

    public List<Element> findDragonsElement(long dragonId) {
        //String query = "SELECT * FROM " + DBHelper.TABLE_DRAGONS_ELEMENT + " WHERE dragon_id = ?";
        String query = new QueryBuilder().select(TableName.DRAGONS_ELEMENT).where(ColumnName.DRAGON_ID, false).build();

        List<Element> elements = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setLong(1, dragonId);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String elementName = resultSet.getString("element_name");
                Element element = findElementByName(elementName);
                elements.add(element);
            }
        } catch (SQLException e) {
            LOG.error("findDragonsElement(long dragonId)", e.getMessage());
        }
        return elements;
    }

    public boolean addDragonToDB(Dragon dragon) {
        //String query = "INSERT INTO dragon (unique_name, dragon_text, rarity) VALUES (?, ?, ?);";
        String query = new QueryBuilder().insert(TableName.DRAGON, ColumnName.UNIQUE_NAME, ColumnName.DRAGON_TEXT, ColumnName.RARITY).build();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, dragon.getUniqueName());
            ps.setString(2, dragon.getDragonText());
            ps.setInt(3, Transformer.getInstance().getDBIndex(dragon.getRarity()));

            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException e) {
            LOG.error("addDragonToDB(Dragon dragon)", e.getMessage());
            return false;
        }
    }

}
