package application.database;

public enum TableName {

    DRAGON(5),
    ELEMENT(3),
    DRAGONS_ELEMENT(2);

    public final int COL_NUM;

    TableName(int colNum) {
        COL_NUM = colNum;
    }

}
