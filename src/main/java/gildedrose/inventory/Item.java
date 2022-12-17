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
    @JsonSubTypes.Type(value= LegendaryItem.class, name = "LegendaryItem"),
    @JsonSubTypes.Type(value= RelicItem.class, name = "RelicItem")
})
public abstract sealed class Item permits SellableItem, UnsellableItem {
    protected String name;

    public String type;
    protected int quality;

    protected Item(@JsonProperty("name") String name, @JsonProperty("quality") int quality) {
        this.name = name;
        this.quality = quality;
        this.type = this.getClass().getSimpleName();
    }


    public String getName() {
        return name;
    }

    public int getQuality() {
        return quality;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item item)) return false;
        return quality == item.quality && Objects.equals(name, item.name) && type.equals(item.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, quality);
    }
}
