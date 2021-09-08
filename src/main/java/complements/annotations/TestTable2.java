package complements.annotations;

import application.models.Rarity;

@Table
public class TestTable2 {

    private short number;
    private String text;
    private Rarity rarity;

    @FK(reference = TestTable.class, referenceField = "id")
    private TestTable testTable;

}
