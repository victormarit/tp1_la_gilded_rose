package gildedrose.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("GenericItem")
public class GenericItem extends AttributedItem implements Updatable {

    public GenericItem(@JsonProperty("name") String name, @JsonProperty("sellIn") int sellIn,
                       @JsonProperty("quality") int quality, @JsonProperty("basePrice") int basePrice) {
        super(name, sellIn, quality, basePrice);
        this.attributes = List.of(
            new AttackAttribute(1),
            new DefenseAttribute(1)
        );
    }

    @Override
    public void update() {
        if (this.quality >= 0) {
            if (this.sellIn <= 0) {
                this.quality = (this.quality - 2);
            } else {
                this.quality -= 1;
            }
            this.sellIn -= 1;
        }
    }
}
