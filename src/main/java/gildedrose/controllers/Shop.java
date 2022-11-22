package gildedrose.controllers;

import gildedrose.beans.Item;
import gildedrose.repositories.InMemoryRepository;
import gildedrose.repositories.ItemsRepository;

import java.util.List;

public class Shop {
    public ItemsRepository itemsRepository;
    private int balance;

    public Shop() {
        this.itemsRepository = InMemoryRepository.getInstance();
        balance = 50;
    }

    public void updateItemsQuality() {
        List<Item> items = itemsRepository.getItems();
        for (Item item : items) {
            item.updateQuality();

        }
    }

    public void sellItem(String type, int quality){
        Item item = this.itemsRepository.getItem(type, quality);
        List<Item> itemsRep = this.itemsRepository.getItems();

        itemsRep.stream().filter(item1 -> item1.equals(item)).findFirst().ifPresent(itemFinal -> {
            itemFinal.setSellIn(itemFinal.getSellIn() - 1);
            this.balance += itemFinal.getValue();
        });
        this.itemsRepository.updateItems(itemsRep);
    }

    public List<Item> getItems() {
        return this.itemsRepository.getItems();
    }

    public float getBalance() {
        return this.balance;
    }

    public void updateItems(List<Item> items) {
        this.itemsRepository.updateItems(items);
    }
}
