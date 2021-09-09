package complements.database.testModels;

import complements.database.SQLType;
import complements.database.annotations.Column;
import complements.database.annotations.PK;
import complements.database.annotations.Skip;
import complements.database.annotations.Table;

@Table
public class TestTable {    // default: test_table

    @PK
    @Column(name = "testID")
    private long id;

    //@Column(length = 50)
    private String username;

    @Column(name = "UserEmail", isNotNull = true)
    private String emailAddress;

    @Column(type = SQLType.INT, length = 11, defaultValue = "0")
    private boolean isInactive;

    @Skip
    private int skipNumber;

}
