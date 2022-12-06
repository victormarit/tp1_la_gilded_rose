package gildedrose;

import gildedrose.inventory.*;
import gildedrose.shop.SellItemRequest;
import gildedrose.shop.ShopInteractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShopTest {

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

    FileItemsRepository fileItemsRepository;

    InMemoryItemsRepository inMemoryItemsRepository;

    ShopInteractor shopInteractor;

    @BeforeEach
    void setUp() {
        shopInteractor = new ShopInteractor();
        fileItemsRepository = FileItemsRepository.getInstance();
        fileItemsRepository.saveInventory(items);
        inMemoryItemsRepository = InMemoryItemsRepository.getInstance();
        inMemoryItemsRepository.saveInventory(items);
        items = fileItemsRepository.getInventory();
    }

    @Test
    void should_build() {
        ShopInteractor shopInteractorLocal = new ShopInteractor();
        assertEquals(ShopInteractor.class, shopInteractorLocal.getClass());
    }

    @Test
    void should_update_quality_conjured_items() {
        shopInteractor.updateInventory();
        List<Item> inMemoryItems = inMemoryItemsRepository.getInventory();
        assertEquals(2, inMemoryItems.get(8).getSellIn());
        assertEquals(4, inMemoryItems.get(8).getQuality());
        assertEquals(-1, inMemoryItems.get(9).getSellIn());
        assertEquals(2, inMemoryItems.get(9).getQuality());
    }


    @Test
    void should_sell_one_item(){
        int itemSellin = inMemoryItemsRepository.getInventory().get(0).getSellIn();
        SellItemRequest request = new SellItemRequest(inMemoryItemsRepository.getInventory().get(0).getClass().getSimpleName(), inMemoryItemsRepository.getInventory().get(0).getQuality());
        shopInteractor.sellItem(request);
        assertEquals(itemSellin-1, inMemoryItemsRepository.getInventory().get(0).getSellIn());
    }

    @Test
    void should_update_items_quality_backstage_passes() {
        shopInteractor.updateInventory();
        List<Item> inMemoryItems = inMemoryItemsRepository.getInventory();
        assertEquals(14, inMemoryItems.get(5).getSellIn());
        assertEquals(21, inMemoryItems.get(5).getQuality());
        assertEquals(9, inMemoryItems.get(6).getSellIn());
        assertEquals(50, inMemoryItems.get(6).getQuality());
        assertEquals(4, inMemoryItems.get(7).getSellIn());
        assertEquals(50, inMemoryItems.get(7).getQuality());
    }

    @Test
    void should_update_items_quality_aged_brie() {
        shopInteractor.updateInventory();
        List<Item> inMemoryItems = inMemoryItemsRepository.getInventory();
        assertEquals(1, inMemoryItems.get(1).getSellIn());
        assertEquals(3, inMemoryItems.get(1).getQuality());
        assertEquals(-2, inMemoryItems.get(10).getSellIn());
        assertEquals(0, inMemoryItems.get(10).getQuality());
        assertEquals(9, inMemoryItems.get(11).getSellIn());
        assertEquals(50, inMemoryItems.get(11).getQuality());
    }

    @Test
    void should_update_items_quality_sulfuras() {
        shopInteractor.updateInventory();
        List<Item> inMemoryItems = inMemoryItemsRepository.getInventory();
        assertEquals(0, inMemoryItems.get(3).getSellIn());
        assertEquals(80, inMemoryItems.get(3).getQuality());
        assertEquals(-1, inMemoryItems.get(4).getSellIn());
        assertEquals(80, inMemoryItems.get(4).getQuality());
    }

    @Test
    void should_update_generic_item_quality() {
        shopInteractor.updateInventory();
        List<Item> inMemoryItems = inMemoryItemsRepository.getInventory();
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
}
