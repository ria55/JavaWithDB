package complements.annotations;

import application.models.Rarity;

@Table
public class TestTable2 {

    @Skip
    private short number;

    @Skip
    private String text;

    @Skip
    private Rarity rarity;

    @FK(reference = TestTable.class, referenceField = "id")
    private TestTable testTable;

}
