package gildedrose.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import gildedrose.controllers.ItemController;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("ConjuredItem")
public class ConjuredItem extends Item {

    public ConjuredItem(@JsonProperty("name") String name, @JsonProperty("sellIn") int sellIn, @JsonProperty("quality") int quality, @JsonProperty("basePrice") int basePrice) {
        super(name, sellIn, quality, basePrice);
        this.multiplier = 2;
    }

    @Override
    public void updateQuality() {
        boolean isNegative = ItemController.qualityIsNotNegative(this);
        if (isNegative) {
            ItemController.itemIsNotAgedBrieOrBackstagePassesOrSulfuras(this);
            ItemController.decreaseSellIn(this);
        }
    }
}
