package application.annotations;

import application.helpers.SQLType;

@Table
public class TestTable {    // default: test_table

    @Skip
    private long id;

    //@Column(length = 50)
    private String username;

    @Column(name = "UserEmail")
    private String emailAddress;

    @Column(type = SQLType.INT, length = 11)
    private boolean isInactive;

}
