package gildedrose.shop;

import java.util.List;

public class ShopConsoleView implements ShopOutputBoundary {

    public void displayInventory(List<ItemResponse> inventory) {
        for (ItemResponse item : inventory) {
            System.out.println("---");
            System.out.println("Name : " + item.name());
            System.out.println("sellIn : " + item.sellIn());
            System.out.println("quality : " + item.quality());
        }
    }

    public void displayBalance(float balance) {
        System.out.println("---");
        System.out.println("Balance : " + balance);
        System.out.println("---");
    }
}
