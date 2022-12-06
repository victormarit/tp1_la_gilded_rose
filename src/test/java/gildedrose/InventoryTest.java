package gildedrose;

import gildedrose.inventory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryTest {

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

    FileItemsRepository fileRepository;

    InMemoryItemsRepository inMemoryRepository;

    @BeforeEach
    void setUp() {
        fileRepository = FileItemsRepository.getInstance();
        fileRepository.saveInventory(items);
        inMemoryRepository = InMemoryItemsRepository.getInstance();
        inMemoryRepository.saveInventory(items);
        items = fileRepository.getInventory();
    }


    @Test
    void should_get_items() {
        List<Item> inMemoryItems = inMemoryRepository.getInventory();
        for (int i = 0; i < items.size(); i++) {
            assertEquals(items.get(i).getClass().getName(), inMemoryItems.get(i).getClass().getName());
        }
    }

    @Test
    void should_get_item_sellIn() {
        List<Item> inMemoryItems = inMemoryRepository.getInventory();
        assertEquals(10, inMemoryItems.get(0).getSellIn());
    }

    @Test
    void should_get_item_quality() {
        List<Item> inMemoryItems = inMemoryRepository.getInventory();
        assertEquals(20, inMemoryItems.get(0).getQuality());
    }

    @Test
    void should_build_ConjuredItem() {
        ConjuredItem conjuredItem = new ConjuredItem("Conjured", 50, 50, 10);
        assertEquals(ConjuredItem.class, conjuredItem.getClass());
    }

}
