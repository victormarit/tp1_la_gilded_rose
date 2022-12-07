package gildedrose.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonTypeName("RelicItem")
public class RelicItem extends UnsellableItem {
    public RelicItem(@JsonProperty("name") String name, @JsonProperty("quality") int quality) {
        super(name, quality);
    }

    @Override
    public void update() {
    }
}
