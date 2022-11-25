package gildedrose.inventory;

import java.util.Collections;
import java.util.List;

public class InMemoryRepository extends ItemsGateway {
    private List<Item> items;

    private static InMemoryRepository INSTANCE = new InMemoryRepository();

    /** Point d'accès pour l'instance unique du singleton */
    public static InMemoryRepository getInstance()
    {
        return INSTANCE;
    }

    @Override
    public List<Item> getInventory() {
        return Collections.unmodifiableList(this.items);
    }

    @Override
    public void saveInventory(List<Item> items) {
        this.items = items;
    }

    @Override
    public Item findItem(String type, int quality) {
        return items.stream().filter(item -> item.getClass().getSimpleName().equals(type) && item.getQuality() == quality).findFirst().orElse(null);
    }
}
