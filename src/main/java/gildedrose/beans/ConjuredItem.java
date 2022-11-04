package gildedrose.beans;

import gildedrose.controllers.ItemController;

public class ConjuredItem extends Item {

    public ConjuredItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
        this.multiplier = 2;
    }

    @Override
    public void updateQuality() {
        boolean isNegative = ItemController.qualityIsNotNegative(this);
        if (isNegative) {
            ItemController.itemIsNotAgedBrieOrBackstagePassesOrSulfuras(this);
            ItemController.decreaseSellIn(this);
        }
    }
}
