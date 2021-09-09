package complements.database.testModels;

import application.models.Rarity;
import complements.database.annotations.FK;
import complements.database.annotations.Skip;
import complements.database.annotations.Table;

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
