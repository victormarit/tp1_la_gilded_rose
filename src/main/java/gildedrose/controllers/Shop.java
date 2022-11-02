package gildedrose.controllers;

import gildedrose.beans.Item;

import java.util.List;

public class Shop {
    public List<Item> items;

    public Shop(List<Item> items) {
        this.items = items;
    }

    public void updateItemsQuality() {
        for (Item item : items) {
            item.updateQuality();
        }
    }
}
