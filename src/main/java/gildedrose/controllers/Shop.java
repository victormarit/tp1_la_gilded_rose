package gildedrose.controllers;

import gildedrose.beans.Item;

public class Shop {
    Item[] items;

    public Shop(Item[] items) {
        this.items = items;
    }

    public void updateItemsQuality() {
        for (Item item : items) {
            item.updateQuality();
        }
    }
}
