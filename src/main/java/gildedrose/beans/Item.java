package gildedrose.beans;

import gildedrose.interfaces.IItem;

public abstract class Item implements IItem {
    private String name;
    private int sellIn;
    private int quality;

    protected int multiplier = 1;

    protected Item(String name, int sellIn, int quality) {
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
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


}
