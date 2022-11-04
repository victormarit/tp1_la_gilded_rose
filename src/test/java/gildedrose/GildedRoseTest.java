package gildedrose;

import gildedrose.beans.ConjuredItem;
import gildedrose.beans.Item;
import gildedrose.controllers.Shop;
import org.junit.jupiter.api.BeforeEach;
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
        new ConjuredItem("Test", 3, 6),
        new ConjuredItem("Test", 0, 6),
        new Item("Aged Brie", -1, 49),
        new Item("Aged Brie", 10, 48)
    );

    Shop shop;

    @BeforeEach
    void setUp() {
        shop = new Shop(items);
    }

    @Test
    void should_build() {
        Shop shopLocal = new Shop(items);
        assertEquals(Shop.class, shopLocal.getClass());
    }

    @Test
    void should_get_items() {
        assertEquals(items, shop.items);
    }

    @Test
    void should_get_item_sellIn() {
        assertEquals(10, shop.items.get(0).getSellIn());
    }

    @Test
    void should_get_item_quality() {
        assertEquals(20, shop.items.get(0).getQuality());
    }

    @Test
    void should_update_items_quality_backstage_passes() {
        shop.updateItemsQuality();
        assertEquals(14, shop.items.get(5).getSellIn());
        assertEquals(21, shop.items.get(5).getQuality());
        assertEquals(9, shop.items.get(6).getSellIn());
        assertEquals(50, shop.items.get(6).getQuality());
        assertEquals(4, shop.items.get(7).getSellIn());
        assertEquals(50, shop.items.get(7).getQuality());
    }

    @Test
    void should_update_items_quality_aged_brie() {
        shop.updateItemsQuality();
        assertEquals(1, shop.items.get(1).getSellIn());
        assertEquals(3, shop.items.get(1).getQuality());
        assertEquals(-2, shop.items.get(10).getSellIn());
        assertEquals(0, shop.items.get(10).getQuality());
        assertEquals(9, shop.items.get(11).getSellIn());
        assertEquals(50, shop.items.get(11).getQuality());
    }

    @Test
    void should_update_items_quality_sulfuras() {
        shop.updateItemsQuality();
        assertEquals(0, shop.items.get(3).getSellIn());
        assertEquals(80, shop.items.get(3).getQuality());
        assertEquals(-1, shop.items.get(4).getSellIn());
        assertEquals(80, shop.items.get(4).getQuality());
    }

    @Test
    void should_update_regular_item_quality(){
        shop.updateItemsQuality();
        assertEquals(9, shop.items.get(0).getSellIn());
        assertEquals(19, shop.items.get(0).getQuality());
        assertEquals(4, shop.items.get(2).getSellIn());
        assertEquals(6, shop.items.get(2).getQuality());
    }

    @Test
    void should_build_ConjuredItem() {
        ConjuredItem conjuredItem = new ConjuredItem("Conjured", 50, 50);
        assertEquals(ConjuredItem.class, conjuredItem.getClass());
    }

    @Test
    void should_update_quality_conjured_items() {
        shop.updateItemsQuality();
        assertEquals(2, shop.items.get(8).getSellIn());
        assertEquals(4, shop.items.get(8).getQuality());
        assertEquals(-1, shop.items.get(9).getSellIn());
        assertEquals(2, shop.items.get(9).getQuality());
    }
}
