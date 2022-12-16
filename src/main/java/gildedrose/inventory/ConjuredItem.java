package gildedrose.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("ConjuredItem")
public class ConjuredItem extends AttributedItem implements Updatable {

    public ConjuredItem(@JsonProperty("name") String name, @JsonProperty("sellIn") int sellIn, @JsonProperty("quality") int quality, @JsonProperty("basePrice") int basePrice) {
        super(name, sellIn, quality, basePrice);
        this.attributes = List.of(
            new AttackAttribute(-2),
            new DefenseAttribute(2)
        );
    }

    @Override
    public void update(){
        if (this.quality >= 0) {
            if (this.sellIn <= 0) {
                this.quality = (this.quality - 4);
            } else {
                this.quality -= 2;
            }
            this.sellIn -= 1;
        }
    }
}
