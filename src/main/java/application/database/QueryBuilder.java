package application.database;

import application.helpers.EnumHelper;

public class QueryBuilder {

    private StringBuilder query;

    public QueryBuilder() {
        query = new StringBuilder();
    }

    public QueryBuilder select(Table table, Column... columns) {
        query.append("SELECT ");
        if (columns.length > 0) {
            addColumns(columns);
        } else {
            query.append("*");
        }
        query.append(" FROM ")
            .append(EnumHelper.getDBName(table));
        return this;
    }

    public QueryBuilder insert(Table table, Column... columns) {
        query.append("INSERT INTO ")
                .append(EnumHelper.getDBName(table));

        int questionMark = (columns.length > 0 ? columns.length : table.COL_NUM);

        if (columns.length > 0) {
            query.append("(");
            addColumns(columns);
            query.append(")");
        }

        prepareValues(questionMark);

        return this;
    }

    private void addColumns(Column... columns) {
        for (Column column : columns) {
            query.append(EnumHelper.getDBName(column))
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
