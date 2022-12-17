package gildedrose.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("DefenseAttribute")
public class DefenseAttribute extends Attribute {
    public DefenseAttribute(@JsonProperty("effect") int effect) {
        super("Defense" + effect, effect);
    }
}
