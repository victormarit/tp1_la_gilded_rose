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
        new Item("Conjured", 3, 6),
        new Item("Conjured", 0, 6),
        new Item("Aged Brie", -1, 49),
        new Item("Aged Brie", 10, 48)
    );

    @Test
    void should_build() {
        Shop shop = new Shop(items);
        assertEquals(Shop.class, shop.getClass());
    }

    @Test
    void should_update_items_quality() {
        Shop shop = new Shop(items);
        shop.updateItemsQuality();
        assertEquals(9, shop.items.get(0).getSellIn());
        assertEquals(19, shop.items.get(0).getQuality());
        assertEquals(1, shop.items.get(1).getSellIn());
        assertEquals(3, shop.items.get(1).getQuality());
        assertEquals(4, shop.items.get(2).getSellIn());
        assertEquals(6, shop.items.get(2).getQuality());
        assertEquals(0, shop.items.get(3).getSellIn());
        assertEquals(80, shop.items.get(3).getQuality());
        assertEquals(-1, shop.items.get(4).getSellIn());
        assertEquals(80, shop.items.get(4).getQuality());
        assertEquals(14, shop.items.get(5).getSellIn());
        assertEquals(21, shop.items.get(5).getQuality());
        assertEquals(9, shop.items.get(6).getSellIn());
        assertEquals(50, shop.items.get(6).getQuality());
        assertEquals(4, shop.items.get(7).getSellIn());
        assertEquals(50, shop.items.get(7).getQuality());
        assertEquals(2, shop.items.get(8).getSellIn());
        assertEquals(4, shop.items.get(8).getQuality());
        assertEquals(-1, shop.items.get(9).getSellIn());
        assertEquals(2, shop.items.get(9).getQuality());
        assertEquals(-2, shop.items.get(10).getSellIn());
        assertEquals(0, shop.items.get(10).getQuality());
        assertEquals(9, shop.items.get(11).getSellIn());
        assertEquals(50, shop.items.get(11).getQuality());
    }
}
