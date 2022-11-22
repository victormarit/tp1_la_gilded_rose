package gildedrose.beans;

import com.fasterxml.jackson.annotation.*;
import gildedrose.interfaces.IItem;

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
public abstract class Item implements IItem {
    private String name;

    public String type;

    private int sellIn;
    private int quality;
    private int basePrice;

    protected int multiplier = 1;

    protected Item(@JsonProperty("name") String name, @JsonProperty("sellIn") int sellIn, @JsonProperty("quality") int quality, @JsonProperty("basePrice") int basePrice) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.basePrice = basePrice;
        this.type = this.getClass().getSimpleName();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        if (this instanceof LegendaryItem) {
            this.quality = 80;
        } else {
            this.quality = quality;
        }
    }
    public int getMultiplier() {
        return this.multiplier;
    }

    @JsonIgnore
    public int getValue() {
        return this.basePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return sellIn == item.sellIn && quality == item.quality && basePrice == item.basePrice && multiplier == item.multiplier && Objects.equals(name, item.name) && type.equals(item.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, sellIn, quality, basePrice, multiplier);
    }
}
