package gildedrose.shop;

public record ItemResponse(String name, int sellIn, int quality, float value) {
    public ItemResponse(String name, int quality) {
        this(name, 0, quality, 0);
    }
}
