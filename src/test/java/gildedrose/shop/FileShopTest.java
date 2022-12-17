package gildedrose.shop;

import gildedrose.inventory.*;
import gildedrose.notifier.DiscordNotifier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileShopTest {

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
        new AgingItem("Aged Brie", 10, 48, 8),
        new RelicItem("Relic", 48)
    );

    FileItemsRepository fileItemsRepository;

    BalanceGateway balanceRepository;

    ShopInteractor shopInteractor;

    @BeforeEach
    void setUp() {
        fileItemsRepository = FileItemsRepository.getInstance();
        fileItemsRepository.saveInventory(items);
        balanceRepository = FileBalanceRepository.getInstance();
        balanceRepository.saveBalance(50);
        shopInteractor = new ShopInteractor(fileItemsRepository, balanceRepository);
        items = fileItemsRepository.getInventory();
    }

    @Test
    void should_build() {
        ShopInteractor shopInteractorLocal = new ShopInteractor(fileItemsRepository, balanceRepository);
        assertEquals(ShopInteractor.class, shopInteractorLocal.getClass());
    }

    @Test
    void should_update_quality_conjured_items() {
        shopInteractor.updateInventory();
        List<Item> fileItems = fileItemsRepository.getInventory();

        if (fileItems.get(8) instanceof SellableItem sellableItem) {
            assertEquals(2, sellableItem.getSellIn());
        }

        assertEquals(4, fileItems.get(8).getQuality());
        if (fileItems.get(9) instanceof SellableItem sellableItem) {
            assertEquals(-1, sellableItem.getSellIn());
        }
        assertEquals(2, fileItems.get(9).getQuality());
    }


    @Test
    void should_sell_one_item(){
        List<Item> fileItems = fileItemsRepository.getInventory();
        if(fileItems.get(0) instanceof GenericItem genericItem){
            int itemSellin = genericItem.getSellIn();
            SellItemRequest request = new SellItemRequest(fileItemsRepository.getInventory().get(0).getClass().getSimpleName(), fileItemsRepository.getInventory().get(0).getQuality());
            shopInteractor.sellItem(request);
            fileItems = fileItemsRepository.getInventory();
            assertEquals(itemSellin-1, ((GenericItem) fileItems.get(0)).getSellIn());
        }

    }

    @Test
    void should_update_items_quality_backstage_passes() {
        shopInteractor.updateInventory();
        List<Item> fileItems = fileItemsRepository.getInventory();

        if (fileItems.get(5) instanceof SellableItem sellableItem) {
            assertEquals(14, sellableItem.getSellIn());
        }
        assertEquals(21, fileItems.get(5).getQuality());

        if (fileItems.get(6) instanceof SellableItem sellableItem) {
            assertEquals(9, sellableItem.getSellIn());
        }
        assertEquals(50, fileItems.get(6).getQuality());

        if (fileItems.get(7) instanceof SellableItem sellableItem) {
            assertEquals(4, sellableItem.getSellIn());
        }
        assertEquals(50, fileItems.get(7).getQuality());
    }

    @Test
    void should_update_items_quality_aged_brie() {
        shopInteractor.updateInventory();
        List<Item> fileItems = fileItemsRepository.getInventory();

        if (fileItems.get(1) instanceof SellableItem sellableItem) {
            assertEquals(1, sellableItem.getSellIn());
        }
        assertEquals(3, fileItems.get(1).getQuality());

        if (fileItems.get(10) instanceof SellableItem sellableItem) {
            assertEquals(-2, sellableItem.getSellIn());
        }
        assertEquals(0, fileItems.get(10).getQuality());

        if (fileItems.get(11) instanceof SellableItem sellableItem) {
            assertEquals(9, sellableItem.getSellIn());
        }
        assertEquals(50, fileItems.get(11).getQuality());
    }

    @Test
    void should_update_items_quality_sulfuras() {
        shopInteractor.updateInventory();
        List<Item> fileItems = fileItemsRepository.getInventory();

        if (fileItems.get(3) instanceof SellableItem sellableItem) {
            assertEquals(0, sellableItem.getSellIn());
        }
        assertEquals(80, fileItems.get(3).getQuality());

        if (fileItems.get(4) instanceof SellableItem sellableItem) {
            assertEquals(-1, sellableItem.getSellIn());
        }
        assertEquals(80, fileItems.get(4).getQuality());
    }

    @Test
    void should_update_generic_item_quality() {
        shopInteractor.updateInventory();
        List<Item> fileItems = fileItemsRepository.getInventory();

        if (fileItems.get(0) instanceof SellableItem sellableItem) {
            assertEquals(9, sellableItem.getSellIn());
        }
        assertEquals(19, fileItems.get(0).getQuality());

        if (fileItems.get(2) instanceof SellableItem sellableItem) {
            assertEquals(4, sellableItem.getSellIn());
        }
        assertEquals(6, fileItems.get(2).getQuality());
    }

    @Test
    void should_build_ConjuredItem() {
        ConjuredItem conjuredItem = new ConjuredItem("Conjured", 50, 50, 10);
        assertEquals(ConjuredItem.class, conjuredItem.getClass());
    }

    @Test
    void should_increase_shop_balance_by_100(){
        float shopBalance = this.balanceRepository.getBalance();
        List<Item> fileItems = fileItemsRepository.getInventory();
        shopInteractor.updateInventory();
        assertTrue(fileItems.get(12) instanceof UnsellableItem);
        if(fileItems.get(12) instanceof UnsellableItem){
            assertEquals(shopBalance+100, this.balanceRepository.getBalance());
        }
    }

    @Test
    void should_sell_an_item(){
        SellItemRequest sellItemRequest = new SellItemRequest("EventItem", 20);
        shopInteractor.sellItem(sellItemRequest);
        List<Item> fileItems = fileItemsRepository.getInventory();
        if (fileItems.get(5) instanceof SellableItem sellableItem) {
            assertEquals(14, sellableItem.getSellIn());
            assertEquals(204, DiscordNotifier.statusCode);
        }
    }

    @Test
    void relic_item_should_be_updatable(){
        assertFalse(items.get(12) instanceof Updatable);
        float balanceBeforeUpdate = balanceRepository.getBalance();
        shopInteractor.updateInventory();
        assertEquals(balanceBeforeUpdate + 100, balanceRepository.getBalance());

    }
}
