package gildedrose.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import gildedrose.controllers.ItemController;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("LegendaryItem")
public class LegendaryItem extends Item {

    public LegendaryItem(@JsonProperty("name") String name, @JsonProperty("sellIn") int sellIn, @JsonProperty("quality") int quality, @JsonProperty("basePrice") int basePrice) {
        super(name, sellIn, quality, basePrice);
    }

    @Override
    public void updateQuality() {

    }

}
