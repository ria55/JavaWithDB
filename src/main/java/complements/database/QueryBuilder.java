package complements.database;

import application.helpers.Transformer;

import java.lang.reflect.Field;

public class QueryBuilder {

    private final Transformer TRANS;

    private StringBuilder query;

    public QueryBuilder() {
        query = new StringBuilder();
        TRANS = Transformer.getInstance();
    }

    public QueryBuilder createTable(Class<?> table, Field... columns) {
        query.append("CREATE TABLE IF NOT EXISTS ")
                .append(TRANS.getDBName(table))
                .append("(");

        addColumns(columns);

        query.setLength(query.length() - 2);
        query.append(")");

        return this;
    }

    private void addColumns(Field... columns) {
        for (Field col : columns) {
            String name = TRANS.getDBName(col);

            query.append(name).append(", ");
        }
    }

    public QueryBuilder select(TableName tableName, ColumnName... columnNames) {
        query.append("SELECT ");
        if (columnNames.length > 0) {
            addColumns(columnNames);
        } else {
            query.append("*");
        }
        query.append(" FROM ")
            .append(TRANS.getDBName(tableName, false));
        return this;
    }

    public QueryBuilder where(ColumnName columnName, boolean useLike) {
        query.append(" WHERE ")
                .append(TRANS.getDBName(columnName, false))
                .append( (useLike ? " LIKE " : " = ") )
                .append("?");
        return this;
    }

    public QueryBuilder insert(TableName tableName, ColumnName... columnNames) {
        query.append("INSERT INTO ")
                .append(TRANS.getDBName(tableName, false));

        int questionMark = (columnNames.length > 0 ? columnNames.length : tableName.COL_NUM);

        if (columnNames.length > 0) {
            query.append("(");
            addColumns(columnNames);
            query.append(")");
        }

        prepareValues(questionMark);

        return this;
    }

    private void addColumns(ColumnName... columnNames) {
        for (ColumnName columnName : columnNames) {
            query.append(TRANS.getDBName(columnName, false))
                .append(", ");
        }
        query.setLength(query.length() - 2);
    }

    private void prepareValues(int repeat) {
        query.append(" VALUES (")
                .append("?,".repeat(repeat));
        query.setLength(query.length() - 1);
        query.append(")");
    }

    public String build() {
        query.append(";");
        return query.toString();
    }

}
