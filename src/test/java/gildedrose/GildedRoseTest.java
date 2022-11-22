package gildedrose;

import gildedrose.beans.*;
import gildedrose.controllers.Shop;
import gildedrose.interfaces.IItem;
import gildedrose.repositories.FileRepository;
import gildedrose.repositories.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    List<Item> items = List.of(
        new GenericItem("+5 Dexterity Vest", 10, 20, 10),
        new AgingItem("Aged Brie", 2, 0, 5),
        new GenericItem("Elixir of the Mongoose", 5, 7, 6),
        new LegendaryItem("Sulfuras", 0, 80, 8),
        new LegendaryItem("Sulfuras", -1, 80, 12),
        new EventItem("Backstage passes", 15, 20, 15),
        new EventItem("Backstage passes", 10, 49, 10),
        new EventItem("Backstage passes", 5, 49, 9),
        new ConjuredItem("Test", 3, 6, 7),
        new ConjuredItem("Test", 0, 6, 4),
        new AgingItem("Aged Brie", -1, 49, 6),
        new AgingItem("Aged Brie", 10, 48, 8)
    );

    FileRepository fileRepository;

    InMemoryRepository inMemoryRepository;

    Shop shop;

    @BeforeEach
    void setUp() {
        shop = new Shop(items);
        fileRepository = FileRepository.getInstance();
        fileRepository.updateItems(items);
        inMemoryRepository = InMemoryRepository.getInstance();
        inMemoryRepository.updateItems(items);
        items = fileRepository.getItems();
    }

    @Test
    void should_build() {
        Shop shopLocal = new Shop(items);
        assertEquals(Shop.class, shopLocal.getClass());
    }

    @Test
    void should_get_items() {
        for (int i = 0; i < items.size(); i++) {
            assertEquals(items.get(i).getClass().getName(), shop.items.get(i).getClass().getName());
        }
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
    void should_update_generic_item_quality() {
        shop.updateItemsQuality();
        assertEquals(9, shop.items.get(0).getSellIn());
        assertEquals(19, shop.items.get(0).getQuality());
        assertEquals(4, shop.items.get(2).getSellIn());
        assertEquals(6, shop.items.get(2).getQuality());
    }

    @Test
    void should_build_ConjuredItem() {
        ConjuredItem conjuredItem = new ConjuredItem("Conjured", 50, 50, 10);
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


    @Test
    void should_sell_one_item(){
        int itemSellin = shop.items.get(0).getSellIn();
        shop.sellItem(shop.items.get(0).getClass().getSimpleName(), shop.items.get(0).getQuality());
        assertEquals(itemSellin-1, inMemoryRepository.getItems().get(0).getSellIn());
    }

}
