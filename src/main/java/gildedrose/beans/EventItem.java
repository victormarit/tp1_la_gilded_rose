package gildedrose.beans;

import gildedrose.controllers.ItemController;

public class EventItem extends Item {

    public EventItem(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    @Override
    public void updateQuality() {
        boolean isNegative = ItemController.qualityIsNotNegative(this);
        if (isNegative) {
            ItemController.qualityIsLessThan51AndItemIsAgedBrieOrBackstagePasses(this);
            ItemController.decreaseSellIn(this);
        }
    }
}
