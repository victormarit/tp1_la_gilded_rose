package gildedrose.beans;

import gildedrose.enums.ItemName;

public class Item {
    private String name;
    private int sellIn;
    private int quality;

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

    public void updateQuality() {
        //TODO add comment to explain RG before each conditions
        if (quality < 0) {
            return;
        }

        if (!this.name.equals(ItemName.AGED_BRIE.getName())
            && !this.name.equals(ItemName.BACKSTAGE_PASSES.getName())) {
            if (!this.name.equals(ItemName.SULFURAS.getName())) {
                if (this.sellIn <= 0) {
                    quality = this.quality - (this.name.equals(ItemName.CONJURED.getName()) ? 4 : 2);
                } else {
                    this.quality = this.quality - (this.name.equals(ItemName.CONJURED.getName()) ? 2 : 1);
                }
            }
        } else {
            if (this.quality < 51) {
                this.quality = this.quality + 1;

                if (this.name.equals( ItemName.BACKSTAGE_PASSES.getName())) {
                    if (this.sellIn < 11 && (this.quality + 2) < 51) {
                        this.quality = this.quality + 2;
                    } else {
                        this.quality = 50;
                    }

                    if (this.sellIn < 6 && (this.quality + 3) < 51) {
                        this.quality = this.quality + 3;
                    } else {
                        this.quality = 50;
                    }

                    if (this.sellIn < 0) {
                        this.quality = 0;
                    }
                }
            }
        }

        if (!this.name.equals(ItemName.SULFURAS.getName())) {
            this.sellIn = this.sellIn - 1;
        }
    }
}
