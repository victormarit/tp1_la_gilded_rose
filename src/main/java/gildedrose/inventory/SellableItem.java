package gildedrose.inventory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value= GenericItem.class, name = "AgingItem"),
    @JsonSubTypes.Type(value= GenericItem.class, name = "GenericItem"),
    @JsonSubTypes.Type(value= ConjuredItem.class, name = "ConjuredItem"),
    @JsonSubTypes.Type(value= LegendaryItem.class, name = "LegendaryItem"),
    @JsonSubTypes.Type(value= EventItem.class, name = "EventItem")
})
public abstract non-sealed class SellableItem extends Item {
    protected float basePrice;

    protected int sellIn;

    protected SellableItem(@JsonProperty("name") String name, @JsonProperty("sellIn") int sellIn, @JsonProperty("quality") int quality, @JsonProperty("basePrice") float basePrice) {
        super(name, quality);
        this.basePrice = basePrice;
        this.sellIn = sellIn;
    }

    @JsonProperty("basePrice")
    public float getValue() {
        return this.basePrice;
    }

    public int getSellIn() {
        return this.sellIn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SellableItem that = (SellableItem) o;
        return Float.compare(that.basePrice, basePrice) == 0 && sellIn == that.sellIn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), basePrice, sellIn);
    }
}
