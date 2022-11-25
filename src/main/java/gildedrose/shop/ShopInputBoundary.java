package gildedrose.shop;

public interface ShopInputBoundary {
    void updateInventory();
    void sellItem(SellItemRequest request);

    void displayBalance();

    void displayInventory();
}
