package gildedrose;

import gildedrose.beans.*;
import gildedrose.controllers.Shop;
import gildedrose.repositories.FileRepository;
import gildedrose.repositories.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        shop = new Shop();
        fileRepository = FileRepository.getInstance();
        fileRepository.updateItems(items);
        inMemoryRepository = InMemoryRepository.getInstance();
        inMemoryRepository.updateItems(items);
        items = fileRepository.getItems();
    }

    @Test
    void should_build() {
        Shop shopLocal = new Shop();
        assertEquals(Shop.class, shopLocal.getClass());
    }

    @Test
    void should_get_items() {
        List<Item> inMemoryItems = inMemoryRepository.getItems();
        for (int i = 0; i < items.size(); i++) {
            assertEquals(items.get(i).getClass().getName(), inMemoryItems.get(i).getClass().getName());
        }
    }

    @Test
    void should_get_item_sellIn() {
        List<Item> inMemoryItems = inMemoryRepository.getItems();
        assertEquals(10, inMemoryItems.get(0).getSellIn());
    }

    @Test
    void should_get_item_quality() {
        List<Item> inMemoryItems = inMemoryRepository.getItems();
        assertEquals(20, inMemoryItems.get(0).getQuality());
    }

    @Test
    void should_update_items_quality_backstage_passes() {
        shop.updateItemsQuality();
        List<Item> inMemoryItems = inMemoryRepository.getItems();
        assertEquals(14, inMemoryItems.get(5).getSellIn());
        assertEquals(21, inMemoryItems.get(5).getQuality());
        assertEquals(9, inMemoryItems.get(6).getSellIn());
        assertEquals(50, inMemoryItems.get(6).getQuality());
        assertEquals(4, inMemoryItems.get(7).getSellIn());
        assertEquals(50, inMemoryItems.get(7).getQuality());
    }

    @Test
    void should_update_items_quality_aged_brie() {
        shop.updateItemsQuality();
        List<Item> inMemoryItems = inMemoryRepository.getItems();
        assertEquals(1, inMemoryItems.get(1).getSellIn());
        assertEquals(3, inMemoryItems.get(1).getQuality());
        assertEquals(-2, inMemoryItems.get(10).getSellIn());
        assertEquals(0, inMemoryItems.get(10).getQuality());
        assertEquals(9, inMemoryItems.get(11).getSellIn());
        assertEquals(50, inMemoryItems.get(11).getQuality());
    }

    @Test
    void should_update_items_quality_sulfuras() {
        shop.updateItemsQuality();
        List<Item> inMemoryItems = inMemoryRepository.getItems();
        assertEquals(0, inMemoryItems.get(3).getSellIn());
        assertEquals(80, inMemoryItems.get(3).getQuality());
        assertEquals(-1, inMemoryItems.get(4).getSellIn());
        assertEquals(80, inMemoryItems.get(4).getQuality());
    }

    @Test
    void should_update_generic_item_quality() {
        shop.updateItemsQuality();
        List<Item> inMemoryItems = inMemoryRepository.getItems();
        assertEquals(9, inMemoryItems.get(0).getSellIn());
        assertEquals(19, inMemoryItems.get(0).getQuality());
        assertEquals(4, inMemoryItems.get(2).getSellIn());
        assertEquals(6, inMemoryItems.get(2).getQuality());
    }

    @Test
    void should_build_ConjuredItem() {
        ConjuredItem conjuredItem = new ConjuredItem("Conjured", 50, 50, 10);
        assertEquals(ConjuredItem.class, conjuredItem.getClass());
    }

    @Test
    void should_update_quality_conjured_items() {
        shop.updateItemsQuality();
        List<Item> inMemoryItems = inMemoryRepository.getItems();
        assertEquals(2, inMemoryItems.get(8).getSellIn());
        assertEquals(4, inMemoryItems.get(8).getQuality());
        assertEquals(-1, inMemoryItems.get(9).getSellIn());
        assertEquals(2, inMemoryItems.get(9).getQuality());
    }


    @Test
    void should_sell_one_item(){
        int itemSellin = inMemoryRepository.getItems().get(0).getSellIn();
        shop.sellItem(inMemoryRepository.getItems().get(0).getClass().getSimpleName(), inMemoryRepository.getItems().get(0).getQuality());
        assertEquals(itemSellin-1, inMemoryRepository.getItems().get(0).getSellIn());
    }

}
