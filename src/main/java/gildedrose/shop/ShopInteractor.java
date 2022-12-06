package gildedrose.shop;

import gildedrose.inventory.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ShopInteractor implements ShopInputBoundary {
    public final ItemsGateway itemsRepository;

    public final BalanceGateway balanceRepository;

    public final ShopOutputBoundary viewBoundary;



    public ShopInteractor(ItemsGateway itemsRepository, BalanceGateway balanceRepository) {
        this.itemsRepository = itemsRepository;
        this.viewBoundary = new ShopConsoleView();
        this.balanceRepository = balanceRepository;
    }

    public void updateInventory() {
        List<Item> items = itemsRepository.getInventory();
        for (Item item : items) {
            item.update();
        }
    }
    public boolean sellItem(SellItemRequest request){
        AtomicBoolean itemSold = new AtomicBoolean(false);
        Item item = this.itemsRepository.findItem(request.type(), request.quality());
        List<Item> itemsRep = this.itemsRepository.getInventory();

        itemsRep.stream().filter(item1 -> item1.equals(item)).findFirst().ifPresent(itemFinal -> {
            itemFinal.update();
            this.balanceRepository.saveBalance(balanceRepository.getBalance() + itemFinal.getValue());
            itemSold.set(true);
        });
        this.itemsRepository.saveInventory(itemsRep);
        return itemSold.get();
    }

    public void displayBalance() {
        viewBoundary.displayBalance(this.balanceRepository.getBalance());
    }

    public void displayInventory() {
        List<ItemResponse> itemResponses = new ArrayList<>();
        for (Item item : this.itemsRepository.getInventory()) {
            itemResponses.add(new ItemResponse(item.getName(), item.getSellIn(), item.getQuality(), item.getValue()));
        }
        viewBoundary.displayInventory(itemResponses);
    }
}
