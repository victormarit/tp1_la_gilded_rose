package gildedrose.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value= RelicItem.class, name = "RelicItem")
})
public abstract non-sealed class UnsellableItem extends Item {
    protected UnsellableItem(@JsonProperty("name") String name, @JsonProperty("quality") int quality) {
        super(name, quality);
    }


}
