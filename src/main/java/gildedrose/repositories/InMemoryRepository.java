package gildedrose.repositories;

import gildedrose.beans.Item;

import java.io.File;
import java.util.Collections;
import java.util.List;

public class InMemoryRepository extends ItemsRepository {
    private List<Item> items;

    private static InMemoryRepository INSTANCE = new InMemoryRepository();

    /** Point d'accès pour l'instance unique du singleton */
    public static InMemoryRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public List<Item> getItems() {
        return Collections.unmodifiableList(this.items);
    }

    @Override
    public void updateItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public Item getItem(String type, int quality) {
        return items.stream().filter(item -> item.getClass().getSimpleName().equals(type) && item.getQuality() == quality).findFirst().orElse(null);
    }
}
