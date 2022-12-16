package gildedrose;

import gildedrose.inventory.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        if (inMemoryItems.get(0) instanceof GenericItem genericItem) {
            assertEquals(10, genericItem.getSellIn());
        }
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

    @Test
    void conjured_item_should_have_attributes_attack_minus_2_and_defense_plus_2() {
        if (items.get(8) instanceof AttributedItem attributedItem) {
            assertEquals(-2, attributedItem.getAttributes().stream().filter(attribute -> attribute.type.equals("AttackAttribute")).findFirst().get().getEffect());
            assertEquals(2, attributedItem.getAttributes().stream().filter(attribute -> attribute.type.equals("DefenseAttribute")).findFirst().get().getEffect());
        }
    }

    @Test
    void generic_item_should_have_defense_plus_1_and_attack_plus_1(){
        if (items.get(0) instanceof AttributedItem attributedItem) {
            assertEquals(1, attributedItem.getAttributes().stream().filter(attribute -> attribute.type.equals("AttackAttribute")).findFirst().get().getEffect());
            assertEquals(1, attributedItem.getAttributes().stream().filter(attribute -> attribute.type.equals("DefenseAttribute")).findFirst().get().getEffect());
        }
    }

    @Test
    void legendary_item_should_have_attack_plus_4(){
        if (items.get(3) instanceof AttributedItem attributedItem) {
            assertEquals(4, attributedItem.getAttributes().stream().filter(attribute -> attribute.type.equals("AttackAttribute")).findFirst().get().getEffect());
        }
    }

    @Test
    void legendary_item_should_not_be_updatable(){
        assertFalse(items.get(3) instanceof Updatable);
    }


    @Test
    void item_different_from_legendary_and_relic_should_be_updatable(){
        for (Item item : items) {
            if (!(item instanceof LegendaryItem) && !(item instanceof RelicItem)) {
                assertTrue(item instanceof Updatable);
            }
        }
    }

}
