package gildedrose.shop;

import java.util.List;

public class ConsoleView implements ShopOutputBoundary {

    public void displayInventory(List<ItemResponse> inventory) {
        for (ItemResponse item : inventory) {
            System.out.println("---");
            System.out.println("Name : " + item.getName());
            System.out.println("sellIn : " + item.getSellIn());
            System.out.println("quality : " + item.getQuality());
        }
    }

    public void displayBalance(float balance) {
        System.out.println("---");
        System.out.println("Balance : " + balance);
        System.out.println("---");
    }
}
