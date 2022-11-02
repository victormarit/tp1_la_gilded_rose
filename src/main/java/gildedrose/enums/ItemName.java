package gildedrose.enums;

public enum ItemName {
    AGED_BRIE("Aged Brie"),
    BACKSTAGE_PASSES("Backstage passes"),
    SULFURAS("Sulfuras"),
    CONJURED("Conjured");

    private final String name;

    ItemName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
