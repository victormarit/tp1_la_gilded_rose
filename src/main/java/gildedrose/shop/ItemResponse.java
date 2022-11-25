package gildedrose.shop;

public class ItemResponse {
    private int sellIn;
    private int quality;
    private int value;
    private String name;

    public ItemResponse(String name, int sellIn, int quality, int value) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.value = value;
    }

    public int getSellIn() {
        return sellIn;
    }

    public int getQuality() {
        return quality;
    }

    public int getValue() {
        return value;
    }

    public String getName() { return name; }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setName(String name) { this.name = name; }
}
