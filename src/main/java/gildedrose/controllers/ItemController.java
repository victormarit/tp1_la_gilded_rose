package gildedrose.controllers;

import gildedrose.beans.Item;
import gildedrose.enums.ItemName;
import gildedrose.enums.ItemStates;

public class ItemController {

    private ItemController() {
    }

    /**
     * If the quality of the item is greater than or equal to 0, then update the quality of the item
     *
     * @param item the item to be updated
     */
    public static void qualityIsNotNegative(Item item) {
        if (item.getQuality() >= 0) {
            ItemStates.QualityIsNotNegative.nextState().updateQuality(item);
        }
    }

    /**
     * If the item is not Aged Brie, Backstage Passes, or Sulfuras, then decrease the quality by 2 if the sell in date is
     * less than or equal to 0, otherwise decrease the quality by 1.
     * If the item is conjured then multiply the quality decrease by 2.
     *
     * @param item The item to be updated
     */
    public static void itemIsNotAgedBrieOrBackstagePassesOrSulfuras(Item item) {
        if (!item.getName().equals(ItemName.AGED_BRIE.getName())
            && !item.getName().equals(ItemName.BACKSTAGE_PASSES.getName()) && !item.getName().equals(ItemName.SULFURAS.getName())) {
            if (item.getSellIn() <= 0) {
                item.setQuality(item.getQuality() - 2*item.getMultiplier());
            } else {
                item.setQuality(item.getQuality() - item.getMultiplier());
            }
        }
        ItemStates.ItemIsNotAgedBrieOrBackstagePassesOrSulfuras.nextState().updateQuality(item);
    }


    /**
     * If the item is Aged Brie or Backstage Passes, and the quality is less than 50, then increase the quality by 1, 2, or
     * 3 depending on the sell in date
     *
     * @param item The item to be updated
     */
    public static void qualityIsLessThan51AndItemIsAgedBrieOrBackstagePasses(Item item) {
        if (item.getName().equals(ItemName.AGED_BRIE.getName()) || item.getName().equals(ItemName.BACKSTAGE_PASSES.getName())) {
            if (item.getSellIn() < 6 && (item.getQuality() + 3) < 51) {
                item.setQuality(Math.min(item.getQuality() + 3, 50));
            } else if (item.getSellIn() < 11 && (item.getQuality() + 2) < 51) {
                item.setQuality(Math.min(item.getQuality() + 2, 50));
            } else if (item.getQuality() < 50) {
                item.setQuality(item.getQuality() + 1);
            }

            if (item.getSellIn() < 0) {
                item.setQuality(0);
            }
        }

        ItemStates.QualityIsLessThan51AndItemIsAgedBrieOrBackstagePasses.nextState().updateQuality(item);
    }

    /**
     * If the item is not Sulfuras, decrease the sellIn value by 1.
     *
     * @param item The item to be updated
     */
    public static void decreaseSellIn(Item item) {
        if (!item.getName().equals(ItemName.SULFURAS.getName())) {
            item.setSellIn(item.getSellIn() - 1);
        }
    }
}
