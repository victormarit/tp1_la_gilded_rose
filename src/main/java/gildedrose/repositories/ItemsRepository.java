package gildedrose.repositories;

import gildedrose.beans.Item;

import java.util.List;

public abstract class ItemsRepository {
    public abstract List<Item> getItems();

    public abstract Item getItem(String type, int quality);

    public abstract void updateItems(List<Item> items);
}
