package gildedrose;

import gildedrose.beans.Item;
import gildedrose.controllers.Shop;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    List<Item> items = List.of(
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Aged Brie", 2, 0),
            new Item("Elixir of the Mongoose", 5, 7),
            new Item("Sulfuras", 0, 80),
            new Item("Sulfuras", -1, 80),
            new Item("Backstage passes", 15, 20),
            new Item("Backstage passes", 10, 49),
            new Item("Backstage passes", 5, 49),
            new Item("Conjured", 3, 6)
    );
    @Test
    void should_build() {
        Item[] itemsArray = new Item[items.size()];
        itemsArray = items.toArray(itemsArray);
        Shop shop = new Shop(itemsArray);
        assertEquals(shop.getClass(), Shop.class);
    }

    @Test
    void should_update_items_quality() {
        Item[] itemsArray = new Item[items.size()];
        itemsArray = items.toArray(itemsArray);
        Shop shop = new Shop(itemsArray);
        shop.updateItemsQuality();
        assertEquals(9, shop.items[0].getSellIn());
        assertEquals(19, shop.items[0].getQuality());
        assertEquals(1, shop.items[1].getSellIn());
        assertEquals(3, shop.items[1].getQuality());
        assertEquals(4, shop.items[2].getSellIn());
        assertEquals(6, shop.items[2].getQuality());
        assertEquals(0, shop.items[3].getSellIn());
        assertEquals(80, shop.items[3].getQuality());
        assertEquals(-1, shop.items[4].getSellIn());
        assertEquals(80, shop.items[4].getQuality());
        assertEquals(14, shop.items[5].getSellIn());
        assertEquals(21, shop.items[5].getQuality());
        assertEquals(9, shop.items[6].getSellIn());
        assertEquals(50, shop.items[6].getQuality());
        assertEquals(4, shop.items[7].getSellIn());
        assertEquals(50, shop.items[7].getQuality());
        assertEquals(2, shop.items[8].getSellIn());
        assertEquals(4, shop.items[8].getQuality());
    }
}
