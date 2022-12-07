package gildedrose.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("LegendaryItem")
public class LegendaryItem extends AttributedItem {

    public LegendaryItem(@JsonProperty("name") String name, @JsonProperty("sellIn") int sellIn,
                         @JsonProperty("quality") int quality, @JsonProperty("basePrice") int basePrice) {
        super(name, sellIn, quality, basePrice);
        this.attributes = List.of(
            new AttackAttribute(4)
        );
    }

    @Override
    public void update(){}

}
