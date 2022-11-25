package gildedrose.inventory;

import java.util.List;

public abstract class ItemsGateway {
    public abstract List<Item> getInventory();

    public abstract Item findItem(String type, int quality);

    public abstract void saveInventory(List<Item> items);
}
