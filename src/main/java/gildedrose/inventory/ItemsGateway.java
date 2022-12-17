package gildedrose.inventory;

import java.util.List;

public interface ItemsGateway {
    List<Item> getInventory();

    Item findItem(String type, int quality);

    void saveInventory(List<Item> items);
}
