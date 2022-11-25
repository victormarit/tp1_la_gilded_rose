package gildedrose.inventory;

import com.fasterxml.jackson.annotation.*;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value= GenericItem.class, name = "GenericItem"),
    @JsonSubTypes.Type(value= AgingItem.class, name = "AgingItem"),
    @JsonSubTypes.Type(value= ConjuredItem.class, name = "ConjuredItem"),
    @JsonSubTypes.Type(value= EventItem.class, name = "EventItem"),
    @JsonSubTypes.Type(value= LegendaryItem.class, name = "LegendaryItem")
})
public abstract class Item {
    protected String name;

    public String type;

    protected int sellIn;
    protected int quality;
    protected int basePrice;

    protected Item(@JsonProperty("name") String name, @JsonProperty("sellIn") int sellIn, @JsonProperty("quality") int quality, @JsonProperty("basePrice") int basePrice) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.basePrice = basePrice;
        this.type = this.getClass().getSimpleName();
    }

    public abstract void update();


    public String getName() {
        return name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public int getQuality() {
        return quality;
    }

    @JsonIgnore
    public int getValue() {
        return this.basePrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return sellIn == item.sellIn && quality == item.quality && basePrice == item.basePrice && Objects.equals(name, item.name) && type.equals(item.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, sellIn, quality, basePrice);
    }
}
