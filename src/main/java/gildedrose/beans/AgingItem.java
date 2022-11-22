package gildedrose.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import gildedrose.controllers.ItemController;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("AgingItem")
public class AgingItem extends Item {

    public AgingItem(@JsonProperty("name") String name, @JsonProperty("sellIn") int sellIn, @JsonProperty("quality") int quality, @JsonProperty("basePrice") int basePrice) {
        super(name, sellIn, quality, basePrice);

    }

    @Override
    public void updateQuality() {
        boolean isNegative = ItemController.qualityIsNotNegative(this);
        if (isNegative) {
            ItemController.qualityIsLessThan51AndItemIsAgedBrieOrBackstagePasses(this);
            ItemController.decreaseSellIn(this);
        }
    }
}
