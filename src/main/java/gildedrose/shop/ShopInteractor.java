package gildedrose.shop;

import gildedrose.inventory.*;
import gildedrose.notifier.DiscordNotifier;

import java.io.IOException;
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
            if (item instanceof RelicItem) {
                this.balanceRepository.saveBalance(this.balanceRepository.getBalance() + 100);
            } else if (item instanceof Updatable updatableItem) {
                updatableItem.update();
            }
        }
        this.itemsRepository.saveInventory(items);
    }

    public boolean sellItem(SellItemRequest request) {
        AtomicBoolean itemSold = new AtomicBoolean(false);
        Item item = this.itemsRepository.findItem(request.type(), request.quality());
        List<Item> itemsRep = this.itemsRepository.getInventory();

        itemsRep.stream().filter(item1 -> item1.equals(item)).findFirst().ifPresent(itemFinal -> {
            if (itemFinal instanceof SellableItem sellableItem) {
                if (sellableItem instanceof Updatable updatableItem) {
                    updatableItem.update();
                }
                this.balanceRepository.saveBalance(balanceRepository.getBalance() + sellableItem.getValue());
                itemSold.set(true);
                try {
                    DiscordNotifier.notify("Item sold: " + itemFinal.getName() + " for " + sellableItem.getValue() + " gold");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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
            if (item instanceof SellableItem sellableItem) {
                itemResponses.add(new ItemResponse(item.getName(), sellableItem.getSellIn(), item.getQuality(), sellableItem.getValue()));
            } else {
                itemResponses.add(new ItemResponse(item.getName(), item.getQuality()));
            }
        }
        viewBoundary.displayInventory(itemResponses);
    }
}
