package gildedrose.beans;

import gildedrose.enums.ItemName;
import gildedrose.enums.ItemStates;

public class Item {
    private String name;
    private int sellIn;
    private int quality;

    protected int multiplier = 1;

    public Item(String name, int sellIn, int quality) {
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
        if (this.name.equals(ItemName.SULFURAS.getName())) {
            this.quality = 80;
        } else {
            this.quality = quality;
        }
    }

    public int getMultiplier() {
        return this.multiplier;
    }

    /**
     * update item quality in function of multiple rules
     */
    public void updateQuality() {
        ItemStates.QualityIsNotNegative.updateQuality(this);
    }
}
