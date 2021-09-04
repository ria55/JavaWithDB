package application.database;

public enum Table {

    DRAGON(5),
    ELEMENT(3),
    DRAGONS_ELEMENT(2);

    public final int COL_NUM;

    Table(int colNum) {
        COL_NUM = colNum;
    }

}
