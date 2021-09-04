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

    private void addColumns(Column... columns) {
        for (Column column : columns) {
            query.append(EnumHelper.getDBName(column))
                .append(", ");
        }
        query.setLength(query.length() - 2);
    }

    public String build() {
        query.append(";");
        return query.toString();
    }

}
