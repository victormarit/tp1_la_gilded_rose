package gildedrose.shop;

import gildedrose.inventory.*;

import java.util.ArrayList;
import java.util.List;

public class ShopInteractor implements ShopInputBoundary {
    public final ItemsGateway itemsRepository;

    public final ShopOutputBoundary viewBoundary;
    private int balance;

    List<Item> samples = List.of(
        new AgingItem("Aged Brie", 2, 0, 5),
        new EventItem("Backstage passes to a TAFKAL80ETC concert", 15, 20, 8),
        new ConjuredItem("Conjured Mana Cake", 3, 6, 10)
    );

    public ShopInteractor() {
        this.itemsRepository = InMemoryRepository.getInstance();
        this.itemsRepository.saveInventory(samples);
        this.viewBoundary = new ConsoleView();
        balance = 50;
    }

    public void updateInventory() {
        List<Item> items = itemsRepository.getInventory();
        for (Item item : items) {
            item.update();
        }
    }
    public void sellItem(SellItemRequest request){
        Item item = this.itemsRepository.findItem(request.getType(), request.getQuality());
        List<Item> itemsRep = this.itemsRepository.getInventory();

        itemsRep.stream().filter(item1 -> item1.equals(item)).findFirst().ifPresent(itemFinal -> {
            itemFinal.update();
            this.balance += itemFinal.getValue();
        });
        this.itemsRepository.saveInventory(itemsRep);
    }

    public void displayBalance() {
        viewBoundary.displayBalance(this.balance);
    }

    public void displayInventory() {
        List<ItemResponse> itemResponses = new ArrayList<>();
        for (Item item : this.itemsRepository.getInventory()) {
            itemResponses.add(new ItemResponse(item.getName(), item.getSellIn(), item.getQuality(), item.getValue()));
        }
        viewBoundary.displayInventory(itemResponses);
    }
}