package gildedrose.enums;

import gildedrose.beans.Item;
import gildedrose.controllers.ItemController;

public enum ItemStates {
    QualityIsNotNegative {
        @Override
        public ItemStates nextState() {
            return ItemIsNotAgedBrieOrBackstagePasses;
        }

        @Override
        public void updateQuality(Item item) {
            ItemController.qualityIsNotNegative(item);
        }
    },
    ItemIsNotAgedBrieOrBackstagePasses {
        @Override
        public ItemStates nextState() {
            return QualityIsLessThan51AndItemIsAgedBrieOrBackstagePasses;
        }

        @Override
        public void updateQuality(Item item) {
            ItemController.itemIsNotAgedBrieOrBackstagePasses(item);
        }
    },
    QualityIsLessThan51AndItemIsAgedBrieOrBackstagePasses {
        @Override
        public ItemStates nextState() {
            return DecreaseSellIn;
        }

        @Override
        public void updateQuality(Item item) {
            ItemController.qualityIsLessThan51AndItemIsAgedBrieOrBackstagePasses(item);
        }
    },
    DecreaseSellIn {
        @Override
        public ItemStates nextState() {
            return this;
        }

        @Override
        public void updateQuality(Item item) {
            ItemController.decreaseSellIn(item);
        }
    };



    public abstract ItemStates nextState();
    public abstract void updateQuality(Item item);
}
