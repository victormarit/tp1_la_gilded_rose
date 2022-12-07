package gildedrose.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("AttackAttribute")
public class AttackAttribute extends Attribute {
    public AttackAttribute(@JsonProperty("effect") int effect) {
        super("Attack" + effect, effect);
    }
}
