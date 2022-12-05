package gildedrose.shop;

public interface ShopInputBoundary {
    void updateInventory();
    boolean sellItem(SellItemRequest request);

    void displayBalance();

    void displayInventory();
}
