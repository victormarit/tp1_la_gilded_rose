package gildedrose.inventory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value= AttackAttribute.class, name = "AttackAttribute"),
    @JsonSubTypes.Type(value= DefenseAttribute.class, name = "DefenseAttribute")
})
public abstract class Attribute {
    public String type;

    String description;
    int effect;

    protected Attribute(@JsonProperty("description") String description, @JsonProperty("effect") int effect) {
        this.description = description;
        this.effect = effect;
        this.type = this.getClass().getSimpleName();
    }

    public String getDescription() {
        return description;
    }

    public int getEffect() {
        return effect;
    }
}
