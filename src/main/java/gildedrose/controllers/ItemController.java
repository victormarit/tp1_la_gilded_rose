package gildedrose.controllers;

import gildedrose.beans.Item;
import gildedrose.enums.ItemName;
import gildedrose.enums.ItemStates;

public class ItemController {

    public static void qualityIsNotNegative(Item item){
        if (item.getQuality() >= 0) {
            ItemStates.QualityIsNotNegative.nextState().updateQuality(item);
        }
    }

    public static void itemIsNotAgedBrieOrBackstagePasses(Item item) {
        if (!item.getName().equals(ItemName.AGED_BRIE.getName())
            && !item.getName().equals(ItemName.BACKSTAGE_PASSES.getName())) {
            if (!item.getName().equals(ItemName.SULFURAS.getName())) {
                if (item.getSellIn() <= 0) {
                    item.setQuality(item.getQuality() - (item.getName().equals(ItemName.CONJURED.getName()) ? 4 : 2));
                } else {
                    item.setQuality(item.getQuality() - (item.getName().equals(ItemName.CONJURED.getName()) ? 2 : 1));
                }
            }
        }
        ItemStates.ItemIsNotAgedBrieOrBackstagePasses.nextState().updateQuality(item);
    }


    public static void qualityIsLessThan51AndItemIsAgedBrieOrBackstagePasses(Item item){
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

    public static void decreaseSellIn(Item item) {
        if (!item.getName().equals(ItemName.SULFURAS.getName())) {
            item.setSellIn(item.getSellIn() - 1);
        }
    }
}
