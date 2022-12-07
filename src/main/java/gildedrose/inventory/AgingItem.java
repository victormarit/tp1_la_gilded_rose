package gildedrose.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;


@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("AgingItem")
public class AgingItem extends SellableItem {

    public AgingItem(@JsonProperty("name") String name, @JsonProperty("sellIn") int sellIn, @JsonProperty("quality") int quality, @JsonProperty("basePrice") int basePrice) {
        super(name, sellIn, quality, basePrice);

    }
    @Override
    public void update(){
        if (this.quality >= 0) {
            if (this.sellIn < 6 && (this.quality + 3) < 51) {
                this.quality = Math.min(this.quality + 3, 5);
            } else if (this.sellIn < 11 && (this.quality + 2) < 51) {
                this.quality = Math.min(this.quality + 2, 50);
            } else if (this.quality < 50) {
                this.quality += 1;
            }

            if (this.sellIn < 0) {
                this.quality = 0;
            }

            this.sellIn -= 1;
        }
    }
}
