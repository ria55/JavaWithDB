package application.annotations;

import application.helpers.SQLType;

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

}
