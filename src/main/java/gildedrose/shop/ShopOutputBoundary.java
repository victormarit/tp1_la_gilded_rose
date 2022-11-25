package gildedrose.shop;

import java.util.List;

public interface ShopOutputBoundary {
    void displayInventory(List<ItemResponse> inventory);
    void displayBalance(int balance);
}
