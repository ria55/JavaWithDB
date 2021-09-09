package complements.database;

import application.helpers.Transformer;

public class QueryBuilder {

    private StringBuilder query;

    public QueryBuilder() {
        query = new StringBuilder();
    }

    public QueryBuilder select(TableName tableName, ColumnName... columnNames) {
        query.append("SELECT ");
        if (columnNames.length > 0) {
            addColumns(columnNames);
        } else {
            query.append("*");
        }
        query.append(" FROM ")
            .append(Transformer.getDBName(tableName, false));
        return this;
    }

    public QueryBuilder where(ColumnName columnName, boolean useLike) {
        query.append(" WHERE ")
                .append(Transformer.getDBName(columnName, false))
                .append( (useLike ? " LIKE " : " = ") )
                .append("?");
        return this;
    }

    public QueryBuilder insert(TableName tableName, ColumnName... columnNames) {
        query.append("INSERT INTO ")
                .append(Transformer.getDBName(tableName, false));

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
            query.append(Transformer.getDBName(columnName, false))
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
