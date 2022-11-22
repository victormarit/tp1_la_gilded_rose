package gildedrose.controllers;

import gildedrose.beans.Item;
import gildedrose.repositories.InMemoryRepository;
import gildedrose.repositories.ItemsRepository;

import java.util.List;

public class Shop {
    public List<Item> items;
    public ItemsRepository itemsRepository;

    public Shop(List<Item> items) {
        this.items = items;
        this.itemsRepository = InMemoryRepository.getInstance();
    }

    public List<Item> updateItemsQuality() {
        for (Item item : items) {
            item.updateQuality();

        }
        return items;
    }

    public void sellItem(String type, int quality){
        Item item = this.itemsRepository.getItem(type, quality);
        List<Item> itemsRep = this.itemsRepository.getItems();

        itemsRep.stream().filter(item1 -> item1.equals(item)).findFirst().ifPresent(itemFinal -> itemFinal.setSellIn(itemFinal.getSellIn() - 1));
        this.itemsRepository.updateItems(items);
    }
}
