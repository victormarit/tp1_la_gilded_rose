package gildedrose.beans;

import gildedrose.controllers.ItemController;

public class GenericItem extends Item {

    public GenericItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
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
